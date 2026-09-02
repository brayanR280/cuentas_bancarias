package com.cuentas_bancarias.domain.exception;

public class FondosInsuficientesExcepcion extends RuntimeException{
    public FondosInsuficientesExcepcion(String mensaje) {
        super(mensaje);
    }
}
