package com.cuentas_bancarias.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
public class CuentaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String titular;
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;
}
