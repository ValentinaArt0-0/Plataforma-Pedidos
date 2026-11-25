package com.restaurante.pedidos.dto;

import com.restaurante.pedidos.modelo.Complemento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record ProductoRequest(
        @NotBlank(message = "es obligatorio") String nombre,
        String descripcion,
        @NotBlank(message = "es obligatoria") String categoria,
        @NotNull(message = "es obligatorio") @Positive(message = "debe ser mayor que 0") BigDecimal precio,
        List<String> ingredientesBase,
        List<Complemento> complementos) {
}
