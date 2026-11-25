package com.restaurante.pedidos.modelo;

import com.restaurante.pedidos.excepcion.ReglaNegocioException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private List<ItemPedido> unItem() {
        Producto pizza = new Producto("Pizza", "", "Pizzas", new BigDecimal("10000"), List.of("Queso"), List.of());
        Carrito carrito = new Carrito("usuario-1");
        carrito.agregarItem(pizza, 1, null, null, null);
        return carrito.getItems();
    }

    @Test
    void elTotalEsSubtotalMasImpuestosYEmpiezaRecibido() {
        Pedido pedido = new Pedido("usuario-1", unItem(), new BigDecimal("10000"), new BigDecimal("800"),
                MetodoPago.TARJETA, TipoEntrega.RECOGER_EN_LOCAL, null);

        assertEquals(new BigDecimal("10800"), pedido.getTotal());
        assertEquals(EstadoPedido.RECIBIDO, pedido.getEstado());
    }

    @Test
    void domicilioSinDireccionNoSePermite() {
        assertThrows(ReglaNegocioException.class, () -> new Pedido("usuario-1", unItem(),
                new BigDecimal("10000"), BigDecimal.ZERO, MetodoPago.PAGO_MOVIL, TipoEntrega.DOMICILIO, " "));
    }

    @Test
    void pedidoParaRecogerTerminaEnListo() {
        Pedido pedido = new Pedido("usuario-1", unItem(), new BigDecimal("10000"), BigDecimal.ZERO,
                MetodoPago.TARJETA, TipoEntrega.RECOGER_EN_LOCAL, null);

        pedido.avanzarEstado(); // EN_PREPARACION
        pedido.avanzarEstado(); // LISTO

        assertEquals(EstadoPedido.LISTO, pedido.getEstado());
        assertThrows(ReglaNegocioException.class, pedido::avanzarEstado);
    }

    @Test
    void lineaDeTiempoMarcaLasEtapasCompletadas() {
        Pedido pedido = new Pedido("usuario-1", unItem(), new BigDecimal("10000"), BigDecimal.ZERO,
                MetodoPago.TARJETA, TipoEntrega.DOMICILIO, "Calle 1 # 2-3");
        pedido.avanzarEstado(); // EN_PREPARACION

        List<EtapaLineaTiempo> linea = pedido.lineaDeTiempo();

        assertEquals(4, linea.size());
        assertTrue(linea.get(0).completada());
        assertTrue(linea.get(1).completada());
        assertTrue(linea.get(1).actual());
        assertFalse(linea.get(2).completada());
        assertNull(linea.get(3).fecha());
    }
}
