package com.cuentas_bancarias.infrastructure.adapters.output.persistence;

import com.cuentas_bancarias.application.ports.output.CuentaRepositoryPort;
import com.cuentas_bancarias.application.ports.output.TransaccionRepositoryPort;
import com.cuentas_bancarias.domain.entities.CuentaBancaria;
import com.cuentas_bancarias.domain.entities.Transaccion;
import com.cuentas_bancarias.infrastructure.adapters.output.persistence.entity.CuentaEntity;
import com.cuentas_bancarias.infrastructure.adapters.output.persistence.entity.TransaccionEntity;
import com.cuentas_bancarias.infrastructure.adapters.output.persistence.jpa.SpringDataCuentaRepository;
import com.cuentas_bancarias.infrastructure.adapters.output.persistence.jpa.SpringDataTransaccionRepository;
import com.cuentas_bancarias.infrastructure.adapters.output.persistence.mapper.CuentaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class JpaCuentaRepositoryAdapter implements CuentaRepositoryPort, TransaccionRepositoryPort {

    private final SpringDataCuentaRepository cuentaRepository;
    private final SpringDataTransaccionRepository transaccionRepository;

    @Override
    public CuentaBancaria save(CuentaBancaria cuentaBancaria) {
        CuentaEntity cuentaEntity = CuentaMapper.toEntity(cuentaBancaria);
        CuentaEntity savedEntity = cuentaRepository.save(cuentaEntity);
        return CuentaMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CuentaBancaria> findById(Long id) {
        return cuentaRepository.findById(id).map(CuentaMapper::toDomain);
    }

    @Override
    public CuentaBancaria update(CuentaBancaria cuentaBancaria) {
        CuentaEntity cuentaEntity = cuentaRepository.findById(cuentaBancaria.getId()).orElseThrow(
                () -> new IllegalArgumentException("Cuenta no encontrada"));
        cuentaEntity.setSaldo(cuentaBancaria.getSaldo());
        CuentaEntity updatedEntity = cuentaRepository.save(cuentaEntity);
        return CuentaMapper.toDomain(updatedEntity);
    }

    @Override
    public Transaccion save(Transaccion transaccion) {
        TransaccionEntity transaccionEntity = new TransaccionEntity();
        transaccionEntity.setId(transaccion.getId());
        transaccionEntity.setMonto(transaccion.getMonto());
        transaccionEntity.setFecha(transaccion.getFecha());
        TransaccionEntity savedEntity = transaccionRepository.save(transaccionEntity);
        transaccion.setId(savedEntity.getId());
        return transaccion;
    }
}