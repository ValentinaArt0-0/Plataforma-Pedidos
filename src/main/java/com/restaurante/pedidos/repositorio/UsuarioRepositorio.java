package com.restaurante.pedidos.repositorio;

import com.restaurante.pedidos.modelo.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {

    boolean existsByCorreo(String correo);

    boolean existsByTelefono(String telefono);

    Optional<Usuario> findByCorreoOrTelefono(String correo, String telefono);
}
