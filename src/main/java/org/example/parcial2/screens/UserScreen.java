package org.example.parcial2.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserScreen {

    private final Stage stage;
    private final int userId;

    public UserScreen(Stage stage, int userId) {
        this.stage = stage;
        this.userId = userId;
    }

    public void show() {
        Button datosButton = new Button("Datos Personales");
        datosButton.setOnAction(e -> new UserDatosScreen(stage, userId).show()); // Pasa el userId

        Button historialButton = new Button("Historial de Compras");
        historialButton.setOnAction(e -> new UserHistorialScreen(stage, userId).show()); // Pasa el userId

        Button cancionesButton = new Button("Comprar Canciones");
        cancionesButton.setOnAction(e -> new UserCancionScreen(stage, userId).show()); // Pasa el userId

        Button albumesButton = new Button("Comprar Álbumes");
        albumesButton.setOnAction(e -> new UserAlbumScreen(stage, userId).show()); // Pasa el userId

        VBox vbox = new VBox(10, datosButton, historialButton, cancionesButton, albumesButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 300, 200);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Menú Principal de Usuario");
        stage.show();
    }
}
