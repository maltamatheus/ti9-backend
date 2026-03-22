package com.ti9.ti9_backend.exceptionhandler;

import com.ti9.ti9_backend.exceptions.OperacaoNaoRealizadaException;
import com.ti9.ti9_backend.exceptions.RecursoNaoEncontradoException;
import com.ti9.ti9_backend.exceptions.ValorNaoPermitidoException;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<String> handlerNotFound(RecursoNaoEncontradoException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler(ValorNaoPermitidoException.class)
    public ResponseEntity<String> handlerInvalidArgument(ValorNaoPermitidoException exception){
        return ResponseEntity.status(400).body(exception.getMessage());
    }
    @ExceptionHandler(OperacaoNaoRealizadaException.class)
    public ResponseEntity<String> handlerOperationError(OperacaoNaoRealizadaException exception){
        return ResponseEntity.status(400).body(exception.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleMessageNotReadableException(HttpMessageNotReadableException exception){
        return ResponseEntity.status(400).body(exception.getMessage());
    }
}
