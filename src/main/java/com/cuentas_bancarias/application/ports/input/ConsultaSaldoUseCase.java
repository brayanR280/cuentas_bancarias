package com.cuentas_bancarias.application.ports.input;

import com.cuentas_bancarias.application.dto.ConsultarSaldoResponse;

public interface ConsultaSaldoUseCase {
    ConsultarSaldoResponse consultarSaldo(Long numeroCuenta);
}
