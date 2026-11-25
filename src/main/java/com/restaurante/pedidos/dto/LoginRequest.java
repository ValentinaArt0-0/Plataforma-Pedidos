package com.restaurante.pedidos.dto;

import jakarta.validation.constraints.NotBlank;

/** "usuario" puede ser el correo o el teléfono. */
public record LoginRequest(
        @NotBlank(message = "es obligatorio (correo o teléfono)") String usuario,
        @NotBlank(message = "es obligatoria") String contrasena) {
}
