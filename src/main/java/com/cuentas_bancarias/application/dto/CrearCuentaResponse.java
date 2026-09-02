package com.cuentas_bancarias.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class CrearCuentaResponse {
    private Long numeroCuenta;
    private String titular;
    private BigDecimal saldo;
    private LocalDateTime fechaCreacion;
}
