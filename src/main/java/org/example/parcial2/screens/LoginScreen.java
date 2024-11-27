package org.example.parcial2.screens;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.parcial2.utils.Database;

public class LoginScreen {

    public Scene getLoginScene(Stage stage) {
        Label userLabel = new Label("Usuario:");
        TextField userField = new TextField();

        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Iniciar sesión");
        Label createAccountLabel = new Label("Crea una cuenta");
        createAccountLabel.setStyle("-fx-text-fill: blue; -fx-underline: true;");

        VBox vbox = new VBox(10, userLabel, userField, passwordLabel, passwordField, loginButton, createAccountLabel);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #f0f0f0;");

        Scene scene = new Scene(vbox, 300, 200);
        scene.getStylesheets().add("resources/styles.css");

        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passwordField.getText();
            Database db = new Database();
            int userId = db.authenticateUser(username, password); // Obtiene el userId

            if (userId != -1) {
                String role = db.getUserRole(username);
                if (role.equals("user")) {
                    new UserScreen(stage, userId).show(); // Pasa el userId a UserScreen
                } else if (role.equals("admin")) {
                    new AdminScreen(stage).show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Usuario o password incorrectos");
                alert.showAndWait();
            }
        });

        createAccountLabel.setOnMouseClicked(e -> new RegisterScreen(stage));
        return scene;
    }
}
