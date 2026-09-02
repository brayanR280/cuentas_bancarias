package com.cuentas_bancarias.infrastructure.adapters.output.persistence.jpa;

import com.cuentas_bancarias.infrastructure.adapters.output.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCuentaRepository extends JpaRepository<CuentaEntity, Long> {
}
