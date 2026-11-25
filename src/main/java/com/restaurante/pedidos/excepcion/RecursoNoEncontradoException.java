package com.restaurante.pedidos.excepcion;

/** Se lanza cuando no existe el recurso buscado (responde 404). */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
