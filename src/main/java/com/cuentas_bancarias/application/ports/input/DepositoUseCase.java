package com.cuentas_bancarias.application.ports.input;

import com.cuentas_bancarias.application.dto.TransaccionRequest;
import com.cuentas_bancarias.application.dto.TransaccionResponse;

public interface DepositoUseCase {
    TransaccionResponse depositar(TransaccionRequest request, Long numeroCuenta);
}
