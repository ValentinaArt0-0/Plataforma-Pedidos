package com.restaurante.pedidos.controlador;

import com.restaurante.pedidos.dto.LoginRequest;
import com.restaurante.pedidos.dto.RegistroRequest;
import com.restaurante.pedidos.dto.UsuarioResponse;
import com.restaurante.pedidos.servicio.UsuarioServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * GRASP Controlador: recibe las peticiones HTTP y las delega al servicio.
 * No contiene lógica de negocio.
 */
@RestController
@RequestMapping("/api/auth")
public class AutenticacionControlador {

    private final UsuarioServicio usuarioServicio;

    public AutenticacionControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse registrar(@Valid @RequestBody RegistroRequest datos) {
        return usuarioServicio.registrar(datos);
    }

    @PostMapping("/login")
    public UsuarioResponse iniciarSesion(@Valid @RequestBody LoginRequest datos) {
        return usuarioServicio.iniciarSesion(datos);
    }
}
