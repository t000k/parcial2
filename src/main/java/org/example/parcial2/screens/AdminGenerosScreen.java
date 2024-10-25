package org.example.parcial2.screens;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminGenerosScreen {

    private final Stage stage;

    public AdminGenerosScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label label = new Label("Administración de Géneros");
        Button backButton = new Button("Regresar");

        backButton.setOnAction(e -> new AdminScreen(stage).show());

        VBox vbox = new VBox(10, label, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Admin - Géneros");
        stage.show();
    }
}
