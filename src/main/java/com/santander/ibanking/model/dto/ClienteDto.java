package com.santander.ibanking.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto extends DTO{

    @Size(min = 1, max = 20, message = "O numero de conta deve conter de 1 a 20 caracteres")
    @NotBlank
    @Digits( integer = 6, fraction = 0, message = "O numero de conta deve conter números somente")
    private String numeroConta;

    @Size(max = 50, message = "O nome deve conter entre 0 e 50 caracteres")
    @NotBlank
    private String nome;

    @Past(message = "A data de nascimento deve estar no passado")
    private LocalDate dataNascimento;

    @NotNull(message = "Deve ser informado se o cliente pertence ao plano exclusive")
    private Boolean isPlanoExclusive;

    @Digits( integer = 6, fraction = 2, message = "O saldo deve conter números somente")
    private BigDecimal saldo;
}
