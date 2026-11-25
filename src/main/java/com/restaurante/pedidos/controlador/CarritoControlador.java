package com.restaurante.pedidos.controlador;

import com.restaurante.pedidos.dto.AgregarItemRequest;
import com.restaurante.pedidos.dto.CambiarCantidadRequest;
import com.restaurante.pedidos.modelo.Carrito;
import com.restaurante.pedidos.servicio.CarritoServicio;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carritos/{usuarioId}")
public class CarritoControlador {

    private final CarritoServicio carritoServicio;

    public CarritoControlador(CarritoServicio carritoServicio) {
        this.carritoServicio = carritoServicio;
    }

    @GetMapping
    public Carrito obtener(@PathVariable String usuarioId) {
        return carritoServicio.obtener(usuarioId);
    }

    @PostMapping("/items")
    public Carrito agregarItem(@PathVariable String usuarioId, @Valid @RequestBody AgregarItemRequest datos) {
        return carritoServicio.agregarItem(usuarioId, datos);
    }

    /** Botones + y –: se envía la nueva cantidad. */
    @PatchMapping("/items/{itemId}")
    public Carrito cambiarCantidad(@PathVariable String usuarioId, @PathVariable String itemId,
                                   @Valid @RequestBody CambiarCantidadRequest datos) {
        return carritoServicio.cambiarCantidad(usuarioId, itemId, datos.cantidad());
    }

    @DeleteMapping("/items/{itemId}")
    public Carrito eliminarItem(@PathVariable String usuarioId, @PathVariable String itemId) {
        return carritoServicio.eliminarItem(usuarioId, itemId);
    }

    @DeleteMapping
    public Carrito vaciar(@PathVariable String usuarioId) {
        return carritoServicio.vaciar(usuarioId);
    }
}
