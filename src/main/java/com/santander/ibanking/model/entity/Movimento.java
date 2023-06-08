package com.santander.ibanking.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_movimentacao_conta")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movimento {
    @Id
    @Column(name = "id_movimentacao")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISTORICO_MOVIMENTACAO_CONTA_SEQ")
    private Long id;
    @Column
    private String numeroConta;
    @Column(name = "data_movimentacao")
    LocalDateTime dataMovimentacao;
    @Column(name = "tipo_movimentacao")
    private String tipoMovimentacao;

    @Column(name = "valor_movimentacao")
    private BigDecimal valorMovimentacao;

    @Column(name = "valor_taxa_administracao")
    private BigDecimal valorTaxaAdministracao;

    @Column(name = "regra_taxa_aplicada")
    private String regra_taxa_aplicada;

    @Column(name = "saldo_anterior")
    private BigDecimal saldoAnterior;

    @Column(name = "saldo_atual")
    private BigDecimal saldoAtual;
}
