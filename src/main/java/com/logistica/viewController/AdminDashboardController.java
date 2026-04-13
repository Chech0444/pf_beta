package com.logistica.viewController;

import com.logistica.controller.GestionEventos;
import com.logistica.model.*;
import com.logistica.model.proxy.ReporteProxy;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class AdminDashboardController {
    // Eventos tab
    @FXML private TableView<Evento> tblEventos;
    @FXML private TableColumn<Evento, String> colEvId, colEvNombre, colEvCategoria, colEvCiudad, colEvFecha, colEvEstado, colEvRecinto;

    // Usuarios tab
    @FXML private TableView<Usuario> tblUsuarios;
    @FXML private TableColumn<Usuario, String> colUsId, colUsNombre, colUsEmail, colUsTel, colUsRol, colUsCompras;

    // Compras tab
    @FXML private TableView<Compra> tblCompras;
    @FXML private TableColumn<Compra, String> colCmpId, colCmpCliente, colCmpEvento, colCmpTotal, colCmpEstado, colCmpFecha;

    // Recintos tab
    @FXML private TableView<Recinto> tblRecintos;
    @FXML private TableColumn<Recinto, String> colRecId, colRecNombre, colRecCiudad, colRecDir, colRecZonas;

    // Métricas tab
    @FXML private PieChart chartOcupacion;
    @FXML private BarChart<String, Number> chartVentas;
    @FXML private Label lblTotalCompras, lblTasaCancelacion, lblTotalEventos;
    @FXML private ListView<String> lstIncidencias;

    private GestionEventos gestion = GestionEventos.getInstance();

    @FXML
    public void initialize() {
        configurarTablas();
        cargarDatos();
    }

    private void configurarTablas() {
        // Eventos
        colEvId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdEvento()));
        colEvNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colEvCategoria.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategoria()));
        colEvCiudad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCiudad()));
        colEvFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        colEvEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstado().name()));
        colEvRecinto.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRecintoAsociado().getNombre()));

        // Usuarios
        colUsId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdUsuario()));
        colUsNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombreCompleto()));
        colUsEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));
        colUsTel.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        colUsRol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().isEsAdmin() ? "Admin" : "Cliente"));
        colUsCompras.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getHistorialCompras().size())));

        // Compras
        colCmpId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdCompra()));
        colCmpCliente.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsuarioAsociado().getNombreCompleto()));
        colCmpEvento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEventoAsociado().getNombre()));
        colCmpTotal.setCellValueFactory(c -> new SimpleStringProperty("$" + String.format("%,.0f", c.getValue().getTotal())));
        colCmpEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstadoActual().getNombreEstado()));
        colCmpFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

        // Recintos
        colRecId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdRecinto()));
        colRecNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colRecCiudad.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCiudad()));
        colRecDir.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDireccion()));
        colRecZonas.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getZonas().size())));
    }

    private void cargarDatos() {
        tblEventos.setItems(FXCollections.observableArrayList(gestion.listarEventos()));
        tblUsuarios.setItems(FXCollections.observableArrayList(gestion.listarUsuarios()));
        tblCompras.setItems(FXCollections.observableArrayList(gestion.getCompras()));
        tblRecintos.setItems(FXCollections.observableArrayList(gestion.listarRecintos()));
        lstIncidencias.setItems(FXCollections.observableArrayList(
                gestion.getIncidencias().stream().map(i -> "[" + i.getTipo() + "] " + i.getDescripcion()).toList()));

        // Métricas cards
        lblTotalCompras.setText(String.valueOf(gestion.getCompras().size()));
        lblTasaCancelacion.setText(String.format("%.1f%%", gestion.obtenerTasaCancelacion()));
        lblTotalEventos.setText(String.valueOf(gestion.listarEventos().size()));

        // PieChart: ocupación por zona del primer evento
        List<Evento> publicados = gestion.explorarEventos(new Filtros());
        if (!publicados.isEmpty()) {
            Evento ev = publicados.get(0);
            Map<String, Double> ocup = gestion.obtenerOcupacionPorZona(ev);
            chartOcupacion.setData(FXCollections.observableArrayList(
                    ocup.entrySet().stream()
                            .map(e -> new PieChart.Data(e.getKey() + " (" + String.format("%.0f", e.getValue()) + "%)", Math.max(e.getValue(), 1)))
                            .toList()));
            chartOcupacion.setTitle("Ocupación: " + ev.getNombre());
        }

        // BarChart: ventas por evento (RF-019)
        chartVentas.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ingresos");
        for (Evento ev : gestion.listarEventos()) {
            double ventas = gestion.getCompras().stream()
                    .filter(c -> c.getEventoAsociado().getIdEvento().equals(ev.getIdEvento()))
                    .mapToDouble(Compra::getTotal).sum();
            if (ventas > 0) {
                series.getData().add(new XYChart.Data<>(ev.getNombre().length() > 15 ? ev.getNombre().substring(0, 15) + "..." : ev.getNombre(), ventas));
            }
        }
        if (!series.getData().isEmpty()) chartVentas.getData().add(series);
    }

    // === ACCIONES EVENTOS ===
    @FXML private void publicarEvento(ActionEvent event) {
        Evento ev = tblEventos.getSelectionModel().getSelectedItem();
        if (ev != null) { gestion.publicarEvento(ev); cargarDatos(); }
    }
    @FXML private void pausarEvento(ActionEvent event) {
        Evento ev = tblEventos.getSelectionModel().getSelectedItem();
        if (ev != null) { gestion.pausarEvento(ev); cargarDatos(); }
    }
    @FXML private void cancelarEvento(ActionEvent event) {
        Evento ev = tblEventos.getSelectionModel().getSelectedItem();
        if (ev != null) { gestion.cancelarEvento(ev); cargarDatos(); }
    }
    @FXML private void eliminarEvento(ActionEvent event) {
        Evento ev = tblEventos.getSelectionModel().getSelectedItem();
        if (ev != null) { gestion.eliminarEvento(ev.getIdEvento()); cargarDatos(); }
    }

    // === ACCIONES USUARIOS ===
    @FXML private void eliminarUsuario(ActionEvent event) {
        Usuario u = tblUsuarios.getSelectionModel().getSelectedItem();
        if (u != null) { gestion.eliminarUsuario(u.getIdUsuario()); cargarDatos(); }
    }

    // === ACCIONES COMPRAS ===
    @FXML private void cancelarCompraAdmin(ActionEvent event) {
        Compra c = tblCompras.getSelectionModel().getSelectedItem();
        if (c != null) { gestion.cancelarCompra(c); cargarDatos(); }
    }
    @FXML private void registrarReembolso(ActionEvent event) {
        Compra c = tblCompras.getSelectionModel().getSelectedItem();
        if (c != null) { gestion.registrarReembolso(c); cargarDatos(); }
    }

    // === REPORTES ===
    @FXML private void generarPDF(ActionEvent event) {
        new ReporteProxy(LoginController.getUsuarioLogueado()).generarReportePDF(new Filtros(), "Reporte_BookIt.pdf");
    }
    @FXML private void generarCSV(ActionEvent event) {
        new ReporteProxy(LoginController.getUsuarioLogueado()).generarReporteCSV(new Filtros(), "Reporte_BookIt.csv");
    }

    @FXML private void logout(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/Login.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1024, 700));
    }
}
