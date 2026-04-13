# BookIt - Plataforma de Gestión de Eventos 🎤🎭

Plataforma modular desarrollada en **Java y JavaFX** para la gestión logística de eventos, reserva de entradas y administración de recintos. El proyecto implementa una arquitectura basada en patrones de diseño sólidos para garantizar escalabilidad y mantenimiento bajo los principios **SOLID**.

---

## 🚀 Cómo Ejecutar el Proyecto

### Requisitos Previos
*   **Java 17** o superior.
*   **Maven 3.6** o superior.

### Comandos para Iniciar
Clona el repositorio y ejecuta el siguiente comando en la raíz del proyecto:

```bash
mvn clean compile javafx:run
```

---

## 🔑 Credenciales de Acceso (Datos de Prueba)

El sistema inicializa automáticamente datos de prueba para facilitar la navegación:

*   **Administrador:**
    *   Correo: `admin@logistica.com`
    *   Contraseña: `admin123`
*   **Usuario Cliente:**
    *   Correo: `juan@test.com`
    *   Contraseña: `1234`

---

## 🏗️ Arquitectura y Patrones de Diseño

El sistema está diseñado siguiendo el mapa maestro de **9 patrones de diseño**:

### Patrones Creacionales
1.  **Singleton + Facade:** `GestionEventos`. Centraliza la lógica y el acceso a datos.
2.  **Builder:** `EventoBuilder`. Para la creación detallada de eventos.
3.  **Factory Method:** `IEntradaFactory`. Gestión jerárquica de creación de tickets.

### Patrones Estructurales
4.  **Decorator:** `ServicioDecorator` (VIP, Seguros). Añade funcionalidades a la entrada sin modificar su base.
5.  **Adapter:** `IPagoAdapter`. Unifica servicios de pago (PayPal, Tarjetas).
6.  **Proxy:** `ReporteProxy`. Control de acceso y seguridad para informes de administrador.

### Patrones de Comportamiento
7.  **Strategy:** `ITarifaStrategy`. Algoritmos de precios dinámicos (Preventa/Estándar).
8.  **State:** `IEstadoCompra`. La compra evoluciona de Creada -> Pagada -> Confirmada.
9.  **Observer:** `Usuario` (Observador) y `Evento` (Sujeto). Notificaciones automáticas ante cambios.

---

## 🛠️ Tecnologías Utilizadas
*   **Core:** Java 17.
*   **UI:** JavaFX (Interfaz pura, sin dependencias de CSS externo).
*   **Build Tool:** Maven.
*   **PDF Engine:** Apache PDFBox.

---
Generado por **Antigravity AI** para el Proyecto Final de Programación.
