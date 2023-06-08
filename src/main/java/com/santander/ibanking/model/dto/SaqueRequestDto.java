package com.santander.ibanking.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaqueRequestDto extends TransactionDTO{

    @Size(min = 1, max = 20, message = "O numero de conta deve conter de 1 a 20 caracteres")
    @NotBlank
    @Digits( integer = 10, fraction = 0, message = "O numero de conta deve conter números somente")
    private String numeroConta;

    @Digits( integer = 4, fraction = 0, message = "O numero de conta deve conter números somente")
    @Max(value = 1000, message = "A quantia informada é muito alta, esta operação precisa ser feita junto ao caixa ou você deve solicitar aumento do limite dessa transação.")
    @Min(value = 1)
    private BigDecimal quantia;
}
