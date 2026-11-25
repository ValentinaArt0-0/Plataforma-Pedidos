package com.restaurante.pedidos.servicio;

import com.restaurante.pedidos.dto.LoginRequest;
import com.restaurante.pedidos.dto.RegistroRequest;
import com.restaurante.pedidos.dto.UsuarioResponse;
import com.restaurante.pedidos.excepcion.CredencialesInvalidasException;
import com.restaurante.pedidos.excepcion.RecursoNoEncontradoException;
import com.restaurante.pedidos.excepcion.ReglaNegocioException;
import com.restaurante.pedidos.modelo.Usuario;
import com.restaurante.pedidos.repositorio.UsuarioRepositorio;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder cifrador;

    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, PasswordEncoder cifrador) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.cifrador = cifrador;
    }

    public UsuarioResponse registrar(RegistroRequest datos) {
        String correo = limpiar(datos.correo());
        String telefono = limpiar(datos.telefono());

        if (correo == null && telefono == null) {
            throw new ReglaNegocioException("Debe registrarse con correo o con número de teléfono");
        }
        if (correo != null && usuarioRepositorio.existsByCorreo(correo)) {
            throw new ReglaNegocioException("Ya existe un usuario con ese correo");
        }
        if (telefono != null && usuarioRepositorio.existsByTelefono(telefono)) {
            throw new ReglaNegocioException("Ya existe un usuario con ese teléfono");
        }

        Usuario usuario = new Usuario(datos.nombre(), correo, telefono, cifrador.encode(datos.contrasena()));
        return UsuarioResponse.desde(usuarioRepositorio.save(usuario));
    }

    public UsuarioResponse iniciarSesion(LoginRequest datos) {
        String usuario = datos.usuario().trim().toLowerCase();
        return usuarioRepositorio.findByCorreoOrTelefono(usuario, usuario)
                .filter(u -> cifrador.matches(datos.contrasena(), u.getContrasena()))
                .map(UsuarioResponse::desde)
                .orElseThrow(CredencialesInvalidasException::new);
    }

    public void verificarQueExiste(String usuarioId) {
        if (!usuarioRepositorio.existsById(usuarioId)) {
            throw new RecursoNoEncontradoException("No existe el usuario " + usuarioId);
        }
    }

    /** Quita espacios, pasa a minúsculas y convierte el texto vacío en null. */
    private String limpiar(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim().toLowerCase();
    }
}
