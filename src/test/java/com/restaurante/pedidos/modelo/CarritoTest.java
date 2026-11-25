package com.restaurante.pedidos.modelo;

import com.restaurante.pedidos.excepcion.ReglaNegocioException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarritoTest {

    private final Producto hamburguesa = new Producto("Hamburguesa", "", "Hamburguesas", new BigDecimal("20000"),
            List.of("Carne", "Lechuga", "Tomate"),
            List.of(new Complemento("Queso extra", new BigDecimal("3000"))));

    @Test
    void calculaElSubtotalConComplementosYCantidad() {
        Carrito carrito = new Carrito("usuario-1");

        carrito.agregarItem(hamburguesa, 2, List.of("Carne", "Lechuga"), List.of("Queso extra"), "Sin sal");

        // (20000 + 3000) * 2
        assertEquals(new BigDecimal("46000"), carrito.getSubtotal());
    }

    @Test
    void siNoSeEligenIngredientesSeUsanLosDeLaReceta() {
        Carrito carrito = new Carrito("usuario-1");

        ItemPedido item = carrito.agregarItem(hamburguesa, 1, null, null, null);

        assertEquals(List.of("Carne", "Lechuga", "Tomate"), item.getIngredientes());
    }

    @Test
    void cambiarCantidadActualizaElSubtotal() {
        Carrito carrito = new Carrito("usuario-1");
        ItemPedido item = carrito.agregarItem(hamburguesa, 1, null, null, null);

        carrito.cambiarCantidad(item.getId(), 3);

        assertEquals(new BigDecimal("60000"), carrito.getSubtotal());
    }

    @Test
    void rechazaUnComplementoQueElProductoNoTiene() {
        Carrito carrito = new Carrito("usuario-1");

        assertThrows(ReglaNegocioException.class,
                () -> carrito.agregarItem(hamburguesa, 1, null, List.of("Piña"), null));
    }

    @Test
    void rechazaUnIngredienteQueNoEstaEnLaReceta() {
        Carrito carrito = new Carrito("usuario-1");

        assertThrows(ReglaNegocioException.class,
                () -> carrito.agregarItem(hamburguesa, 1, List.of("Pepinillos"), null, null));
    }
}
