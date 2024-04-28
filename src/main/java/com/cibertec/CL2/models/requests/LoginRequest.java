package com.cibertec.CL2.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "El nombre de usuario es requerido")
    @Size(max = 255, message = "Máximo 255 caracteres")
    private String username;

    @NotBlank(message = "La clave es requerida")
    @Size(max = 255, message = "Máximo 255 caracteres")
    private String key;
}
