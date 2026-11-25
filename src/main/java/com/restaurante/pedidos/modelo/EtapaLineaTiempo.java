package com.restaurante.pedidos.modelo;

import java.time.LocalDateTime;

/** Una etapa de la línea de tiempo del pedido. La fecha es null si aún no se alcanza. */
public record EtapaLineaTiempo(EstadoPedido estado, boolean completada, boolean actual, LocalDateTime fecha) {
}
