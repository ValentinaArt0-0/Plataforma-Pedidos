package com.restaurante.pedidos.servicio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * GRASP Fabricación Pura: el cálculo de impuestos no pertenece a ninguna clase del
 * dominio, así que se crea esta clase solo para esa responsabilidad.
 * La tasa se configura en application.properties (pedidos.impuesto.tasa).
 */
@Component
public class CalculadoraImpuestoPorcentaje implements CalculadoraImpuestos {

    private final BigDecimal tasa;

    public CalculadoraImpuestoPorcentaje(@Value("${pedidos.impuesto.tasa}") BigDecimal tasa) {
        this.tasa = tasa;
    }

    @Override
    public BigDecimal calcular(BigDecimal subtotal) {
        return subtotal.multiply(tasa).setScale(2, RoundingMode.HALF_UP);
    }
}
