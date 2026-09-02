package com.cuentas_bancarias.application.ports.input;

import com.cuentas_bancarias.application.dto.ConsultarSaldoResponse;
import com.cuentas_bancarias.application.dto.CrearCuentaRequest;
import com.cuentas_bancarias.application.dto.CrearCuentaResponse;

public interface CrearCuentaUseCase {
    CrearCuentaResponse crearCuenta(CrearCuentaRequest request);
}
