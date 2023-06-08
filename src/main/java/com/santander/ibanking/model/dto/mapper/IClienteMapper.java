package com.santander.ibanking.model.dto.mapper;

import com.santander.ibanking.model.entity.Cliente;
import com.santander.ibanking.model.dto.ClienteDto;

import java.math.BigDecimal;

public interface IClienteMapper {
    public static ClienteDto mapToDto(Cliente clienteEntity) {

        return ClienteDto.builder()
                .isPlanoExclusive(clienteEntity.getPlanoExclusive())
                .nome(clienteEntity.getNome())
                .dataNascimento(clienteEntity.getDataNascimento())
                .numeroConta(clienteEntity.getNumeroConta())
                .saldo(clienteEntity.getSaldo())
                .build();
    }

    public static Cliente mapToEntity(ClienteDto clienteDto) {
        //Conta contaEntity = IContaMapper.mapToEntity(clienteDto.getConta());

        return Cliente.builder()
                .planoExclusive(clienteDto.getIsPlanoExclusive())
                .numeroConta(clienteDto.getNumeroConta())
                .dataNascimento(clienteDto.getDataNascimento())
                .nome(clienteDto.getNome())
                .saldo(clienteDto.getSaldo())
                .build();

    }
}
