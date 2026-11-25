package com.restaurante.pedidos.dto;

import jakarta.validation.constraints.Min;

public record CambiarCantidadRequest(@Min(value = 1, message = "debe ser al menos 1") int cantidad) {
}
