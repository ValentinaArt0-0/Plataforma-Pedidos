package com.restaurante.pedidos.dto;

import com.restaurante.pedidos.modelo.MetodoPago;
import com.restaurante.pedidos.modelo.TipoEntrega;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** direccionEntrega solo es obligatoria cuando tipoEntrega es DOMICILIO. */
public record ConfirmarPedidoRequest(
        @NotBlank(message = "es obligatorio") String usuarioId,
        @NotNull(message = "es obligatorio (TARJETA o PAGO_MOVIL)") MetodoPago metodoPago,
        @NotNull(message = "es obligatorio (RECOGER_EN_LOCAL o DOMICILIO)") TipoEntrega tipoEntrega,
        String direccionEntrega) {
}
