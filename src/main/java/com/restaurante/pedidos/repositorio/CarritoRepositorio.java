package com.restaurante.pedidos.repositorio;

import com.restaurante.pedidos.modelo.Carrito;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarritoRepositorio extends MongoRepository<Carrito, String> {
}
