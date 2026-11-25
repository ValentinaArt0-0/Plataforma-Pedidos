# Plataforma de Pedidos para un Restaurante (Backend)

API REST para gestionar los pedidos de un restaurante: registro e inicio de sesión, catálogo,
personalización de productos, carrito, confirmación del pedido con impuestos y seguimiento
del estado con línea de tiempo.

**Tecnologías:** Java 21, Spring Boot 3, MongoDB, Postman.

## Cómo ejecutarlo

1. Tener instalado **Java 21**, **Maven** y **MongoDB** corriendo en `localhost:27017`.
   (Para usar otra base, por ejemplo MongoDB Atlas, define la variable de entorno `MONGODB_URI`.)
2. Ejecutar:
   ```bash
   mvn spring-boot:run
   ```
3. La API queda en `http://localhost:8080/api`. Al primer arranque se carga un menú de ejemplo.
4. En Postman, importar `postman/Plataforma-Pedidos.postman_collection.json` y ejecutar las carpetas en orden.

Pruebas unitarias del dominio: `mvn test`

## Arquitectura

Arquitectura en capas: cada capa solo conoce a la de abajo.

```
Postman ──► controlador ──► servicio ──► repositorio ──► MongoDB
                               │
                               ▼
                             modelo  (reglas del negocio)
```

| Paquete       | Responsabilidad |
|---------------|-----------------|
| `controlador` | Recibe las peticiones HTTP y las delega. No tiene lógica de negocio. |
| `servicio`    | Coordina cada caso de uso: carga datos, llama al modelo y guarda. |
| `modelo`      | Entidades del dominio con sus reglas (precios, estados, validaciones). |
| `repositorio` | Acceso a MongoDB con Spring Data. |
| `dto`         | Datos que entran y salen de la API, con validaciones. |
| `excepcion`   | Errores del negocio y su conversión a respuestas JSON (404, 400, 401). |
| `config`      | Cifrado de contraseñas y datos iniciales del menú. |

## Patrones GRASP aplicados

| Patrón | Dónde | Por qué |
|--------|-------|---------|
| **Experto en Información** | `Carrito.getSubtotal()`, `ItemPedido.getPrecioUnitario()`, `Producto.validarIngredientes()`, `Pedido.avanzarEstado()` y `Pedido.lineaDeTiempo()` | Cada clase hace los cálculos con la información que ella misma tiene. |
| **Creador** | `Carrito.agregarItem()` crea los `ItemPedido` | El carrito contiene los ítems, por eso es el que los crea. |
| **Controlador** | `AutenticacionControlador`, `ProductoControlador`, `CarritoControlador`, `PedidoControlador` | Son el punto de entrada de cada caso de uso y delegan en los servicios. |
| **Bajo Acoplamiento** | Inyección por constructor; los servicios dependen de interfaces (`CalculadoraImpuestos`, repositorios) | Cambiar una pieza no obliga a cambiar las demás. |
| **Alta Cohesión** | Un servicio por área (usuarios, productos, carrito, pedidos) y `ManejadorErrores` para los errores | Cada clase tiene una responsabilidad clara. |
| **Polimorfismo** | `TipoEntrega` define sus propias etapas | Recoger termina en `LISTO` y domicilio sigue a `EN_CAMINO`, sin `if/else` en el pedido. |
| **Fabricación Pura** | `CalculadoraImpuestoPorcentaje`, `Notificador`, repositorios | Son responsabilidades que no pertenecen a ninguna entidad del dominio. |
| **Indirección** | Los servicios están entre los controladores y los repositorios | El controlador no habla directo con la base de datos. |
| **Variaciones Protegidas** | Interfaz `CalculadoraImpuestos` | Si cambia el impuesto, se cambia la implementación sin tocar `PedidoServicio`. |

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST   | `/api/auth/registro` | Registrar usuario (correo o teléfono) |
| POST   | `/api/auth/login` | Iniciar sesión con correo o teléfono |
| GET    | `/api/productos?categoria=` | Catálogo (filtro opcional por categoría) |
| GET    | `/api/productos/{id}` | Detalle de un producto |
| POST   | `/api/productos` | Crear producto (administración) |
| GET    | `/api/carritos/{usuarioId}` | Ver carrito con subtotal |
| POST   | `/api/carritos/{usuarioId}/items` | Agregar producto personalizado |
| PATCH  | `/api/carritos/{usuarioId}/items/{itemId}` | Cambiar cantidad (+ / –) |
| DELETE | `/api/carritos/{usuarioId}/items/{itemId}` | Quitar un producto |
| DELETE | `/api/carritos/{usuarioId}` | Vaciar carrito |
| POST   | `/api/pedidos` | Confirmar pedido (pago, entrega, impuestos y total) |
| GET    | `/api/pedidos/{id}` | Ver pedido |
| GET    | `/api/pedidos/usuario/{usuarioId}` | Pedidos de un usuario |
| GET    | `/api/pedidos/{id}/linea-tiempo` | Línea de tiempo del estado |
| PATCH  | `/api/pedidos/{id}/avanzar-estado` | Pasar a la siguiente etapa (personal del restaurante) |
| PATCH  | `/api/pedidos/{id}/notificaciones` | Activar o desactivar notificaciones |

**Valores permitidos**
- `metodoPago`: `TARJETA`, `PAGO_MOVIL`
- `tipoEntrega`: `RECOGER_EN_LOCAL`, `DOMICILIO` (este exige `direccionEntrega`)
- Estados: `RECIBIDO` → `EN_PREPARACION` → `LISTO` → `EN_CAMINO` (solo domicilio)

## Decisiones de alcance

Para mantener el proyecto simple y fácil de exponer:
- **No hay frontend**: todo se prueba desde Postman.
- **No hay inicio de sesión con Google/Facebook** ni tokens JWT: el login valida las credenciales
  (contraseña cifrada con BCrypt) y devuelve el usuario, cuyo `id` se usa en las demás rutas.
- **El pago no se procesa de verdad**: solo se registra el método elegido.
- **Las notificaciones se escriben en la consola del servidor** (clase `Notificador`), listas para
  cambiarse por correo o SMS sin tocar el resto del código.
