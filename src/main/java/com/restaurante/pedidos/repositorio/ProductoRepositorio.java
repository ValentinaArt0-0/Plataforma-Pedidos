package com.restaurante.pedidos.repositorio;

import com.restaurante.pedidos.modelo.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductoRepositorio extends MongoRepository<Producto, String> {

    List<Producto> findByDisponibleTrue();

    List<Producto> findByCategoriaIgnoreCaseAndDisponibleTrue(String categoria);
}
