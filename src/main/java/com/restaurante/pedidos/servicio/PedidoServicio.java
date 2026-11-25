package com.restaurante.pedidos.servicio;

import com.restaurante.pedidos.dto.ConfirmarPedidoRequest;
import com.restaurante.pedidos.excepcion.RecursoNoEncontradoException;
import com.restaurante.pedidos.modelo.Carrito;
import com.restaurante.pedidos.modelo.EtapaLineaTiempo;
import com.restaurante.pedidos.modelo.Pedido;
import com.restaurante.pedidos.repositorio.PedidoRepositorio;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoServicio {

    private final PedidoRepositorio pedidoRepositorio;
    private final CarritoServicio carritoServicio;
    private final CalculadoraImpuestos calculadoraImpuestos;
    private final Notificador notificador;

    public PedidoServicio(PedidoRepositorio pedidoRepositorio, CarritoServicio carritoServicio,
                          CalculadoraImpuestos calculadoraImpuestos, Notificador notificador) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.carritoServicio = carritoServicio;
        this.calculadoraImpuestos = calculadoraImpuestos;
        this.notificador = notificador;
    }

    /** Convierte el carrito del usuario en un pedido y vacía el carrito. */
    public Pedido confirmar(ConfirmarPedidoRequest datos) {
        Carrito carrito = carritoServicio.obtener(datos.usuarioId());
        BigDecimal subtotal = carrito.getSubtotal();
        BigDecimal impuestos = calculadoraImpuestos.calcular(subtotal);

        Pedido pedido = new Pedido(datos.usuarioId(), carrito.getItems(), subtotal, impuestos,
                datos.metodoPago(), datos.tipoEntrega(), datos.direccionEntrega());
        Pedido guardado = pedidoRepositorio.save(pedido);

        carritoServicio.vaciar(datos.usuarioId());
        return guardado;
    }

    public Pedido buscar(String id) {
        return pedidoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el pedido " + id));
    }

    public List<Pedido> listarPorUsuario(String usuarioId) {
        return pedidoRepositorio.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);
    }

    public List<EtapaLineaTiempo> lineaDeTiempo(String id) {
        return buscar(id).lineaDeTiempo();
    }

    /** Lo usa el personal del restaurante para mover el pedido a la siguiente etapa. */
    public Pedido avanzarEstado(String id) {
        Pedido pedido = buscar(id);
        pedido.avanzarEstado();
        Pedido guardado = pedidoRepositorio.save(pedido);
        if (guardado.isNotificacionesActivas()) {
            notificador.notificarCambioEstado(guardado);
        }
        return guardado;
    }

    public Pedido cambiarNotificaciones(String id, boolean activar) {
        Pedido pedido = buscar(id);
        pedido.cambiarNotificaciones(activar);
        return pedidoRepositorio.save(pedido);
    }
}
