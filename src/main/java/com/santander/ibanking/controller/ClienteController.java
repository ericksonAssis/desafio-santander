package com.santander.ibanking.controller;

import com.santander.ibanking.exception.AccountAlreadyExistsException;
import com.santander.ibanking.model.dto.ConsultaHistoricoRequestDto;
import com.santander.ibanking.model.dto.DepositoRequestDto;
import com.santander.ibanking.model.dto.SaqueRequestDto;
import com.santander.ibanking.model.dto.ClienteDto;
import com.santander.ibanking.repository.ClienteRepository;
import com.santander.ibanking.service.IClienteServiceImpl;
import com.santander.ibanking.service.request.RequestType;
import com.santander.ibanking.service.response.TransacaoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class ClienteController {

    @Autowired
    private IClienteServiceImpl clienteService;

    @GetMapping("/todos")
    public ResponseEntity buscaTodosClientes(){

        List<ClienteDto> clientes =  clienteService.buscaTodos();
        return ResponseEntity.ok(clientes);

    }
    @PostMapping("/cliente")
    public ResponseEntity adicionaCliente(@Valid @RequestBody ClienteDto clienteDto) throws AccountAlreadyExistsException{

        clienteService.cadastraCliente(clienteDto);
        return ResponseEntity.ok("Cliente criado");
    }

    @PostMapping(name = "conta/saque", value = "saque")
    public ResponseEntity saque(@Valid @RequestBody SaqueRequestDto saqueReqDto){

        TransacaoResponse response = clienteService.sacar(saqueReqDto);
            return ResponseEntity.ok(response);

    }

    @PostMapping(name = "conta/deposito", value = "depositos")
    public ResponseEntity deposito(@Valid @RequestBody DepositoRequestDto depositoRequestDto){

        TransacaoResponse response = clienteService.depositar(depositoRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(name = "conta/historico", value = "historico")
    public ResponseEntity historicoMovimentacoes(@Valid @RequestBody ConsultaHistoricoRequestDto consulta){

        TransacaoResponse response = clienteService.consultaHistorico(consulta);
        return ResponseEntity.ok(response);
    }

}
