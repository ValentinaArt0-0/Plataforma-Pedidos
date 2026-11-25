package com.restaurante.pedidos.dto;

import com.restaurante.pedidos.modelo.Usuario;

/** Lo que se devuelve del usuario: nunca incluye la contraseña. */
public record UsuarioResponse(String id, String nombre, String correo, String telefono) {

    public static UsuarioResponse desde(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getTelefono());
    }
}
