package com.cuentas_bancarias.domain.entities;

import com.cuentas_bancarias.domain.exception.FondosInsuficientesExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias para CuentaBancaria")
class CuentaBancariaTest {

    private CuentaBancaria cuenta;

    @BeforeEach
    void setUp() {
        cuenta = new CuentaBancaria("Juan Pérez");
    }

    @Test
    @DisplayName("Debe crear una cuenta con saldo inicial en cero")
    void testCrearCuentaConSaldoInicial() {
        assertNotNull(cuenta);
        assertEquals("Juan Pérez", cuenta.getTitular());
        assertEquals(BigDecimal.ZERO, cuenta.getSaldo());
        assertNotNull(cuenta.getFechaCreacion());
    }

    @Test
    @DisplayName("Debe crear una cuenta usando el método estático crear()")
    void testCrearCuentaStatico() {
        CuentaBancaria cuentaCreada = CuentaBancaria.crear("María García");
        assertNotNull(cuentaCreada);
        assertEquals("María García", cuentaCreada.getTitular());
        assertEquals(BigDecimal.ZERO, cuentaCreada.getSaldo());
    }

    @Test
    @DisplayName("Debe depositar dinero correctamente")
    void testDepositarDinero() {
        BigDecimal monto = new BigDecimal("1000.00");
        cuenta.depositar(monto);
        assertEquals(new BigDecimal("1000.00"), cuenta.getSaldo());
    }

    @Test
    @DisplayName("Debe acumular múltiples depósitos")
    void testMultiplesDepositos() {
        cuenta.depositar(new BigDecimal("500.00"));
        cuenta.depositar(new BigDecimal("300.00"));
        cuenta.depositar(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("1000.00"), cuenta.getSaldo());
    }

    @Test
    @DisplayName("Debe retirar dinero cuando hay saldo suficiente")
    void testRetirarConSaldoSuficiente() {
        cuenta.depositar(new BigDecimal("1000.00"));
        cuenta.retirar(new BigDecimal("300.00"));
        assertEquals(new BigDecimal("700.00"), cuenta.getSaldo());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando se intenta retirar más de lo disponible")
    void testRetirarConSaldoInsuficiente() {
        cuenta.depositar(new BigDecimal("500.00"));
        assertThrows(FondosInsuficientesExcepcion.class, () -> {
            cuenta.retirar(new BigDecimal("1000.00"));
        });
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando se intenta retirar de una cuenta vacía")
    void testRetirarDeCuentaVacia() {
        assertThrows(FondosInsuficientesExcepcion.class, () -> {
            cuenta.retirar(new BigDecimal("100.00"));
        });
    }

    @Test
    @DisplayName("Debe validar saldo correctamente")
    void testValidarSaldoConMontoSuficiente() {
        cuenta.depositar(new BigDecimal("1000.00"));
        assertDoesNotThrow(() -> cuenta.validarSaldo(new BigDecimal("500.00")));
    }

    @Test
    @DisplayName("Debe fallar validación cuando monto es mayor al saldo")
    void testValidarSaldoConMontoInsuficiente() {
        cuenta.depositar(new BigDecimal("500.00"));
        assertThrows(FondosInsuficientesExcepcion.class, () -> {
            cuenta.validarSaldo(new BigDecimal("1000.00"));
        });
    }

    @Test
    @DisplayName("Debe permitir depósitos con decimales")
    void testDepositoConDecimales() {
        cuenta.depositar(new BigDecimal("100.50"));
        cuenta.depositar(new BigDecimal("50.75"));
        assertEquals(new BigDecimal("151.25"), cuenta.getSaldo());
    }

    @Test
    @DisplayName("Debe permitir retiros con decimales")
    void testRetiroConDecimales() {
        cuenta.depositar(new BigDecimal("500.00"));
        cuenta.retirar(new BigDecimal("125.50"));
        assertEquals(new BigDecimal("374.50"), cuenta.getSaldo());
    }
}
