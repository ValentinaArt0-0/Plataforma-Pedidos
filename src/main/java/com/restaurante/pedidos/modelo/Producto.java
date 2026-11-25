package com.restaurante.pedidos.modelo;

import com.restaurante.pedidos.excepcion.ReglaNegocioException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * GRASP Experto en Información: el producto conoce sus ingredientes y complementos,
 * por eso es él quien valida qué personalizaciones son posibles.
 */
@Document("productos")
public class Producto {

    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private BigDecimal precio;
    private List<String> ingredientesBase = new ArrayList<>();
    private List<Complemento> complementos = new ArrayList<>();
    private boolean disponible;

    protected Producto() {
    }

    public Producto(String nombre, String descripcion, String categoria, BigDecimal precio,
                    List<String> ingredientesBase, List<Complemento> complementos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.ingredientesBase = ingredientesBase == null ? new ArrayList<>() : new ArrayList<>(ingredientesBase);
        this.complementos = complementos == null ? new ArrayList<>() : new ArrayList<>(complementos);
        this.disponible = true;
    }

    /** Verifica que cada ingrediente elegido pertenezca a la receta del producto. */
    public void validarIngredientes(List<String> elegidos) {
        for (String ingrediente : elegidos) {
            if (!ingredientesBase.contains(ingrediente)) {
                throw new ReglaNegocioException(
                        "El ingrediente '" + ingrediente + "' no pertenece a " + nombre);
            }
        }
    }

    /** Convierte los nombres de complementos pedidos en complementos reales (con su precio). */
    public List<Complemento> buscarComplementos(List<String> nombres) {
        List<Complemento> encontrados = new ArrayList<>();
        for (String nombreComplemento : nombres) {
            Complemento complemento = complementos.stream()
                    .filter(c -> c.nombre().equalsIgnoreCase(nombreComplemento))
                    .findFirst()
                    .orElseThrow(() -> new ReglaNegocioException(
                            "El complemento '" + nombreComplemento + "' no está disponible para " + nombre));
            encontrados.add(complemento);
        }
        return encontrados;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public List<String> getIngredientesBase() {
        return ingredientesBase;
    }

    public List<Complemento> getComplementos() {
        return complementos;
    }

    public boolean isDisponible() {
        return disponible;
    }
}
