package com.cuentas_bancarias.domain.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias para Transaccion")
class TransaccionTest {

    private CuentaBancaria cuenta;
    private Transaccion transaccion;

    @BeforeEach
    void setUp() {
        cuenta = new CuentaBancaria("Carlos Ruiz");
        cuenta.setId(1L);
        cuenta.setSaldo(new BigDecimal("5000.00"));
    }

    @Test
    @DisplayName("Debe crear una transacción de depósito correctamente")
    void testCrearTransaccionDeposito() {
        // Act
        transaccion = new Transaccion(cuenta, TipoTransferencia.DEPOSITO, new BigDecimal("500.00"));

        // Assert
        assertNotNull(transaccion);
        assertEquals(cuenta, transaccion.getCuentaBancaria());
        assertEquals(TipoTransferencia.DEPOSITO, transaccion.getTipo());
        assertEquals(new BigDecimal("500.00"), transaccion.getMonto());
        assertNotNull(transaccion.getFecha());
    }

    @Test
    @DisplayName("Debe crear una transacción de retiro correctamente")
    void testCrearTransaccionRetiro() {
        // Act
        transaccion = new Transaccion(cuenta, TipoTransferencia.RETIRO, new BigDecimal("200.00"));

        // Assert
        assertNotNull(transaccion);
        assertEquals(cuenta, transaccion.getCuentaBancaria());
        assertEquals(TipoTransferencia.RETIRO, transaccion.getTipo());
        assertEquals(new BigDecimal("200.00"), transaccion.getMonto());
        assertNotNull(transaccion.getFecha());
    }

    @Test
    @DisplayName("Debe establecer la fecha actual en la transacción")
    void testFechaActual() {
        // Arrange
        LocalDateTime antesDeCrear = LocalDateTime.now();

        // Act
        transaccion = new Transaccion(cuenta, TipoTransferencia.DEPOSITO, new BigDecimal("100.00"));
        LocalDateTime despuesDeCrear = LocalDateTime.now();

        // Assert
        assertNotNull(transaccion.getFecha());
        assertFalse(transaccion.getFecha().isBefore(antesDeCrear));
        assertFalse(transaccion.getFecha().isAfter(despuesDeCrear.plusSeconds(1)));
    }

    @Test
    @DisplayName("Debe permitir montos con decimales")
    void testMontoConDecimales() {
        // Act
        transaccion = new Transaccion(cuenta, TipoTransferencia.DEPOSITO, new BigDecimal("1234.56"));

        // Assert
        assertEquals(new BigDecimal("1234.56"), transaccion.getMonto());
    }

    @Test
    @DisplayName("Debe mantener referencia a la cuenta bancaria")
    void testReferenciaACuenta() {
        // Act
        transaccion = new Transaccion(cuenta, TipoTransferencia.RETIRO, new BigDecimal("300.00"));

        // Assert
        assertEquals(cuenta, transaccion.getCuentaBancaria());
        assertEquals("Carlos Ruiz", transaccion.getCuentaBancaria().getTitular());
        assertEquals(1L, transaccion.getCuentaBancaria().getId());
    }

    @Test
    @DisplayName("Debe permitir establecer el ID de la transacción")
    void testEstablecerIdTransaccion() {
        // Act
        transaccion = new Transaccion(cuenta, TipoTransferencia.DEPOSITO, new BigDecimal("500.00"));
        transaccion.setId(10L);

        // Assert
        assertEquals(10L, transaccion.getId());
    }
}
