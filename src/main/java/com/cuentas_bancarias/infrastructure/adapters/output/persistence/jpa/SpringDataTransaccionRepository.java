package com.cuentas_bancarias.infrastructure.adapters.output.persistence.jpa;

import com.cuentas_bancarias.infrastructure.adapters.output.persistence.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataTransaccionRepository extends JpaRepository<TransaccionEntity, Long> {
}
