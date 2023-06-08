package com.santander.ibanking.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepositoRequestDto extends TransactionDTO{

    @Size(min = 1, max = 20, message = "O numero de conta deve conter de 1 a 20 caracteres")
    @NotBlank
    @Digits( integer = 10, fraction = 0, message = "O numero de conta deve conter números somente")
    private String numeroConta;

    @Digits( integer = 5, fraction = 2, message = "O limite máximo para essa transação é de R$ 50.000,00. Para valores superiores, contate o seu gerente.")
    @Max(value = 50000, message = "O limite máximo para essa transação é R$ 50.000,00. Para valores maiores, conta o seu gerente.")
    @Min(value = 1, message = "O valor mínimo de depósito é R$ 1,00")
    private BigDecimal quantia;
}
