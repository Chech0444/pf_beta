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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class UserDashboardController {
    @FXML private ListView<String> lstEventos;
    @FXML private TextField txtFiltroCiudad, txtFiltroCategoria;

    private GestionEventos gestion = GestionEventos.getInstance();
    private List<Evento> eventosActuales;

    public static Evento eventoSeleccionado;

    @FXML
    public void initialize() {
        filtrarEventos(null);
    }

    @FXML
    private void filtrarEventos(ActionEvent event) {
        Filtros f = new Filtros();
        if (txtFiltroCiudad != null) f.setCiudad(txtFiltroCiudad.getText());
        if (txtFiltroCategoria != null) f.setCategoria(txtFiltroCategoria.getText());
        eventosActuales = gestion.explorarEventos(f);
        lstEventos.setItems(FXCollections.observableArrayList(
                eventosActuales.stream().map(e -> e.getNombre() + " | " + e.getCategoria() + " | " + e.getCiudad() + " | " + e.getFecha().toLocalDate()).toList()));
    }

    @FXML
    private void verDetalles(ActionEvent event) {
        int idx = lstEventos.getSelectionModel().getSelectedIndex();
        if (idx >= 0) {
            eventoSeleccionado = eventosActuales.get(idx);
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/EventDetail.fxml"));
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(new Scene(root, 900, 650));
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @FXML
    private void verCompras(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/MisCompras.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 900, 650));
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void logout(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/Login.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 900, 650));
    }
}
