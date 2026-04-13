package com.logistica.viewController;

import com.logistica.controller.GestionEventos;
import com.logistica.model.*;
import com.logistica.model.adapter.*;
import com.logistica.model.decorator.*;
import com.logistica.model.factory.EntradaNumeradaFactory;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EventDetailController {
    @FXML private Label lblNombre, lblInfo, lblDescripcion, lblLugar, lblFecha, lblPoliticas, lblAforo, lblPrecio, lblMensaje;
    @FXML private ComboBox<Zona> cmbZonas;
    @FXML private ComboBox<Asiento> cmbAsientos;
    @FXML private ComboBox<String> cmbMetodoPago;
    @FXML private CheckBox chkVIP, chkSeguro, chkMerch;
    @FXML private FlowPane seatMap;

    private Evento evento;
    private Asiento asientoSeleccionado;

    @FXML
    public void initialize() {
        evento = UserDashboardController.eventoSeleccionado;
        if (evento == null) return;

        lblNombre.setText(evento.getNombre());
        lblInfo.setText(evento.getCategoria() + " • " + evento.getCiudad() + " • " + evento.getEstado());
        lblDescripcion.setText(evento.getDescripcion());
        lblLugar.setText("📍 " + evento.getRecintoAsociado().getNombre() + " - " + evento.getRecintoAsociado().getDireccion());
        lblFecha.setText("📅 " + evento.getFecha().format(DateTimeFormatter.ofPattern("EEEE dd MMM yyyy, HH:mm")));
        if (evento.getPoliticas() != null) {
            lblPoliticas.setText("📋 " + evento.getPoliticas().getPoliticaCancelacion() + " | " + evento.getPoliticas().getPoliticaReembolso());
        }

        // Aforo (RF-004)
        Map<Zona, Integer> disp = evento.consultarDisponibilidad();
        int totalDisp = disp.values().stream().mapToInt(Integer::intValue).sum();
        int totalCap = evento.getRecintoAsociado().getZonas().stream().mapToInt(Zona::getCapacidad).sum();
        lblAforo.setText("🏟️ Aforo: " + totalDisp + " disponibles de " + totalCap + " totales");

        cmbZonas.setItems(FXCollections.observableArrayList(evento.getRecintoAsociado().getZonas()));
        cmbZonas.setOnAction(e -> { cargarAsientos(); actualizarPrecio(); cargarSeatMap(); });
        cmbMetodoPago.setItems(FXCollections.observableArrayList("Tarjeta de Crédito", "PayPal"));

        chkVIP.setOnAction(e -> actualizarPrecio());
        chkSeguro.setOnAction(e -> actualizarPrecio());
        chkMerch.setOnAction(e -> actualizarPrecio());
    }

    private void cargarAsientos() {
        Zona z = cmbZonas.getValue();
        if (z != null) {
            List<Asiento> disponibles = z.getAsientos().stream()
                    .filter(a -> a.getEstado() == EstadoAsiento.DISPONIBLE).toList();
            cmbAsientos.setItems(FXCollections.observableArrayList(disponibles));
        }
    }

    private void cargarSeatMap() {
        seatMap.getChildren().clear();
        Zona z = cmbZonas.getValue();
        if (z == null) return;
        for (Asiento a : z.getAsientos()) {
            Button btn = new Button(a.getFila() + a.getNumero());
            switch (a.getEstado()) {
                case DISPONIBLE -> btn.getStyleClass().add("seat-available");
                case VENDIDO -> btn.getStyleClass().add("seat-sold");
                case RESERVADO -> btn.getStyleClass().add("seat-reserved");
                case BLOQUEADO -> btn.getStyleClass().add("seat-blocked");
            }
            if (a.getEstado() == EstadoAsiento.DISPONIBLE) {
                btn.setOnAction(e -> {
                    asientoSeleccionado = a;
                    cmbAsientos.setValue(a);
                    // Reset visual selection
                    seatMap.getChildren().forEach(n -> {
                        n.getStyleClass().removeAll("seat-selected");
                    });
                    btn.getStyleClass().removeAll("seat-available");
                    btn.getStyleClass().add("seat-selected");
                    actualizarPrecio();
                });
            }
            seatMap.getChildren().add(btn);
        }
    }

    private void actualizarPrecio() {
        Zona z = cmbZonas.getValue();
        if (z == null) { lblPrecio.setText("Total: $0"); return; }
        double precio = z.calcularPrecioFinal();
        if (chkVIP.isSelected()) precio += 50000;
        if (chkSeguro.isSelected()) precio += 15000;
        if (chkMerch.isSelected()) precio += 25000;
        lblPrecio.setText("Total: $" + String.format("%,.0f", precio));
    }

    @FXML
    private void handleComprar(ActionEvent event) {
        Zona z = cmbZonas.getValue();
        Asiento a = cmbAsientos.getValue();
        if (asientoSeleccionado != null) a = asientoSeleccionado;
        String metodo = cmbMetodoPago.getValue();
        if (z == null || metodo == null) {
            lblMensaje.getStyleClass().setAll("label", "label-error");
            lblMensaje.setText("Selecciona zona y método de pago.");
            return;
        }

        // Factory Method
        EntradaNumeradaFactory factory = new EntradaNumeradaFactory();
        Entrada entrada = factory.crearEntrada(z, a);

        // Decorator
        if (chkVIP.isSelected()) entrada = new AccesoVipDecorator(entrada);
        if (chkSeguro.isSelected()) entrada = new SeguroCancelacionDecorator(entrada);
        if (chkMerch.isSelected()) entrada = new MerchandisingDecorator(entrada);

        // Facade
        Usuario user = LoginController.getUsuarioLogueado();
        Compra compra = GestionEventos.getInstance().crearCompra(user, evento, Collections.singletonList(entrada));

        // Adapter
        IPagoAdapter pago = metodo.contains("PayPal") ? new PayPalAdapter() : new TarjetaCreditoAdapter();
        boolean exito = pago.procesarPago(compra.getTotal());

        if (exito) {
            compra.pagar(); // State: CREADA -> PAGADA
            compra.pagar(); // State: PAGADA -> CONFIRMADA
            if (a != null) a.cambiarEstado(EstadoAsiento.VENDIDO);

            // Generar recibo (RF-007)
            generarReciboTxt(compra);

            lblMensaje.getStyleClass().setAll("label", "label-success");
            lblMensaje.setText("✅ ¡Compra exitosa! Recibo: Recibo_" + compra.getIdCompra() + ".txt");
            cargarAsientos();
            cargarSeatMap();
        }
    }

    private void generarReciboTxt(Compra c) {
        String fileName = "Recibo_" + c.getIdCompra() + ".txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("============================================");
            pw.println("         BOOKIT - RECIBO DE COMPRA          ");
            pw.println("============================================");
            pw.println("ID COMPRA:  " + c.getIdCompra());
            pw.println("FECHA:      " + c.getFechaCreacion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pw.println("--------------------------------------------");
            pw.println("CLIENTE:    " + c.getUsuarioAsociado().getNombreCompleto());
            pw.println("EVENTO:     " + c.getEventoAsociado().getNombre());
            pw.println("LUGAR:      " + c.getEventoAsociado().getRecintoAsociado().getNombre());
            pw.println("CIUDAD:     " + c.getEventoAsociado().getCiudad());
            pw.println("--------------------------------------------");
            pw.println("DETALLE DE ENTRADAS:");
            for (Entrada e : c.getItemsCompra()) {
                pw.println("  - " + e.toString());
            }
            pw.println("--------------------------------------------");
            pw.println("TOTAL:    $" + String.format("%,.0f", c.getTotal()));
            pw.println("ESTADO:   " + c.getEstadoActual().getNombreEstado());
            pw.println("============================================");
            pw.println("   ¡Gracias por elegir BookIt!              ");
            pw.println("============================================");
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleVolver(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/UserDashboard.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1024, 700));
    }
}
