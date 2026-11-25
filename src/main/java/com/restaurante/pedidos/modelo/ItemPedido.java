package com.restaurante.pedidos.modelo;

import com.restaurante.pedidos.excepcion.ReglaNegocioException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Un producto personalizado dentro del carrito o del pedido.
 * GRASP Experto en Información: calcula su propio precio porque tiene
 * el precio base, los complementos y la cantidad.
 */
public class ItemPedido {

    private String id;
    private String productoId;
    private String nombreProducto;
    private BigDecimal precioBase;
    private int cantidad;
    private List<String> ingredientes = new ArrayList<>();
    private List<Complemento> complementos = new ArrayList<>();
    private String notas;

    protected ItemPedido() {
    }

    public ItemPedido(Producto producto, int cantidad, List<String> ingredientes,
                      List<Complemento> complementos, String notas) {
        this.id = UUID.randomUUID().toString();
        this.productoId = producto.getId();
        this.nombreProducto = producto.getNombre();
        this.precioBase = producto.getPrecio();
        this.ingredientes = new ArrayList<>(ingredientes);
        this.complementos = new ArrayList<>(complementos);
        this.notas = notas;
        cambiarCantidad(cantidad);
    }

    public void cambiarCantidad(int nuevaCantidad) {
        if (nuevaCantidad < 1) {
            throw new ReglaNegocioException("La cantidad debe ser al menos 1");
        }
        this.cantidad = nuevaCantidad;
    }

    /** Precio de una unidad: precio base + complementos. */
    public BigDecimal getPrecioUnitario() {
        BigDecimal extras = complementos.stream()
                .map(Complemento::precio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return precioBase.add(extras);
    }

    public BigDecimal getSubtotal() {
        return getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad));
    }

    public String getId() {
        return id;
    }

    public String getProductoId() {
        return productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public int getCantidad() {
        return cantidad;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public List<Complemento> getComplementos() {
        return complementos;
    }

    public String getNotas() {
        return notas;
    }
}
