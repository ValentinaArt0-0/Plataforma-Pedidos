package com.restaurante.pedidos.excepcion;

/** Se lanza cuando el usuario o la contraseña no coinciden (responde 401). */
public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException() {
        super("Usuario o contraseña incorrectos");
    }
}
