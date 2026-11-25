package com.restaurante.pedidos.modelo;

import java.math.BigDecimal;

/**
 * Extra que se puede añadir a un producto con un costo adicional (ej: queso extra).
 */
public record Complemento(String nombre, BigDecimal precio) {
}
