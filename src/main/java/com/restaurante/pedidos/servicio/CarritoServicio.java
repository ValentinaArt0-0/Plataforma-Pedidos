package com.restaurante.pedidos.servicio;

import com.restaurante.pedidos.dto.AgregarItemRequest;
import com.restaurante.pedidos.modelo.Carrito;
import com.restaurante.pedidos.modelo.Producto;
import com.restaurante.pedidos.repositorio.CarritoRepositorio;
import org.springframework.stereotype.Service;

/**
 * Coordina el caso de uso del carrito: carga datos, delega la lógica en el
 * Carrito (que es el experto) y guarda el resultado.
 */
@Service
public class CarritoServicio {

    private final CarritoRepositorio carritoRepositorio;
    private final ProductoServicio productoServicio;
    private final UsuarioServicio usuarioServicio;

    public CarritoServicio(CarritoRepositorio carritoRepositorio, ProductoServicio productoServicio,
                           UsuarioServicio usuarioServicio) {
        this.carritoRepositorio = carritoRepositorio;
        this.productoServicio = productoServicio;
        this.usuarioServicio = usuarioServicio;
    }

    /** Devuelve el carrito del usuario; si aún no tiene, le da uno vacío. */
    public Carrito obtener(String usuarioId) {
        usuarioServicio.verificarQueExiste(usuarioId);
        return carritoRepositorio.findById(usuarioId).orElseGet(() -> new Carrito(usuarioId));
    }

    public Carrito agregarItem(String usuarioId, AgregarItemRequest datos) {
        Carrito carrito = obtener(usuarioId);
        Producto producto = productoServicio.buscar(datos.productoId());
        carrito.agregarItem(producto, datos.cantidad(), datos.ingredientes(), datos.complementos(), datos.notas());
        return carritoRepositorio.save(carrito);
    }

    public Carrito cambiarCantidad(String usuarioId, String itemId, int cantidad) {
        Carrito carrito = obtener(usuarioId);
        carrito.cambiarCantidad(itemId, cantidad);
        return carritoRepositorio.save(carrito);
    }

    public Carrito eliminarItem(String usuarioId, String itemId) {
        Carrito carrito = obtener(usuarioId);
        carrito.eliminarItem(itemId);
        return carritoRepositorio.save(carrito);
    }

    public Carrito vaciar(String usuarioId) {
        Carrito carrito = obtener(usuarioId);
        carrito.vaciar();
        return carritoRepositorio.save(carrito);
    }
}
