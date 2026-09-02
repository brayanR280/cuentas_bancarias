package com.cuentas_bancarias.application.ports.output;

import com.cuentas_bancarias.domain.entities.CuentaBancaria;

import java.util.Optional;

public interface CuentaRepositoryPort {
    CuentaBancaria save(CuentaBancaria cuentaBancaria);
    Optional<CuentaBancaria> findById(Long id);
    CuentaBancaria update(CuentaBancaria cuentaBancaria);
}
