package com.cuentas_bancarias.domain.exception;

public class ValidacionDominioExcepcion extends RuntimeException{
    public ValidacionDominioExcepcion(String mensaje) {
        super(mensaje);
    }
}
