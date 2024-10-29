package org.example.parcial2.screens;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.parcial2.utils.Database;

public class RegisterScreen {

    public RegisterScreen(Stage stage) {
        // Crear los elementos del formulario de registro
        Label nameLabel = new Label("Nombre:");
        TextField nameField = new TextField();

        Label phoneLabel = new Label("Teléfono:");
        TextField phoneField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label userLabel = new Label("Usuario:");
        TextField userField = new TextField();

        Label passwordLabel = new Label("Contraseña:");
        PasswordField passwordField = new PasswordField();

        Button registerButton = new Button("Registrar");
        Button backButton = new Button("Volver");

        VBox vbox = new VBox(10, nameLabel, nameField, phoneLabel, phoneField, emailLabel, emailField,
                userLabel, userField, passwordLabel, passwordField,
                registerButton, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 350, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        scene.getStylesheets().add("resources/styles.css");

        stage.setScene(scene);
        stage.setTitle("Registro");
        stage.show();

        // Acción del botón "Registrar"
        registerButton.setOnAction(e -> {
            Database db = new Database();
            if (db.isUserExists(userField.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "El nombre de usuario ya existe. Por favor, elige otro.");
                alert.showAndWait();
            } else {
                db.createUser(nameField.getText(), phoneField.getText(), emailField.getText(), userField.getText(), passwordField.getText(), "user");
                // Crear una instancia de LoginScreen y mostrar la escena de login
                LoginScreen loginScreen = new LoginScreen();
                stage.setScene(loginScreen.getLoginScene(stage)); // Llamar al método getLoginScene
            }
        });

        // Acción del botón "Volver"
        backButton.setOnAction(e -> {
            // Crear una instancia de LoginScreen y mostrar la escena de login
            LoginScreen loginScreen = new LoginScreen();
            stage.setScene(loginScreen.getLoginScene(stage)); // Llamar al método getLoginScene
        });
    }
}
