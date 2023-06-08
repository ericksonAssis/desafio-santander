package com.santander.ibanking.service.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoResponse {

    String message;

    Object objetoTransacionado;

}
