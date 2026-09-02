package com.cuentas_bancarias.infrastructure.adapters.output.persistence.mapper;

import com.cuentas_bancarias.domain.entities.CuentaBancaria;
import com.cuentas_bancarias.infrastructure.adapters.output.persistence.entity.CuentaEntity;
import lombok.NoArgsConstructor;

public final class CuentaMapper {
    private CuentaMapper() {
    }

    public static CuentaBancaria toDomain(CuentaEntity cuenta) {
        if (cuenta == null) {
            return null;
        }

        CuentaBancaria dto = new CuentaBancaria(cuenta.getTitular());
        dto.setId(cuenta.getId());
        dto.setSaldo(cuenta.getSaldo());
        dto.setFechaCreacion(cuenta.getFechaCreacion());
        return dto;
    }

    public static CuentaEntity toEntity(CuentaBancaria dto) {
        if (dto == null) {
            return null;
        }

        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setId(dto.getId());
        cuenta.setTitular(dto.getTitular());
        cuenta.setSaldo(dto.getSaldo());
        cuenta.setFechaCreacion(dto.getFechaCreacion());

        return cuenta;
    }
}
