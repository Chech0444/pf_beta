package com.logistica;

import com.logistica.controller.GestionEventos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializar datos de prueba (RF-045)
        GestionEventos.getInstance().inicializarDatosPrueba();

        Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/Login.fxml"));
        Scene scene = new Scene(root, 900, 650);

        primaryStage.setTitle("BookIt - Plataforma de Gestión de Eventos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
