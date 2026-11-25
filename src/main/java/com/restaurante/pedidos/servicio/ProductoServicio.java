package com.restaurante.pedidos.servicio;

import com.restaurante.pedidos.dto.ProductoRequest;
import com.restaurante.pedidos.excepcion.RecursoNoEncontradoException;
import com.restaurante.pedidos.modelo.Producto;
import com.restaurante.pedidos.repositorio.ProductoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio {

    private final ProductoRepositorio productoRepositorio;

    public ProductoServicio(ProductoRepositorio productoRepositorio) {
        this.productoRepositorio = productoRepositorio;
    }

    public List<Producto> listar(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            return productoRepositorio.findByDisponibleTrue();
        }
        return productoRepositorio.findByCategoriaIgnoreCaseAndDisponibleTrue(categoria);
    }

    public Producto buscar(String id) {
        return productoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el producto " + id));
    }

    public Producto crear(ProductoRequest datos) {
        Producto producto = new Producto(datos.nombre(), datos.descripcion(), datos.categoria(),
                datos.precio(), datos.ingredientesBase(), datos.complementos());
        return productoRepositorio.save(producto);
    }
}
