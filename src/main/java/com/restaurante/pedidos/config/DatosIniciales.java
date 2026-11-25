package com.restaurante.pedidos.config;

import com.restaurante.pedidos.modelo.Complemento;
import com.restaurante.pedidos.modelo.Producto;
import com.restaurante.pedidos.repositorio.ProductoRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Carga un menú de ejemplo la primera vez que arranca la aplicación. */
@Component
public class DatosIniciales implements CommandLineRunner {

    private final ProductoRepositorio productoRepositorio;

    public DatosIniciales(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    @Override
    public void run(String... args) {
        if (productoRepositorio.count() > 0) {
            return;
        }
        productoRepositorio.saveAll(List.of(
                new Producto("Hamburguesa Clásica", "Carne de res, pan artesanal y salsa de la casa",
                        "Hamburguesas", new BigDecimal("22000"),
                        List.of("Carne", "Lechuga", "Tomate", "Cebolla", "Salsa de la casa"),
                        List.of(new Complemento("Queso extra", new BigDecimal("3000")),
                                new Complemento("Tocineta", new BigDecimal("4000")))),
                new Producto("Pizza Margarita", "Salsa de tomate, mozzarella y albahaca",
                        "Pizzas", new BigDecimal("28000"),
                        List.of("Salsa de tomate", "Mozzarella", "Albahaca"),
                        List.of(new Complemento("Champiñones", new BigDecimal("3500")),
                                new Complemento("Borde de queso", new BigDecimal("5000")))),
                new Producto("Papas a la Francesa", "Porción de papas crocantes",
                        "Acompañamientos", new BigDecimal("8000"),
                        List.of("Papas", "Sal"),
                        List.of(new Complemento("Salsa cheddar", new BigDecimal("2500")))),
                new Producto("Limonada de Coco", "Bebida fría de la casa",
                        "Bebidas", new BigDecimal("9000"),
                        List.of("Limón", "Coco", "Hielo"),
                        List.of())
        ));
    }
}
