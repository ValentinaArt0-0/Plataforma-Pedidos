package com.restaurante.pedidos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * ingredientes: los ingredientes base que el cliente deja (si se omite, van todos).
 * complementos: nombres de los extras con costo adicional.
 */
public record AgregarItemRequest(
        @NotBlank(message = "es obligatorio") String productoId,
        @Min(value = 1, message = "debe ser al menos 1") int cantidad,
        List<String> ingredientes,
        List<String> complementos,
        String notas) {
}
