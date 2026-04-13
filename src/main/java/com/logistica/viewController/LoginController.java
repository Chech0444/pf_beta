package com.logistica.viewController;

import com.logistica.controller.GestionEventos;
import com.logistica.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    private static Usuario usuarioLogueado;
    public static Usuario getUsuarioLogueado() { return usuarioLogueado; }
    public static void setUsuarioLogueado(Usuario u) { usuarioLogueado = u; }

    @FXML
    private void handleLogin(ActionEvent event) {
        String correo = txtCorreo.getText();
        String pass = txtPassword.getText();
        if (correo == null || correo.isBlank()) { lblError.setText("Ingresa un correo."); return; }
        if (pass == null || pass.isBlank()) { lblError.setText("Ingresa tu contraseña."); return; }

        Usuario u = GestionEventos.getInstance().iniciarSesion(correo.trim(), pass.trim());
        if (u != null) {
            usuarioLogueado = u;
            System.out.println("--> Login exitoso: " + u.getNombreCompleto() + " [" + (u.isEsAdmin() ? "ADMIN" : "USER") + "]");
            try {
                String vista = u.isEsAdmin() ? "/com/logistica/views/AdminDashboard.fxml" : "/com/logistica/views/UserDashboard.fxml";
                Parent root = FXMLLoader.load(getClass().getResource(vista));
                Stage window = (Stage) txtCorreo.getScene().getWindow();
                window.setScene(new Scene(root, 1024, 700));
            } catch (Exception e) { e.printStackTrace(); }
        } else {
            lblError.setText("Credenciales inválidas.");
        }
    }

    @FXML
    private void handleRegistro(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/Registro.fxml"));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1024, 700));
        } catch (Exception e) { e.printStackTrace(); }
    }
}
