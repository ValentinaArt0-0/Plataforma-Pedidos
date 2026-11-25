package com.restaurante.pedidos.modelo;

import java.time.LocalDateTime;

/** Registro de cuándo el pedido llegó a cada estado (trazabilidad). */
public record CambioEstado(EstadoPedido estado, LocalDateTime fecha) {
}
