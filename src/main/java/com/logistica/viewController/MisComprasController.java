package com.logistica.viewController;

import com.logistica.controller.GestionEventos;
import com.logistica.model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class MisComprasController {
    @FXML private ListView<String> lstCompras;
    @FXML private Label lblDetalle;

    private List<Compra> compras;

    @FXML
    public void initialize() {
        Usuario user = LoginController.getUsuarioLogueado();
        compras = GestionEventos.getInstance().consultarHistorialCompras(user, new Filtros());
        lstCompras.setItems(FXCollections.observableArrayList(
                compras.stream().map(Compra::toString).toList()));
        lstCompras.getSelectionModel().selectedIndexProperty().addListener((obs, old, idx) -> {
            if (idx.intValue() >= 0 && idx.intValue() < compras.size()) {
                Compra c = compras.get(idx.intValue());
                lblDetalle.setText("Compra: " + c.getIdCompra() + "\nEvento: " + c.getEventoAsociado().getNombre()
                        + "\nEstado: " + c.getEstadoActual().getNombreEstado()
                        + "\nTotal: $" + c.getTotal()
                        + "\nEntradas: " + c.getItemsCompra().size()
                        + "\nFecha: " + c.getFechaCreacion());
            }
        });
    }

    @FXML
    private void cancelarCompra(ActionEvent event) {
        int idx = lstCompras.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            GestionEventos.getInstance().cancelarCompra(compras.get(idx));
            initialize(); // refresh
        }
    }

    @FXML
    private void volver(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/UserDashboard.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 900, 650));
    }
}
