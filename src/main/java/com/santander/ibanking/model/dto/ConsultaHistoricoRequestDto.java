package com.santander.ibanking.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ConsultaHistoricoRequestDto {

    @Size(min = 1, max = 20, message = "O numero de conta deve conter de 1 a 20 caracteres")
    @NotBlank
    @Digits( integer = 10, fraction = 0, message = "O numero de conta deve conter números somente")
    private String numeroConta;

    @PastOrPresent(message = "A data de início deve estar no passado ou até o dia de hoje")
    private LocalDate dataInicio;

    @PastOrPresent(message = "A data de início deve estar no passado ou até o dia de hoje")
    private LocalDate dataFim;
}
