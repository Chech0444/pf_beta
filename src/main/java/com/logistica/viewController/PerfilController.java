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
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PerfilController {
    @FXML private TextField txtNombre, txtEmail, txtTelefono, txtDetallePago;
    @FXML private ListView<String> lstMetodosPago;
    @FXML private ComboBox<String> cmbTipoPago;
    @FXML private Label lblMsg;

    @FXML
    public void initialize() {
        Usuario u = LoginController.getUsuarioLogueado();
        if (u == null) return;
        txtNombre.setText(u.getNombreCompleto());
        txtEmail.setText(u.getEmail());
        txtTelefono.setText(u.getTelefono());
        cmbTipoPago.setItems(FXCollections.observableArrayList("Tarjeta", "PayPal", "PSE", "Nequi"));
        cargarMetodosPago();
    }

    private void cargarMetodosPago() {
        Usuario u = LoginController.getUsuarioLogueado();
        lstMetodosPago.setItems(FXCollections.observableArrayList(
                u.getMetodosPago().stream().map(MetodoPago::toString).toList()));
    }

    @FXML
    private void guardarPerfil(ActionEvent event) {
        Usuario u = LoginController.getUsuarioLogueado();
        u.setNombreCompleto(txtNombre.getText());
        u.setEmail(txtEmail.getText());
        u.setTelefono(txtTelefono.getText());
        GestionEventos.getInstance().actualizarUsuario(u);
        lblMsg.setText("Perfil actualizado exitosamente.");
    }

    @FXML
    private void agregarMetodoPago(ActionEvent event) {
        String tipo = cmbTipoPago.getValue();
        String detalle = txtDetallePago.getText();
        if (tipo == null || detalle.isBlank()) return;
        LoginController.getUsuarioLogueado().addMetodoPago(new MetodoPago(tipo, detalle));
        txtDetallePago.clear();
        cargarMetodosPago();
    }

    @FXML
    private void volver(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/UserDashboard.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1024, 700));
    }
}
