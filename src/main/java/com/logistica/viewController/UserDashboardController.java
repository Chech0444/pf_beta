package com.logistica.viewController;

import com.logistica.controller.GestionEventos;
import com.logistica.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserDashboardController {
    @FXML private FlowPane flowEventos;
    @FXML private TextField txtFiltroCiudad, txtFiltroCategoria, txtFiltroPrecio;

    private GestionEventos gestion = GestionEventos.getInstance();
    public static Evento eventoSeleccionado;

    @FXML
    public void initialize() {
        filtrarEventos(null);
    }

    @FXML
    private void filtrarEventos(ActionEvent event) {
        Filtros f = new Filtros();
        if (txtFiltroCiudad != null && !txtFiltroCiudad.getText().isBlank()) f.setCiudad(txtFiltroCiudad.getText());
        if (txtFiltroCategoria != null && !txtFiltroCategoria.getText().isBlank()) f.setCategoria(txtFiltroCategoria.getText());
        if (txtFiltroPrecio != null && !txtFiltroPrecio.getText().isBlank()) {
            try { f.setPrecioMax(Double.parseDouble(txtFiltroPrecio.getText())); } catch (Exception ignored) {}
        }
        List<Evento> eventos = gestion.explorarEventos(f);
        flowEventos.getChildren().clear();
        for (Evento ev : eventos) {
            flowEventos.getChildren().add(crearCardEvento(ev));
        }
    }

    @FXML
    private void limpiarFiltros(ActionEvent event) {
        txtFiltroCiudad.clear();
        txtFiltroCategoria.clear();
        txtFiltroPrecio.clear();
        filtrarEventos(null);
    }

    private VBox crearCardEvento(Evento ev) {
        VBox card = new VBox(8);
        card.getStyleClass().add("card");
        card.setPrefWidth(260);
        card.setPadding(new Insets(16));

        String cat = ev.getCategoria();
        String emoji = cat.equals("Música") ? "🎵" : cat.equals("Teatro") ? "🎭" : cat.equals("Conferencia") ? "💻" : cat.equals("Comedia") ? "😂" : "🎪";

        Label lblEmoji = new Label(emoji);
        lblEmoji.setStyle("-fx-font-size: 36;");

        Label lblNombre = new Label(ev.getNombre());
        lblNombre.setStyle("-fx-font-size: 15; -fx-font-weight: bold; -fx-text-fill: white; -fx-wrap-text: true;");
        lblNombre.setWrapText(true);

        Label lblCat = new Label(ev.getCategoria() + " • " + ev.getCiudad());
        lblCat.setStyle("-fx-text-fill: #58a6ff; -fx-font-size: 12;");

        Label lblFecha = new Label("📅 " + ev.getFecha().format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        lblFecha.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 12;");

        Label lblRecinto = new Label("📍 " + ev.getRecintoAsociado().getNombre());
        lblRecinto.setStyle("-fx-text-fill: #8b949e; -fx-font-size: 11;");

        double minPrice = ev.getRecintoAsociado().getZonas().stream().mapToDouble(Zona::getPrecioBase).min().orElse(0);
        Label lblPrecio = new Label("Desde $" + String.format("%.0f", minPrice));
        lblPrecio.setStyle("-fx-text-fill: #3fb950; -fx-font-weight: bold; -fx-font-size: 14;");

        Button btnVer = new Button("Ver Detalles →");
        btnVer.getStyleClass().add("btn-primary");
        btnVer.setMaxWidth(Double.MAX_VALUE);
        btnVer.setOnAction(e -> {
            eventoSeleccionado = ev;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/com/logistica/views/EventDetail.fxml"));
                Stage window = (Stage) btnVer.getScene().getWindow();
                window.setScene(new Scene(root, 1024, 700));
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        card.getChildren().addAll(lblEmoji, lblNombre, lblCat, lblFecha, lblRecinto, lblPrecio, btnVer);
        return card;
    }

    @FXML private void verPerfil(ActionEvent event) { navegar(event, "/com/logistica/views/Perfil.fxml"); }
    @FXML private void verCompras(ActionEvent event) { navegar(event, "/com/logistica/views/MisCompras.fxml"); }
    @FXML private void logout(ActionEvent event) { navegar(event, "/com/logistica/views/Login.fxml"); }

    private void navegar(ActionEvent event, String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 1024, 700));
        } catch (Exception e) { e.printStackTrace(); }
    }
}
