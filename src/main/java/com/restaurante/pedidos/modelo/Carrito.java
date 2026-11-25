package com.restaurante.pedidos.modelo;

import com.restaurante.pedidos.excepcion.RecursoNoEncontradoException;
import com.restaurante.pedidos.excepcion.ReglaNegocioException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Carrito de compras de un usuario (uno por usuario, su id es el id del usuario).
 *
 * GRASP Creador: el carrito contiene los ítems, por eso es quien los crea.
 * GRASP Experto en Información: conoce sus ítems, por eso calcula el subtotal.
 */
@Document("carritos")
public class Carrito {

    @Id
    private String usuarioId;
    private List<ItemPedido> items = new ArrayList<>();

    protected Carrito() {
    }

    public Carrito(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public ItemPedido agregarItem(Producto producto, int cantidad, List<String> ingredientes,
                                  List<String> nombresComplementos, String notas) {
        if (!producto.isDisponible()) {
            throw new ReglaNegocioException("El producto " + producto.getNombre() + " no está disponible");
        }
        // Si el cliente no personaliza los ingredientes, se usan los de la receta original
        List<String> ingredientesElegidos = (ingredientes == null) ? producto.getIngredientesBase() : ingredientes;
        producto.validarIngredientes(ingredientesElegidos);

        List<Complemento> complementos = producto.buscarComplementos(
                nombresComplementos == null ? List.of() : nombresComplementos);

        ItemPedido item = new ItemPedido(producto, cantidad, ingredientesElegidos, complementos, notas);
        items.add(item);
        return item;
    }

    public void cambiarCantidad(String itemId, int cantidad) {
        buscarItem(itemId).cambiarCantidad(cantidad);
    }

    public void eliminarItem(String itemId) {
        items.remove(buscarItem(itemId));
    }

    public void vaciar() {
        items.clear();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ItemPedido buscarItem(String itemId) {
        return items.stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el ítem " + itemId + " en el carrito"));
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public List<ItemPedido> getItems() {
        return items;
    }
}
