package com.ti9.ti9_backend.exceptionhandler;

import com.ti9.ti9_backend.exceptions.RecursoNaoEncontradoException;
import com.ti9.ti9_backend.exceptions.ValorNaoPermitidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<String> handlerNotFound(RecursoNaoEncontradoException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler(ValorNaoPermitidoException.class)
    public ResponseEntity<String> handlerInvalidArgument(ValorNaoPermitidoException exception){
        return ResponseEntity.status(400).body("Valor Inválido:\n" + exception.getMessage());
    }
}
