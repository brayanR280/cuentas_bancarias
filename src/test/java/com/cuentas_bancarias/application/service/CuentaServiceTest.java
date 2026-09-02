package com.cuentas_bancarias.application.service;

import com.cuentas_bancarias.application.dto.*;
import com.cuentas_bancarias.application.ports.output.CuentaRepositoryPort;
import com.cuentas_bancarias.application.ports.output.TransaccionRepositoryPort;
import com.cuentas_bancarias.domain.entities.CuentaBancaria;
import com.cuentas_bancarias.domain.entities.TipoTransferencia;
import com.cuentas_bancarias.domain.entities.Transaccion;
import com.cuentas_bancarias.domain.exception.ValidacionDominioExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas unitarias para CuentaService")
@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {

    @Mock
    private CuentaRepositoryPort cuentaRepositoryPort;

    @Mock
    private TransaccionRepositoryPort transaccionRepositoryPort;

    @InjectMocks
    private CuentaService cuentaService;

    private CuentaBancaria cuentaDePrueba;
    private CrearCuentaRequest crearCuentaRequest;
    private TransaccionRequest transaccionRequest;

    @BeforeEach
    void setUp() {
        cuentaDePrueba = new CuentaBancaria("Pedro López");
        cuentaDePrueba.setId(1L);
        cuentaDePrueba.setSaldo(BigDecimal.valueOf(1000));
        cuentaDePrueba.setFechaCreacion(LocalDateTime.now());

        crearCuentaRequest = new CrearCuentaRequest();
        crearCuentaRequest.setTitular("Ana Martínez");

        transaccionRequest = new TransaccionRequest();
        transaccionRequest.setMonto(new BigDecimal("500.00"));
    }

    @Test
    @DisplayName("Debe crear una cuenta exitosamente")
    void testCrearCuentaExitosamente() {
        // Arrange
        CuentaBancaria cuentaGuardada = new CuentaBancaria("Ana Martínez");
        cuentaGuardada.setId(2L);
        cuentaGuardada.setFechaCreacion(LocalDateTime.now());
        when(cuentaRepositoryPort.save(any(CuentaBancaria.class))).thenReturn(cuentaGuardada);

        // Act
        CrearCuentaResponse response = cuentaService.crearCuenta(crearCuentaRequest);

        // Assert
        assertNotNull(response);
        assertEquals(2L, response.getNumeroCuenta());
        assertEquals("Ana Martínez", response.getTitular());
        assertEquals(BigDecimal.ZERO, response.getSaldo());
        verify(cuentaRepositoryPort, times(1)).save(any(CuentaBancaria.class));
    }

    @Test
    @DisplayName("Debe consultar el saldo de una cuenta existente")
    void testConsultarSaldoExitosamente() {
        // Arrange
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuentaDePrueba));

        // Act
        ConsultarSaldoResponse response = cuentaService.consultarSaldo(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getCuentaId());
        assertEquals("Pedro López", response.getTitular());
        assertEquals(new BigDecimal("1000"), response.getSaldo());
        verify(cuentaRepositoryPort, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando intenta consultar saldo de cuenta inexistente")
    void testConsultarSaldoCuentaInexistente() {
        // Arrange
        when(cuentaRepositoryPort.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ValidacionDominioExcepcion.class, () -> {
            cuentaService.consultarSaldo(999L);
        });
        verify(cuentaRepositoryPort, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Debe realizar un depósito exitosamente")
    void testDepositarExitosamente() {
        // Arrange
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuentaDePrueba));
        
        Transaccion transaccionGuardada = new Transaccion(cuentaDePrueba, TipoTransferencia.DEPOSITO, transaccionRequest.getMonto());
        transaccionGuardada.setId(1L);
        when(transaccionRepositoryPort.save(any(Transaccion.class))).thenReturn(transaccionGuardada);

        // Act
        TransaccionResponse response = cuentaService.depositar(transaccionRequest, 1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getCuentaId());
        assertEquals(TipoTransferencia.DEPOSITO, response.getTipo());
        assertEquals(new BigDecimal("1500.00"), response.getNuevoSaldo());
        verify(cuentaRepositoryPort, times(1)).findById(1L);
        verify(cuentaRepositoryPort, times(1)).update(any(CuentaBancaria.class));
        verify(transaccionRepositoryPort, times(1)).save(any(Transaccion.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando se deposita en cuenta inexistente")
    void testDepositarEnCuentaInexistente() {
        // Arrange
        when(cuentaRepositoryPort.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ValidacionDominioExcepcion.class, () -> {
            cuentaService.depositar(transaccionRequest, 999L);
        });
        verify(cuentaRepositoryPort, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Debe realizar un retiro exitosamente")
    void testRetirarExitosamente() {
        // Arrange
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuentaDePrueba));
        
        Transaccion transaccionGuardada = new Transaccion(cuentaDePrueba, TipoTransferencia.RETIRO, transaccionRequest.getMonto());
        transaccionGuardada.setId(2L);
        when(transaccionRepositoryPort.save(any(Transaccion.class))).thenReturn(transaccionGuardada);

        // Act
        TransaccionResponse response = cuentaService.retirar(transaccionRequest, 1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getCuentaId());
        assertEquals(TipoTransferencia.RETIRO, response.getTipo());
        assertEquals(new BigDecimal("500.00"), response.getNuevoSaldo());
        verify(cuentaRepositoryPort, times(1)).findById(1L);
        verify(cuentaRepositoryPort, times(1)).update(any(CuentaBancaria.class));
        verify(transaccionRepositoryPort, times(1)).save(any(Transaccion.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando se intenta retirar de cuenta inexistente")
    void testRetirarDeCuentaInexistente() {
        // Arrange
        when(cuentaRepositoryPort.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ValidacionDominioExcepcion.class, () -> {
            cuentaService.retirar(transaccionRequest, 999L);
        });
        verify(cuentaRepositoryPort, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando intenta retirar más dinero del disponible")
    void testRetirarConFondosInsuficientes() {
        // Arrange
        TransaccionRequest montoAlto = new TransaccionRequest();
        montoAlto.setMonto(new BigDecimal("2000.00"));
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuentaDePrueba));

        // Act & Assert
        assertThrows(Exception.class, () -> {
            cuentaService.retirar(montoAlto, 1L);
        });
        verify(cuentaRepositoryPort, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe actualizar correctamente el saldo después de depositar")
    void testSaldoActualizado_DespuesDeDepositar() {
        // Arrange
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuentaDePrueba));
        
        Transaccion transaccionGuardada = new Transaccion(cuentaDePrueba, TipoTransferencia.DEPOSITO, transaccionRequest.getMonto());
        transaccionGuardada.setId(1L);
        when(transaccionRepositoryPort.save(any(Transaccion.class))).thenReturn(transaccionGuardada);

        // Act
        TransaccionResponse response = cuentaService.depositar(transaccionRequest, 1L);

        // Assert
        assertEquals(new BigDecimal("1500.00"), response.getNuevoSaldo());
    }

    @Test
    @DisplayName("Debe actualizar correctamente el saldo después de retirar")
    void testSaldoActualizado_DespuesDeRetirar() {
        // Arrange
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuentaDePrueba));
        
        Transaccion transaccionGuardada = new Transaccion(cuentaDePrueba, TipoTransferencia.RETIRO, transaccionRequest.getMonto());
        transaccionGuardada.setId(2L);
        when(transaccionRepositoryPort.save(any(Transaccion.class))).thenReturn(transaccionGuardada);

        // Act
        TransaccionResponse response = cuentaService.retirar(transaccionRequest, 1L);

        // Assert
        assertEquals(new BigDecimal("500.00"), response.getNuevoSaldo());
    }

    @Test
    @DisplayName("Debe guardar la transacción después de un depósito")
    void testGuardarTransaccion_Deposito() {
        // Arrange
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuentaDePrueba));
        
        Transaccion transaccionGuardada = new Transaccion(cuentaDePrueba, TipoTransferencia.DEPOSITO, transaccionRequest.getMonto());
        transaccionGuardada.setId(1L);
        when(transaccionRepositoryPort.save(any(Transaccion.class))).thenReturn(transaccionGuardada);

        // Act
        cuentaService.depositar(transaccionRequest, 1L);

        // Assert
        verify(transaccionRepositoryPort, times(1)).save(any(Transaccion.class));
    }

    @Test
    @DisplayName("Debe guardar la transacción después de un retiro")
    void testGuardarTransaccion_Retiro() {
        // Arrange
        when(cuentaRepositoryPort.findById(1L)).thenReturn(Optional.of(cuentaDePrueba));
        
        Transaccion transaccionGuardada = new Transaccion(cuentaDePrueba, TipoTransferencia.RETIRO, transaccionRequest.getMonto());
        transaccionGuardada.setId(2L);
        when(transaccionRepositoryPort.save(any(Transaccion.class))).thenReturn(transaccionGuardada);

        // Act
        cuentaService.retirar(transaccionRequest, 1L);

        // Assert
        verify(transaccionRepositoryPort, times(1)).save(any(Transaccion.class));
    }
}
