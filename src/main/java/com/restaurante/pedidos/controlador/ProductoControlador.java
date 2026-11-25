package com.restaurante.pedidos.controlador;

import com.restaurante.pedidos.dto.ProductoRequest;
import com.restaurante.pedidos.modelo.Producto;
import com.restaurante.pedidos.servicio.ProductoServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    public ProductoControlador(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    /** Catálogo. Opcional: ?categoria=Hamburguesas */
    @GetMapping
    public List<Producto> listar(@RequestParam(required = false) String categoria) {
        return productoServicio.listar(categoria);
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable String id) {
        return productoServicio.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@Valid @RequestBody ProductoRequest datos) {
        return productoServicio.crear(datos);
    }
}
