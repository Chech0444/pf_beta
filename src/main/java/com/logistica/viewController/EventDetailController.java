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
import javafx.stage.Stage;

import java.util.Collections;
import java.util.List;

public class EventDetailController {
    @FXML private Label lblNombre, lblInfo, lblDescripcion, lblLugar, lblFecha, lblPoliticas, lblPrecio, lblMensaje;
    @FXML private ComboBox<Zona> cmbZonas;
    @FXML private ComboBox<Asiento> cmbAsientos;
    @FXML private ComboBox<String> cmbMetodoPago;
    @FXML private CheckBox chkVIP, chkSeguro, chkMerch;

    private Evento evento;

    @FXML
    public void initialize() {
        evento = UserDashboardController.eventoSeleccionado;
        if (evento == null) return;

        lblNombre.setText(evento.getNombre());
        lblInfo.setText(evento.getCategoria() + " | " + evento.getCiudad() + " | " + evento.getEstado());
        lblDescripcion.setText(evento.getDescripcion());
        lblLugar.setText("Lugar: " + evento.getRecintoAsociado().getNombre() + " - " + evento.getRecintoAsociado().getDireccion());
        lblFecha.setText("Fecha: " + evento.getFecha().toString());
        if (evento.getPoliticas() != null) {
            lblPoliticas.setText("Políticas: " + evento.getPoliticas().getPoliticaCancelacion() + " | " + evento.getPoliticas().getPoliticaReembolso());
        }

        cmbZonas.setItems(FXCollections.observableArrayList(evento.getRecintoAsociado().getZonas()));
        cmbZonas.setOnAction(e -> cargarAsientos());
        cmbMetodoPago.setItems(FXCollections.observableArrayList("Tarjeta de Crédito", "PayPal"));

        chkVIP.setOnAction(e -> actualizarPrecio());
        chkSeguro.setOnAction(e -> actualizarPrecio());
        chkMerch.setOnAction(e -> actualizarPrecio());
        cmbZonas.setOnAction(e -> { cargarAsientos(); actualizarPrecio(); });
    }

    private void cargarAsientos() {
        Zona z = cmbZonas.getValue();
        if (z != null) {
            List<Asiento> disponibles = z.getAsientos().stream()
                    .filter(a -> a.getEstado() == EstadoAsiento.DISPONIBLE).toList();
            cmbAsientos.setItems(FXCollections.observableArrayList(disponibles));
        }
    }

    private void actualizarPrecio() {
        Zona z = cmbZonas.getValue();
        if (z == null) { lblPrecio.setText("Total: $0"); return; }
        double precio = z.calcularPrecioFinal();
        if (chkVIP.isSelected()) precio += 50000;
        if (chkSeguro.isSelected()) precio += 15000;
        if (chkMerch.isSelected()) precio += 25000;
        lblPrecio.setText("Total: $" + String.format("%.0f", precio));
    }

    @FXML
    private void handleComprar(ActionEvent event) {
        Zona z = cmbZonas.getValue();
        Asiento a = cmbAsientos.getValue();
        String metodo = cmbMetodoPago.getValue();
        if (z == null || metodo == null) {
            lblMensaje.setStyle("-fx-text-fill: #e94560;");
            lblMensaje.setText("Selecciona zona y método de pago.");
            return;
        }

        // Factory: crear entrada
        EntradaNumeradaFactory factory = new EntradaNumeradaFactory();
        Entrada entrada = factory.crearEntrada(z, a);

        // Decorator: agregar servicios
        if (chkVIP.isSelected()) entrada = new AccesoVipDecorator(entrada);
        if (chkSeguro.isSelected()) entrada = new SeguroCancelacionDecorator(entrada);
        if (chkMerch.isSelected()) entrada = new MerchandisingDecorator(entrada);

        // Crear compra via Facade
        Usuario user = LoginController.getUsuarioLogueado();
        Compra compra = GestionEventos.getInstance().crearCompra(user, evento, Collections.singletonList(entrada));

        // Adapter: procesar pago
        IPagoAdapter pago = metodo.contains("PayPal") ? new PayPalAdapter() : new TarjetaCreditoAdapter();
        boolean exito = pago.procesarPago(compra.getTotal());

        if (exito) {
            compra.pagar(); // State: CREADA -> PAGADA
            compra.pagar(); // State: PAGADA -> CONFIRMADA
            if (a != null) a.cambiarEstado(EstadoAsiento.VENDIDO);
            lblMensaje.setStyle("-fx-text-fill: #4caf50;");
            lblMensaje.setText("¡Compra exitosa! ID: " + compra.getIdCompra());
            cargarAsientos();
        }
    }

    @FXML
    private void handleVolver(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/UserDashboard.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 900, 650));
    }
}
