package org.example.parcial2.screens;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminCancionesScreen {

    private final Stage stage;

    public AdminCancionesScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label label = new Label("Administración de Canciones");
        Button backButton = new Button("Regresar");

        backButton.setOnAction(e -> new AdminScreen(stage).show());

        VBox vbox = new VBox(10, label, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Admin - Canciones");
        stage.show();
    }
}
