package com.santander.ibanking.service;

import com.santander.ibanking.exception.AccountAlreadyExistsException;
import com.santander.ibanking.exception.AccountNotFoundException;
import com.santander.ibanking.exception.SaldoInsulficienteException;
import com.santander.ibanking.model.dto.*;
import com.santander.ibanking.model.entity.Cliente;
import com.santander.ibanking.model.dto.mapper.IClienteMapper;
import com.santander.ibanking.model.entity.Movimento;
import com.santander.ibanking.repository.ClienteRepository;
import com.santander.ibanking.repository.MovimentoRepository;
import com.santander.ibanking.service.request.RegraTaxaAdm;
import com.santander.ibanking.service.request.RequestType;
import com.santander.ibanking.service.response.TransacaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class IClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    MovimentoRepository movimentoRepository;
    private BigDecimal quantia;
    private static RegraTaxaAdm regraAplicada;
    private BigDecimal saldoAtual = BigDecimal.ZERO;
    private BigDecimal taxaAdmin = BigDecimal.ZERO;
    private BigDecimal novoSaldo;
    public IClienteServiceImpl() {
    }

    @Override
    public List<ClienteDto> buscaTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return convertClientesToDto(clientes);
    }

    public Cliente buscaCliente(ClienteDto clienteDto) {
        validaCliente(clienteDto.getNumeroConta(), RequestType.CONSULTA, quantia);
        return IClienteMapper.mapToEntity(clienteDto);
    }

    public Cliente  cadastraCliente(ClienteDto clienteDto) {
        validaCliente(clienteDto.getNumeroConta(), RequestType.CADASTRO, null);
        return clienteRepository.save(IClienteMapper.mapToEntity(clienteDto));
    }
    private List<ClienteDto> convertClientesToDto(List<Cliente> clientes){

        List<ClienteDto> clientesDto = new ArrayList<>();
            clientes.stream().forEach(cliente -> {
                ClienteDto clienteDto = IClienteMapper.mapToDto(cliente);
                clientesDto.add(clienteDto);

            });
            return clientesDto;
        }


    void validaCliente(String numeroConta, RequestType requestType, BigDecimal quantia) {

        Optional<Cliente> cliente = clienteRepository.findByNumeroConta(numeroConta);

        switch (requestType) {
            case CADASTRO -> {
                if(cliente.isPresent()){
                    throw new AccountAlreadyExistsException("Já existe cliente cadastrado para a conta informada.");
                }
            }

            case DEPOSITO -> {
                if(!cliente.isPresent()){
                    throw new AccountNotFoundException("A conta informada é inválida.");
                }
            }
            case SAQUE -> {
                if(!cliente.isPresent()){
                    throw new AccountNotFoundException("A conta informada é inválida.");
                }
                if (cliente.isPresent() && cliente.get().getSaldo().compareTo(quantia) == -1) {
                    throw new SaldoInsulficienteException("Saldo insulficiente");
                }
            } case CONSULTA -> {
                if(!cliente.isPresent()){
                    throw new AccountNotFoundException("A conta informada é inválida.");
                }
            }
        }
        return;
    }
    @Override
    public TransacaoResponse sacar (SaqueRequestDto saqueReqDto){

        Cliente cliente = clienteRepository.findByNumeroConta(saqueReqDto.getNumeroConta()).orElseThrow(() -> new AccountNotFoundException("Conta não encontrada."));
        BigDecimal saldoAtual = cliente.getSaldo();
        boolean planoExclusivo = cliente.getPlanoExclusive();
        taxaAdmin = TaxaAministracao.calculaTaxaDeAdministracao(saqueReqDto.getQuantia(), planoExclusivo);
        BigDecimal novoSaldo = saldoAtual.subtract(saqueReqDto.getQuantia().add(taxaAdmin));
        cliente.setSaldo(novoSaldo);
        clienteRepository.save(cliente);
        Movimento movimento = criaMovimento(cliente, saldoAtual, novoSaldo, RequestType.SAQUE, saqueReqDto);
        movimentoRepository.save(movimento);
        return TransacaoResponse.builder().message("Saque realizado com sucesso.")
                .objetoTransacionado(cliente).build();
    }

    public TransacaoResponse depositar (DepositoRequestDto depositoDto){
        validaCliente(depositoDto.getNumeroConta(), RequestType.DEPOSITO, null);
        Cliente cliente = clienteRepository.findByNumeroConta(depositoDto.getNumeroConta()).orElseThrow(() -> new AccountNotFoundException("Conta não encontrada"));
        BigDecimal saldoAtual = cliente.getSaldo();
        BigDecimal novoSaldo = saldoAtual.add(depositoDto.getQuantia());
        cliente.setSaldo(novoSaldo);
        regraAplicada = RegraTaxaAdm.RegraDeposito;
        clienteRepository.save(cliente);
        Movimento movimento = criaMovimento(cliente, saldoAtual, novoSaldo, RequestType.DEPOSITO, depositoDto);
        movimentoRepository.save(movimento);
        return TransacaoResponse.builder().message("Depósito realizado com sucesso.")
                .objetoTransacionado(cliente).build();
    }

    @Override
    public TransacaoResponse consultaHistorico (ConsultaHistoricoRequestDto consulta){
        validaCliente(consulta.getNumeroConta(), RequestType.CONSULTA, null);

        Predicate<Movimento> dataInicio = movimento -> movimento.getDataMovimentacao().isAfter(consulta.getDataInicio().atStartOfDay());
        Predicate<Movimento> dataFim = movimento -> movimento.getDataMovimentacao().isBefore(consulta.getDataFim().plusDays(1).atStartOfDay());

        Predicate<Movimento> between = dataInicio.and(dataFim);
        List<Movimento> movimentos = movimentoRepository.findAll().stream().filter(between)
                        .collect(Collectors.toList());

        String formattedMessage = String.format("Movimentações do período %s à %s", consulta.getDataInicio(), consulta.getDataFim());
        return TransacaoResponse.builder().objetoTransacionado(movimentos.toString())
                .message(formattedMessage).build();
    }

    private Movimento criaMovimento (Cliente cliente, BigDecimal saldoAtual, BigDecimal novoSaldo, RequestType
    requestType, TransactionDTO depositoRequestDto){
        return Movimento.builder()
                .numeroConta(cliente.getNumeroConta())
                .dataMovimentacao(LocalDateTime.now())
                .saldoAnterior(saldoAtual)
                .saldoAtual(novoSaldo)
                .regra_taxa_aplicada(regraAplicada.getDescricao())
                .tipoMovimentacao(requestType.toString())
                .valorMovimentacao(depositoRequestDto.getQuantia())
                .valorTaxaAdministracao(taxaAdmin)
                .build();
    }

    private static class TaxaAministracao {

    Cliente cliente;
    BigDecimal valor;


        static BigDecimal calculaTaxaDeAdministracao(BigDecimal quantia, Boolean isPlanoExclusive) {

            int comparison1 = quantia.compareTo(BigDecimal.valueOf(100.00));
            int comparison2 = quantia.compareTo(BigDecimal.valueOf(300.00));


            if (isPlanoExclusive) {
                regraAplicada = RegraTaxaAdm.RegraSaqueExclusive;
                return BigDecimal.ZERO;
            }

            if (comparison1 == -1 || comparison1 == 0) {
                regraAplicada = RegraTaxaAdm.RegraSaqueUm;
                return BigDecimal.ZERO;
            }

            if (comparison1 == 1 && (comparison2 == -1 || comparison2 == 0)) {
                BigDecimal taxa = BigDecimal.valueOf(Double.parseDouble("0.4")).divide(BigDecimal.valueOf(100));
                BigDecimal valorTaxa = quantia.multiply(taxa);
                regraAplicada = RegraTaxaAdm.RegraSaqueDois;
                return valorTaxa;
            }
            if (comparison2 == 1) {
                BigDecimal taxa = BigDecimal.ONE.divide(BigDecimal.valueOf(100));
                BigDecimal valorTaxa = quantia.multiply(taxa);
                regraAplicada = RegraTaxaAdm.RegraSaqueTres;
                return valorTaxa;
            }

            return null;
        }
    }
}
