package com.cuentas_bancarias.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class Transaccion {
    private Long id;
    private CuentaBancaria cuentaBancaria;
    private TipoTransferencia tipo;
    private BigDecimal monto;
    private LocalDateTime fecha;

    public Transaccion(CuentaBancaria cuentaBancaria, TipoTransferencia tipo, BigDecimal monto) {
        this.cuentaBancaria = cuentaBancaria;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }
}
