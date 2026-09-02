package com.cuentas_bancarias.infrastructure.adapters.input.controllers;

import com.cuentas_bancarias.application.dto.*;
import com.cuentas_bancarias.application.ports.input.ConsultaSaldoUseCase;
import com.cuentas_bancarias.application.ports.input.CrearCuentaUseCase;
import com.cuentas_bancarias.application.ports.input.DepositoUseCase;
import com.cuentas_bancarias.application.ports.input.RetiroUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class CuentaController {

    private final CrearCuentaUseCase crearCuentaUseCase;
    private final DepositoUseCase depositoUseCase;
    private final RetiroUseCase retiroUseCase;
    private final ConsultaSaldoUseCase consultaSaldoUseCase;

    @PostMapping
    public ResponseEntity<CrearCuentaResponse> crear(@Valid @RequestBody CrearCuentaRequest request) {
        CrearCuentaResponse response = crearCuentaUseCase.crearCuenta(request);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<TransaccionResponse> depositar(@Valid @RequestBody TransaccionRequest request, @PathVariable Long id) {
        TransaccionResponse response = depositoUseCase.depositar(request, id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<TransaccionResponse> retirar(@Valid @RequestBody TransaccionRequest request, @PathVariable Long id) {
        TransaccionResponse response = retiroUseCase.retirar(request, id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<ConsultarSaldoResponse> consultarSaldo(@PathVariable Long id) {
        ConsultarSaldoResponse response = consultaSaldoUseCase.consultarSaldo(id);
        return ResponseEntity.ok(response);
    }
}
