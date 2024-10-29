package org.example.parcial2.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminScreen {

    private final Stage stage;

    public AdminScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label welcomeLabel = new Label("Bienvenido Administrador");

        Button usuariosButton = new Button("Usuarios");
        Button cancionesButton = new Button("Canciones");
        Button albumesButton = new Button("Álbumes");
        Button artistasButton = new Button("Artistas");
        Button generosButton = new Button("Géneros");

        usuariosButton.setOnAction(e -> new AdminUsuariosScreen(stage).show());
        cancionesButton.setOnAction(e -> new AdminCancionesScreen(stage).show());
        albumesButton.setOnAction(e -> new AdminAlbumesScreen(stage).show());
        artistasButton.setOnAction(e -> new AdminArtistasScreen(stage).show());
        generosButton.setOnAction(e -> new AdminGenerosScreen(stage).show());

        VBox vbox = new VBox(10, welcomeLabel, usuariosButton, cancionesButton, albumesButton, artistasButton, generosButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 300, 300);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        //scene.getStylesheets().add("resources/styles.css");
        stage.setScene(scene);
        stage.setTitle("Ventana de Administrador");
        stage.show();
    }
}
