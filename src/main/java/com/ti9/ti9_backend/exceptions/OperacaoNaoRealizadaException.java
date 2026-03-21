package com.ti9.ti9_backend.exceptions;

public class OperacaoNaoRealizadaException extends RuntimeException{
    public OperacaoNaoRealizadaException(String message) {
        super(message);
    }
}
