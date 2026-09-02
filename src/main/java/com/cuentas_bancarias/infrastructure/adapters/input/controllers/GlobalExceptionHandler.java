package com.cuentas_bancarias.infrastructure.adapters.input.controllers;

import com.cuentas_bancarias.domain.exception.FondosInsuficientesExcepcion;
import com.cuentas_bancarias.domain.exception.ValidacionDominioExcepcion;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FondosInsuficientesExcepcion.class)
    public ResponseEntity<?> insuficiente(FondosInsuficientesExcepcion ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("FONDOS_INSUFICIENTES", ex.getMessage()));
    }

    @ExceptionHandler(ValidacionDominioExcepcion.class)
    public ResponseEntity<?> handleValidation(ValidacionDominioExcepcion ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse("ERROR_VALIDACION", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgNotValid(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return ResponseEntity.badRequest().body(new ErrorResponse("ERROR_REQUEST", msg));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(500).body(new ErrorResponse("INTERNAL_ERROR", ex.getMessage()));
    }

    @Getter
    static class ErrorResponse {
        private String error;
        private String message;
        public ErrorResponse(String error, String message) { this.error = error; this.message = message; }
    }
}
