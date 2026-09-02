package com.cuentas_bancarias.application.dto;

import com.cuentas_bancarias.domain.entities.TipoTransferencia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransaccionResponse {
    private Long transaccionId;
    private Long cuentaId;
    private TipoTransferencia tipo;
    private BigDecimal nuevoSaldo;
    private LocalDateTime fecha;
}
