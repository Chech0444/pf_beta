package com.logistica.viewController;

import com.logistica.controller.SistemaFacade;
import com.logistica.model.Usuario;
import com.logistica.model.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtCorreo;
    
    @FXML
    private Label lblError;

    private SistemaFacade sistema = new SistemaFacade();

    @FXML
    private void handleLogin(ActionEvent event) {
        String correo = txtCorreo.getText();
        if (correo == null || correo.trim().isEmpty()) {
            lblError.setText("Error: Ingresa un correo electrónico");
            return;
        }

        Usuario u = sistema.login(correo.trim());
        if (u != null) {
            SessionManager.getInstance().setUsuarioActual(u);
            lblError.setStyle("-fx-text-fill: #4caf50;");
            lblError.setText("Login Exitoso! Hola " + u.getNombreCompleto());
            System.out.println("--> Login exitoso: " + u.getNombreCompleto());
            
            try {
                javafx.scene.Parent dashboardParent = FXMLLoader.load(getClass().getResource("/com/logistica/views/Dashboard.fxml"));
                javafx.scene.Scene dashboardScene = new javafx.scene.Scene(dashboardParent, 800, 600);
                javafx.stage.Stage window = (javafx.stage.Stage) txtCorreo.getScene().getWindow();
                window.setScene(dashboardScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            lblError.setStyle("-fx-text-fill: #e94560;");
            lblError.setText("Error: Credenciales inválidas o usuario no existe");
        }
    }
}
