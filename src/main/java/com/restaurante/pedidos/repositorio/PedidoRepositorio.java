package com.restaurante.pedidos.repositorio;

import com.restaurante.pedidos.modelo.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PedidoRepositorio extends MongoRepository<Pedido, String> {

    List<Pedido> findByUsuarioIdOrderByFechaCreacionDesc(String usuarioId);
}
