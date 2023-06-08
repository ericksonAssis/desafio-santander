package com.santander.ibanking.model.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MovimentoDto {

    private Long id;
    private String numeroConta;
    LocalDateTime dataMovimentacao;
    private String tipoMovimentacao;
    private BigDecimal valorMovimentacao;
    private BigDecimal taxa_administracao;
    private BigDecimal valorTaxaAdministracao;
    private String regra_taxa_aplicada;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoAtual;
}
