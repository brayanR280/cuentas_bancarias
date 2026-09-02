package com.cuentas_bancarias.application.service;

import com.cuentas_bancarias.application.dto.*;
import com.cuentas_bancarias.application.ports.input.ConsultaSaldoUseCase;
import com.cuentas_bancarias.application.ports.input.CrearCuentaUseCase;
import com.cuentas_bancarias.application.ports.input.DepositoUseCase;
import com.cuentas_bancarias.application.ports.input.RetiroUseCase;
import com.cuentas_bancarias.application.ports.output.CuentaRepositoryPort;
import com.cuentas_bancarias.application.ports.output.TransaccionRepositoryPort;
import com.cuentas_bancarias.domain.entities.CuentaBancaria;
import com.cuentas_bancarias.domain.entities.TipoTransferencia;
import com.cuentas_bancarias.domain.entities.Transaccion;
import com.cuentas_bancarias.domain.exception.ValidacionDominioExcepcion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CuentaService implements CrearCuentaUseCase, DepositoUseCase, RetiroUseCase, ConsultaSaldoUseCase {

    private final CuentaRepositoryPort cuentaRepositoryPort;
    private final TransaccionRepositoryPort transaccionRepositoryPort;

    @Override
    public ConsultarSaldoResponse consultarSaldo(Long numeroCuenta) {
        CuentaBancaria cuenta = cuentaRepositoryPort.findById(numeroCuenta).orElseThrow(
                () -> new ValidacionDominioExcepcion("Cuenta no encontrada"));
        return new ConsultarSaldoResponse(cuenta.getId(), cuenta.getTitular(), cuenta.getSaldo(), LocalDateTime.now());
    }

    @Override
    public CrearCuentaResponse crearCuenta(CrearCuentaRequest request) {
        CuentaBancaria cuenta = CuentaBancaria.crear(request.getTitular());
        CuentaBancaria cuentaGuardada = cuentaRepositoryPort.save(cuenta);
        return new CrearCuentaResponse(cuentaGuardada.getId(),
                cuentaGuardada.getTitular(), cuentaGuardada.getSaldo(), cuentaGuardada.getFechaCreacion());
    }

    @Override
    public TransaccionResponse depositar(TransaccionRequest request, Long numeroCuenta) {
        CuentaBancaria cuenta = cuentaRepositoryPort.findById(numeroCuenta).orElseThrow(
                () -> new ValidacionDominioExcepcion("Cuenta no encontrada"));
        cuenta.depositar(request.getMonto());
        cuentaRepositoryPort.update(cuenta);
        Transaccion transaccion = new Transaccion(cuenta, TipoTransferencia.DEPOSITO, request.getMonto());
        Transaccion transaccionGuardada = transaccionRepositoryPort.save(transaccion);
        return new TransaccionResponse(transaccionGuardada.getId(), cuenta.getId(),
                TipoTransferencia.DEPOSITO, cuenta.getSaldo(), transaccionGuardada.getFecha());

    }

    @Override
    public TransaccionResponse retirar(TransaccionRequest request, Long numeroCuenta) {
        CuentaBancaria cuenta = cuentaRepositoryPort.findById(numeroCuenta).orElseThrow(
                () -> new ValidacionDominioExcepcion("Cuenta no encontrada"));
        cuenta.retirar(request.getMonto());
        cuentaRepositoryPort.update(cuenta);
        Transaccion transaccion = new Transaccion(cuenta, TipoTransferencia.RETIRO, request.getMonto());
        Transaccion transaccionGuardada = transaccionRepositoryPort.save(transaccion);
        return new TransaccionResponse(transaccionGuardada.getId(), cuenta.getId(),
                TipoTransferencia.RETIRO, cuenta.getSaldo(), transaccionGuardada.getFecha());
    }
}
