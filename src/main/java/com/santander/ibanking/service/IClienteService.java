package com.santander.ibanking.service;

import com.santander.ibanking.model.dto.ConsultaHistoricoRequestDto;
import com.santander.ibanking.model.dto.DepositoRequestDto;
import com.santander.ibanking.model.dto.SaqueRequestDto;
import com.santander.ibanking.model.entity.Cliente;
import com.santander.ibanking.model.dto.ClienteDto;
import com.santander.ibanking.service.response.TransacaoResponse;

import java.util.List;

public interface IClienteService {

    List<ClienteDto> buscaTodos();
    Cliente cadastraCliente(ClienteDto clienteDto);

    TransacaoResponse sacar(SaqueRequestDto saqueRequestDto);

    TransacaoResponse depositar(DepositoRequestDto depositoRequestDto);

    TransacaoResponse consultaHistorico(ConsultaHistoricoRequestDto consulta);
}
