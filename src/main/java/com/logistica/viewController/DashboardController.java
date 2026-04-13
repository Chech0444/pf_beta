package com.logistica.viewController;

import com.logistica.controller.informes.IReporteAdapter;
import com.logistica.controller.informes.ReporteFactory;
import com.logistica.model.DataStore;
import com.logistica.model.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private PieChart ventasPorZonaChart;

    @FXML
    public void initialize() {
        // Datos mock para el gráfico Requerimiento JavaFX Charts (pie)
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("General", 300),
                new PieChart.Data("VIP", 50),
                new PieChart.Data("Platino", 120));
        ventasPorZonaChart.setData(pieChartData);
    }

    @FXML
    private void generarPDF(ActionEvent event) {
        IReporteAdapter pdfGenerator = ReporteFactory.obtenerGenerador("PDF");
        pdfGenerator.generarReporteCompras(DataStore.getInstance().getCompras(), "Reporte_BookIt.pdf");
        System.out.println("Exportando PDF...");
    }

    @FXML
    private void generarCSV(ActionEvent event) {
        IReporteAdapter csvGenerator = ReporteFactory.obtenerGenerador("CSV");
        csvGenerator.generarReporteCompras(DataStore.getInstance().getCompras(), "Reporte_BookIt.csv");
        System.out.println("Exportando CSV...");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        SessionManager.getInstance().logout();
        Parent loginParent = FXMLLoader.load(getClass().getResource("/com/logistica/views/Login.fxml"));
        Scene loginScene = new Scene(loginParent, 800, 600);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }
}
