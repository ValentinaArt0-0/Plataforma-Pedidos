package com.restaurante.pedidos.servicio;

import java.math.BigDecimal;

/**
 * GRASP Variaciones Protegidas: si cambia la forma de calcular impuestos,
 * se crea otra implementación y el resto del sistema no se entera.
 */
public interface CalculadoraImpuestos {

    BigDecimal calcular(BigDecimal subtotal);
}
