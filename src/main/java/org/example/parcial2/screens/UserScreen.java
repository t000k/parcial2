package org.example.parcial2.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserScreen {

    private final Stage stage;

    public UserScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label welcomeLabel = new Label("Bienvenido a la sección de Usuario");

        // Botones para acceder a las diferentes funcionalidades
        Button compraCancionesButton = new Button("Compra de Canciones");
        compraCancionesButton.setOnAction(e -> new UserCancionScreen(stage).show());

        Button compraAlbumesButton = new Button("Compra de Álbumes");
        compraAlbumesButton.setOnAction(e -> new UserAlbumScreen(stage).show());

        Button historialComprasButton = new Button("Historial de Compras");
        historialComprasButton.setOnAction(e -> new UserHistorialScreen(stage).show());

        Button datosPersonalesButton = new Button("Datos Personales");
        datosPersonalesButton.setOnAction(e -> new UserDatosScreen(stage).show());

        // Layout
        VBox vbox = new VBox(15, welcomeLabel, compraCancionesButton, compraAlbumesButton, historialComprasButton, datosPersonalesButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Crear la escena y mostrarla
        Scene scene = new Scene(vbox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Usuario - Menú Principal");
        stage.show();
    }
}
