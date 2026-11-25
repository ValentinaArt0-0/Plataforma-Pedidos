package com.restaurante.pedidos.excepcion;

/** Se lanza cuando una operación viola una regla del negocio (responde 400). */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
