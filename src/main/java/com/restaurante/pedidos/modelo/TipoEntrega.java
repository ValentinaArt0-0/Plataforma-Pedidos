package com.restaurante.pedidos.modelo;

import java.util.List;

/**
 * GRASP Polimorfismo: cada tipo de entrega define sus propias etapas,
 * así el pedido no necesita preguntar con if/else qué tipo de entrega es.
 */
public enum TipoEntrega {

    RECOGER_EN_LOCAL(List.of(EstadoPedido.RECIBIDO, EstadoPedido.EN_PREPARACION, EstadoPedido.LISTO)),
    DOMICILIO(List.of(EstadoPedido.RECIBIDO, EstadoPedido.EN_PREPARACION, EstadoPedido.LISTO, EstadoPedido.EN_CAMINO));

    private final List<EstadoPedido> etapas;

    TipoEntrega(List<EstadoPedido> etapas) {
        this.etapas = etapas;
    }

    public List<EstadoPedido> etapas() {
        return etapas;
    }
}
