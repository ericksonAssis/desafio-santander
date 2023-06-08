package com.santander.ibanking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SaldoInsulficienteException extends RuntimeException{

        public SaldoInsulficienteException(String message) {
            super(message);
        }
}
