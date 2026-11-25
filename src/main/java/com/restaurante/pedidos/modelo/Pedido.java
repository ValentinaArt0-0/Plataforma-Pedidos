package com.restaurante.pedidos.modelo;

import com.restaurante.pedidos.excepcion.ReglaNegocioException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * GRASP Experto en Información: el pedido conoce su estado, su tipo de entrega
 * y su historial, así que él decide cómo avanzar y arma su línea de tiempo.
 */
@Document("pedidos")
public class Pedido {

    @Id
    private String id;
    private String usuarioId;
    private List<ItemPedido> items = new ArrayList<>();
    private BigDecimal subtotal;
    private BigDecimal impuestos;
    private BigDecimal total;
    private MetodoPago metodoPago;
    private TipoEntrega tipoEntrega;
    private String direccionEntrega;
    private EstadoPedido estado;
    private List<CambioEstado> historial = new ArrayList<>();
    private boolean notificacionesActivas;
    private LocalDateTime fechaCreacion;

    protected Pedido() {
    }

    public Pedido(String usuarioId, List<ItemPedido> items, BigDecimal subtotal, BigDecimal impuestos,
                  MetodoPago metodoPago, TipoEntrega tipoEntrega, String direccionEntrega) {
        if (items.isEmpty()) {
            throw new ReglaNegocioException("No se puede confirmar un pedido con el carrito vacío");
        }
        if (tipoEntrega == TipoEntrega.DOMICILIO && (direccionEntrega == null || direccionEntrega.isBlank())) {
            throw new ReglaNegocioException("La entrega a domicilio necesita una dirección");
        }
        this.usuarioId = usuarioId;
        this.items = new ArrayList<>(items);
        this.subtotal = subtotal;
        this.impuestos = impuestos;
        this.total = subtotal.add(impuestos);
        this.metodoPago = metodoPago;
        this.tipoEntrega = tipoEntrega;
        this.direccionEntrega = (tipoEntrega == TipoEntrega.DOMICILIO) ? direccionEntrega : null;
        this.fechaCreacion = LocalDateTime.now();
        registrarEstado(EstadoPedido.RECIBIDO);
    }

    /** Pasa el pedido a la siguiente etapa según su tipo de entrega. */
    public void avanzarEstado() {
        List<EstadoPedido> etapas = tipoEntrega.etapas();
        int posicionActual = etapas.indexOf(estado);
        if (posicionActual == etapas.size() - 1) {
            throw new ReglaNegocioException("El pedido ya está en su última etapa: " + estado);
        }
        registrarEstado(etapas.get(posicionActual + 1));
    }

    /** Etapas del pedido marcando las completadas y la actual. */
    public List<EtapaLineaTiempo> lineaDeTiempo() {
        List<EstadoPedido> etapas = tipoEntrega.etapas();
        int posicionActual = etapas.indexOf(estado);
        List<EtapaLineaTiempo> linea = new ArrayList<>();
        for (int i = 0; i < etapas.size(); i++) {
            EstadoPedido etapa = etapas.get(i);
            linea.add(new EtapaLineaTiempo(etapa, i <= posicionActual, i == posicionActual, fechaDe(etapa)));
        }
        return linea;
    }

    public void cambiarNotificaciones(boolean activar) {
        this.notificacionesActivas = activar;
    }

    private void registrarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
        this.historial.add(new CambioEstado(nuevoEstado, LocalDateTime.now()));
    }

    private LocalDateTime fechaDe(EstadoPedido etapa) {
        return historial.stream()
                .filter(cambio -> cambio.estado() == etapa)
                .map(CambioEstado::fecha)
                .findFirst()
                .orElse(null);
    }

    public String getId() {
        return id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public TipoEntrega getTipoEntrega() {
        return tipoEntrega;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public List<CambioEstado> getHistorial() {
        return historial;
    }

    public boolean isNotificacionesActivas() {
        return notificacionesActivas;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
}
