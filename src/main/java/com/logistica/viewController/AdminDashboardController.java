package com.logistica.viewController;

import com.logistica.controller.GestionEventos;
import com.logistica.model.*;
import com.logistica.model.proxy.ReporteProxy;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class AdminDashboardController {
    @FXML private ListView<String> lstEventos, lstUsuarios, lstCompras, lstIncidencias;
    @FXML private PieChart chartOcupacion;
    @FXML private Label lblMetricas;

    private GestionEventos gestion = GestionEventos.getInstance();

    @FXML
    public void initialize() {
        cargarDatos();
    }

    private void cargarDatos() {
        lstEventos.setItems(FXCollections.observableArrayList(
                gestion.listarEventos().stream().map(Evento::toString).toList()));
        lstUsuarios.setItems(FXCollections.observableArrayList(
                gestion.listarUsuarios().stream().map(Usuario::toString).toList()));
        lstCompras.setItems(FXCollections.observableArrayList(
                gestion.getCompras().stream().map(Compra::toString).toList()));
        lstIncidencias.setItems(FXCollections.observableArrayList(
                gestion.getIncidencias().stream().map(i -> i.getTipo() + ": " + i.getDescripcion()).toList()));

        // Chart de ocupación del primer evento publicado
        List<Evento> publicados = gestion.explorarEventos(new Filtros());
        if (!publicados.isEmpty()) {
            Evento ev = publicados.get(0);
            Map<String, Double> ocupacion = gestion.obtenerOcupacionPorZona(ev);
            chartOcupacion.setData(FXCollections.observableArrayList(
                    ocupacion.entrySet().stream()
                            .map(e -> new PieChart.Data(e.getKey() + " (" + String.format("%.0f", e.getValue()) + "%)", Math.max(e.getValue(), 1)))
                            .toList()));
            chartOcupacion.setTitle("Ocupación: " + ev.getNombre());
        }

        // Métricas
        double tasaCancelacion = gestion.obtenerTasaCancelacion();
        List<Evento> top = gestion.obtenerTopEventos();
        StringBuilder sb = new StringBuilder();
        sb.append("Tasa de cancelación: ").append(String.format("%.1f", tasaCancelacion)).append("%\n");
        sb.append("Total compras: ").append(gestion.getCompras().size()).append("\n");
        sb.append("Top eventos: ");
        for (Evento e : top) sb.append(e.getNombre()).append(", ");
        lblMetricas.setText(sb.toString());
    }

    @FXML
    private void generarPDF(ActionEvent event) {
        ReporteProxy proxy = new ReporteProxy(LoginController.getUsuarioLogueado());
        proxy.generarReportePDF(new Filtros(), "Reporte_BookIt.pdf");
        cargarDatos();
    }

    @FXML
    private void generarCSV(ActionEvent event) {
        ReporteProxy proxy = new ReporteProxy(LoginController.getUsuarioLogueado());
        proxy.generarReporteCSV(new Filtros(), "Reporte_BookIt.csv");
        cargarDatos();
    }

    @FXML
    private void cancelarEvento(ActionEvent event) {
        int idx = lstEventos.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            Evento ev = gestion.listarEventos().get(idx);
            gestion.cancelarEvento(ev);
            cargarDatos();
        }
    }

    @FXML
    private void publicarEvento(ActionEvent event) {
        int idx = lstEventos.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            Evento ev = gestion.listarEventos().get(idx);
            gestion.publicarEvento(ev);
            cargarDatos();
        }
    }

    @FXML
    private void logout(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/Login.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 900, 650));
    }
}
