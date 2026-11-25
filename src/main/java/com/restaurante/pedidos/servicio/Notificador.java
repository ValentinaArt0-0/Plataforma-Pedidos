package com.restaurante.pedidos.servicio;

import com.restaurante.pedidos.modelo.Pedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * GRASP Fabricación Pura: enviar notificaciones no es responsabilidad del pedido.
 * Por ahora la notificación se muestra en la consola del servidor; más adelante
 * podría enviarse por correo o SMS cambiando solo esta clase.
 */
@Component
public class Notificador {

    private static final Logger log = LoggerFactory.getLogger(Notificador.class);

    public void notificarCambioEstado(Pedido pedido) {
        log.info("NOTIFICACIÓN para el usuario {}: su pedido {} ahora está {}",
                pedido.getUsuarioId(), pedido.getId(), pedido.getEstado());
    }
}
