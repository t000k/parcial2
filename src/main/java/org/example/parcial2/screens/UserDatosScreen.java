package org.example.parcial2.screens;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.parcial2.utils.Database;

public class UserDatosScreen {

    private final Stage stage;
    private final Database db;
    private final int userId;

    // Campos de datos personales
    private TextField nombreField;
    private TextField telefonoField;
    private TextField emailField;

    public UserDatosScreen(Stage stage, int userId) {
        this.stage = stage;
        this.db = new Database();
        this.userId = userId;
    }

    public void show() {
        Label label = new Label("Datos Personales");

        // Inicialización de campos
        nombreField = new TextField();
        nombreField.setEditable(false);

        telefonoField = new TextField();
        emailField = new TextField();

        Button actualizarButton = new Button("Actualizar Datos");
        actualizarButton.setOnAction(e -> actualizarDatos());

        Button backButton = new Button("Regresar");
        backButton.setOnAction(e -> new UserScreen(stage, userId).show()); // Botón para regresar a UserScreen

        loadUserData(); // Cargar datos desde la base de datos

        // Layout
        VBox vbox = new VBox(10, label, new Label("Nombre:"), nombreField, new Label("Teléfono:"), telefonoField, new Label("Email:"), emailField, actualizarButton, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Datos Personales");
        stage.show();
    }

    private void loadUserData() {
        String[] userData = db.getUserDataById(userId);
        if (userData != null) {
            nombreField.setText(userData[0]);
            telefonoField.setText(userData[1]);
            emailField.setText(userData[2]);
        }
    }

    private void actualizarDatos() {
        String telefono = telefonoField.getText();
        String email = emailField.getText();

        boolean success = db.updateUserData(userId, telefono, email);
        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                success ? "Datos actualizados exitosamente." : "Error al actualizar los datos.");
        alert.showAndWait();
    }
}
