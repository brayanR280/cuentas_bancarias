package com.cuentas_bancarias.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearCuentaRequest {
    @NotBlank
    @Size(min = 1, max = 100, message = "El titular debe tener entre 1 y 100 caracteres")
    private String titular;
}
