package com.restaurante.pedidos.controlador;

import com.restaurante.pedidos.dto.ConfirmarPedidoRequest;
import com.restaurante.pedidos.dto.NotificacionesRequest;
import com.restaurante.pedidos.modelo.EtapaLineaTiempo;
import com.restaurante.pedidos.modelo.Pedido;
import com.restaurante.pedidos.servicio.PedidoServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoControlador {

    private final PedidoServicio pedidoServicio;

    public PedidoControlador(PedidoServicio pedidoServicio) {
        this.pedidoServicio = pedidoServicio;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido confirmar(@Valid @RequestBody ConfirmarPedidoRequest datos) {
        return pedidoServicio.confirmar(datos);
    }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable String id) {
        return pedidoServicio.buscar(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Pedido> listarPorUsuario(@PathVariable String usuarioId) {
        return pedidoServicio.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}/linea-tiempo")
    public List<EtapaLineaTiempo> lineaDeTiempo(@PathVariable String id) {
        return pedidoServicio.lineaDeTiempo(id);
    }

    @PatchMapping("/{id}/avanzar-estado")
    public Pedido avanzarEstado(@PathVariable String id) {
        return pedidoServicio.avanzarEstado(id);
    }

    @PatchMapping("/{id}/notificaciones")
    public Pedido cambiarNotificaciones(@PathVariable String id, @RequestBody NotificacionesRequest datos) {
        return pedidoServicio.cambiarNotificaciones(id, datos.activar());
    }
}
