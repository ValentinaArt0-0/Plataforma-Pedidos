package com.restaurante.pedidos.excepcion;

import java.time.LocalDateTime;

public record RespuestaError(int estado, String mensaje, LocalDateTime fecha) {

    public RespuestaError(int estado, String mensaje) {
        this(estado, mensaje, LocalDateTime.now());
    }
}
