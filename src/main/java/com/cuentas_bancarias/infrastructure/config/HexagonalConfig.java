package com.cuentas_bancarias.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Configuración principal de la arquitectura hexagonal.
 * Define los componentes y beans de la aplicación.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.cuentas_bancarias.application.service",
        "com.cuentas_bancarias.infrastructure.adapters",
        "com.cuentas_bancarias.infrastructure.config"
})
public class HexagonalConfig {
}
