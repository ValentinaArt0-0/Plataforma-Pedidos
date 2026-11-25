package com.restaurante.pedidos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Datos para registrarse. Se exige correo o teléfono (al menos uno). */
public record RegistroRequest(
        @NotBlank(message = "es obligatorio") String nombre,
        @Email(message = "no tiene un formato válido") String correo,
        String telefono,
        @NotBlank(message = "es obligatoria")
        @Size(min = 6, message = "debe tener al menos 6 caracteres") String contrasena) {
}
