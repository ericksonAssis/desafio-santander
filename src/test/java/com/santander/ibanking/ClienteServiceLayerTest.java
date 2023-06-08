package com.santander.ibanking;

import com.santander.ibanking.model.dto.DepositoRequestDto;
import com.santander.ibanking.model.dto.SaqueRequestDto;
import com.santander.ibanking.model.entity.Cliente;
import com.santander.ibanking.model.entity.Movimento;
import com.santander.ibanking.repository.ClienteRepository;
import com.santander.ibanking.repository.MovimentoRepository;
import com.santander.ibanking.service.IClienteServiceImpl;
import com.santander.ibanking.service.response.TransacaoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class ClienteServiceLayerTest {

    @InjectMocks
    IClienteServiceImpl clienteService = new IClienteServiceImpl();

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    MovimentoRepository movimentoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void realiza_saque(){

        SaqueRequestDto saqueRequestDto = criaSaqueDto();
        Cliente cliente = criaCliente();
        Movimento movimento = criaMovimento();

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteRepository.findByNumeroConta(any(String.class))).thenReturn(Optional.of(cliente));
        when(movimentoRepository.save(any(Movimento.class))).thenReturn(movimento);

        TransacaoResponse response = clienteService.sacar(saqueRequestDto);
        assertThat(true, is(response.getMessage().equals("Saque realizado com sucesso.")));

    }

    @Test
    void realiza_deposito(){

        DepositoRequestDto depositoRequestDto = criaDepositoReqDto();
        Cliente cliente = criaCliente();
        Movimento movimento = criaMovimento();

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(clienteRepository.findByNumeroConta(any(String.class))).thenReturn(Optional.of(cliente));
        when(movimentoRepository.save(any(Movimento.class))).thenReturn(movimento);

        TransacaoResponse response = clienteService.depositar(depositoRequestDto);
        assertThat(true, is(response.getMessage().equals("Depósito realizado com sucesso.")));

    }

    private static DepositoRequestDto criaDepositoReqDto() {
        return DepositoRequestDto.builder()
                .numeroConta("200")
                .quantia(BigDecimal.TEN)
                .build();
    }

    private static SaqueRequestDto criaSaqueDto() {
        SaqueRequestDto saqueRequestDto = SaqueRequestDto.builder()
                .quantia(BigDecimal.TEN)
                .numeroConta("200")
                .build();
        return saqueRequestDto;
    }

    private static Cliente criaCliente() {
        Cliente cliente = Cliente.builder()
                .saldo(BigDecimal.ZERO)
                .numeroConta("333")
                .nome("João")
                .dataNascimento(LocalDate.now().minusYears(18))
                .planoExclusive(false)
                .build();
        return cliente;
    }

    private static Movimento criaMovimento() {
        return Movimento.builder().numeroConta("200")
                .valorTaxaAdministracao(BigDecimal.TEN)
                .tipoMovimentacao("DEPOSITO")
                .valorMovimentacao(BigDecimal.valueOf(1000.00))
                .saldoAnterior(BigDecimal.ZERO)
                .regra_taxa_aplicada("Isento")
                .saldoAtual(BigDecimal.valueOf(Double.parseDouble("1000.00")))
                .dataMovimentacao(LocalDateTime.now())
                .build();
    }
}
