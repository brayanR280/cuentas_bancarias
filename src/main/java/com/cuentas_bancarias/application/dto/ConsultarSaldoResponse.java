package com.cuentas_bancarias.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ConsultarSaldoResponse {
    private Long cuentaId;
    private String titular;
    private BigDecimal saldo;
    private LocalDateTime fechaConsulta;
}
