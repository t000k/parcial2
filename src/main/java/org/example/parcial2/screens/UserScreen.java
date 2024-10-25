package org.example.parcial2.screens;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserScreen {

    public UserScreen(Stage stage) {
        Label label = new Label("Bienvenido, Usuario");
        VBox vbox = new VBox(label);
        Scene scene = new Scene(vbox, 400, 200);

        stage.setScene(scene);
        stage.setTitle("User Dashboard");
        stage.show();
    }
}