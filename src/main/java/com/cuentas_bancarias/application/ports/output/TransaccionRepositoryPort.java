package com.cuentas_bancarias.application.ports.output;

import com.cuentas_bancarias.domain.entities.Transaccion;

public interface TransaccionRepositoryPort {
    Transaccion save(Transaccion transaccion);
}
