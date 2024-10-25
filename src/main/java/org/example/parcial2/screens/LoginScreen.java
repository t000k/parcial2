package org.example.parcial2.screens;


import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.parcial2.utils.Database;

public class LoginScreen {

    // Método que devuelve la escena de login
    public Scene getLoginScene(Stage stage) {
        // Crear elementos de la pantalla de login
        Label userLabel = new Label("Usuario:");
        TextField userField = new TextField();

        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Iniciar sesión");
        Label createAccountLabel = new Label("Crea una cuenta");
        createAccountLabel.setStyle("-fx-text-fill: blue; -fx-underline: true;");

        // Layout
        VBox vbox = new VBox(10, userLabel, userField, passwordLabel, passwordField, loginButton, createAccountLabel);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color: #f0f0f0;");

        Scene scene = new Scene(vbox, 300, 200);
        scene.getStylesheets().add("resources/styles.css"); // Archivo CSS

        // Acción del botón "Iniciar sesión"
        loginButton.setOnAction(e -> {
            String username = userField.getText();
            String password = passwordField.getText();
            Database db = new Database();
            if (db.authenticateUser(username, password)) {
                String role = db.getUserRole(username);
                if (role.equals("admin")) {
                    new AdminScreen(stage).show(); // Abrir interfaz de administrador
                } else if (role.equals("user")) {
                   // new UserScreen(stage).show(); // Abrir interfaz de usuario
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Usuario o password incorrectos");
                alert.showAndWait();
            }
        });

        // Acción del label "Crea una cuenta"
        createAccountLabel.setOnMouseClicked(e -> {
            new RegisterScreen(stage);
        });

        return scene; // Devolver la escena para que el Main la maneje
    }
}
