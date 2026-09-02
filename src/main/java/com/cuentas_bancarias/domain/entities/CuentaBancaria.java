package com.cuentas_bancarias.domain.entities;

import com.cuentas_bancarias.domain.exception.FondosInsuficientesExcepcion;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class CuentaBancaria {
    private Long id;
    private String titular;
    private BigDecimal saldo;
    private LocalDateTime fechaCreacion;

    public CuentaBancaria(String titular) {
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
        this.fechaCreacion = LocalDateTime.now();
    }

    public static CuentaBancaria crear(String titular) {
        return new CuentaBancaria(titular);
    }

    public void depositar(BigDecimal monto) {
        saldo = saldo.add(monto);
    }

    public void retirar(BigDecimal monto) {
        validarSaldo(monto);
        saldo = saldo.subtract(monto);
    }

    public void validarSaldo(BigDecimal monto) {
        if (saldo.compareTo(monto) < 0 || monto == null) {
            throw new FondosInsuficientesExcepcion("Saldo insuficiente");
        }
    }

}
