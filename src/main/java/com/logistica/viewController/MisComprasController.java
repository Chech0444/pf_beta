package com.logistica.viewController;

import com.logistica.controller.GestionEventos;
import com.logistica.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MisComprasController {
    @FXML private TableView<Compra> tblCompras;
    @FXML private TableColumn<Compra, String> colId, colEvento, colFecha, colTotal, colEstado, colEntradas;
    @FXML private ComboBox<String> cmbFiltroEstado;
    @FXML private Label lblDetalle;

    private List<Compra> compras;

    @FXML
    public void initialize() {
        cmbFiltroEstado.setItems(FXCollections.observableArrayList("", "CREADA", "PAGADA", "CONFIRMADA", "CANCELADA", "REEMBOLSADA"));
        colId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdCompra()));
        colEvento.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEventoAsociado().getNombre()));
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFechaCreacion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        colTotal.setCellValueFactory(c -> new SimpleStringProperty("$" + String.format("%,.0f", c.getValue().getTotal())));
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEstadoActual().getNombreEstado()));
        colEntradas.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getItemsCompra().size())));

        tblCompras.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Compra: ").append(sel.getIdCompra()).append("\n");
                sb.append("Evento: ").append(sel.getEventoAsociado().getNombre()).append(" (").append(sel.getEventoAsociado().getCiudad()).append(")\n");
                sb.append("Estado: ").append(sel.getEstadoActual().getNombreEstado()).append("\n");
                sb.append("Total: $").append(String.format("%,.0f", sel.getTotal())).append("\n");
                sb.append("Entradas:\n");
                for (Entrada e : sel.getItemsCompra()) {
                    sb.append("  • ").append(e.toString()).append("\n");
                }
                lblDetalle.setText(sb.toString());
            }
        });

        cargarCompras(new Filtros());
    }

    private void cargarCompras(Filtros f) {
        Usuario user = LoginController.getUsuarioLogueado();
        compras = GestionEventos.getInstance().consultarHistorialCompras(user, f);
        tblCompras.setItems(FXCollections.observableArrayList(compras));
    }

    @FXML
    private void aplicarFiltro(ActionEvent event) {
        Filtros f = new Filtros();
        String est = cmbFiltroEstado.getValue();
        if (est != null && !est.isEmpty()) f.setEstado(est);
        cargarCompras(f);
    }

    @FXML
    private void limpiarFiltro(ActionEvent event) {
        cmbFiltroEstado.setValue(null);
        cargarCompras(new Filtros());
    }

    @FXML
    private void cancelarCompra(ActionEvent event) {
        Compra sel = tblCompras.getSelectionModel().getSelectedItem();
        if (sel != null) {
            GestionEventos.getInstance().cancelarCompra(sel);
            cargarCompras(new Filtros());
        }
    }

    @FXML
    private void descargarCSV(ActionEvent event) {
        String file = "MisCompras_" + LoginController.getUsuarioLogueado().getIdUsuario() + ".csv";
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            pw.println("ID,Evento,Fecha,Total,Estado,Entradas");
            for (Compra c : compras) {
                pw.println(c.getIdCompra() + "," + c.getEventoAsociado().getNombre() + "," +
                        c.getFechaCreacion().toLocalDate() + "," + c.getTotal() + "," +
                        c.getEstadoActual().getNombreEstado() + "," + c.getItemsCompra().size());
            }
            lblDetalle.setText("✅ CSV exportado: " + file);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void descargarPDF(ActionEvent event) {
        com.logistica.model.proxy.ReporteProxy proxy = new com.logistica.model.proxy.ReporteProxy(LoginController.getUsuarioLogueado());
        // For user reports, we bypass proxy (users can see their own)
        String file = "MisCompras_" + LoginController.getUsuarioLogueado().getIdUsuario() + ".pdf";
        new com.logistica.model.proxy.ReporteReal().generarReportePDF(new Filtros(), file);
        lblDetalle.setText("✅ PDF exportado: " + file);
    }

    @FXML
    private void volver(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/UserDashboard.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1024, 700));
    }
}
