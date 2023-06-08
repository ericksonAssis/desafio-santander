package com.santander.ibanking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties
public abstract class TransactionDTO extends DTO{

    private String numeroConta;

    private BigDecimal quantia;
}
