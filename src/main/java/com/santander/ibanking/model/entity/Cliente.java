package com.santander.ibanking.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @Column(name = "numero_conta")
    private String numeroConta;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "plano_exclusive")
    private Boolean planoExclusive;

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
