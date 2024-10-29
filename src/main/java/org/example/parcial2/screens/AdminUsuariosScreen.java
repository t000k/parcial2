package org.example.parcial2.screens;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.parcial2.utils.Database;
import org.example.parcial2.models.User;

public class AdminUsuariosScreen {

    private final Stage stage;
    private final Database db;
    private final TableView<User> userTable;

    public AdminUsuariosScreen(Stage stage) {
        this.stage = stage;
        this.db = new Database();
        this.userTable = new TableView<>();
    }

    public void show() {
        Label label = new Label("Administración de Usuarios");

        // Configurar la tabla de usuarios
        TableColumn<User, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idUser"));

        TableColumn<User, String> nombreCol = new TableColumn<>("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<User, String> telCol = new TableColumn<>("Teléfono");
        telCol.setCellValueFactory(new PropertyValueFactory<>("telUser"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("emailUser"));

        TableColumn<User, String> userCol = new TableColumn<>("Usuario");
        userCol.setCellValueFactory(new PropertyValueFactory<>("user"));

        TableColumn<User, String> passwordCol = new TableColumn<>("Contraseña");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<User, String> roleCol = new TableColumn<>("Rol");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTable.getColumns().addAll(idCol, nombreCol, telCol, emailCol, userCol, passwordCol, roleCol);
        loadUsers();

        // Cuadro de texto y botón para eliminar usuario
        TextField idField = new TextField();
        idField.setPromptText("ID de usuario a eliminar");

        Button deleteButton = new Button("Eliminar usuario");
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());

                // Confirmación de eliminación
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION,
                        "¿Está seguro de que desea eliminar el usuario con ID " + id + "?",
                        ButtonType.YES, ButtonType.NO);
                confirmAlert.setTitle("Confirmación de eliminación");
                confirmAlert.setHeaderText(null);

                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        if (db.deleteUserById(id)) {
                            loadUsers();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Usuario eliminado exitosamente.");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "El usuario con el ID especificado no existe.");
                            alert.showAndWait();
                        }
                    }
                });
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, ingrese un ID válido.");
                alert.showAndWait();
            }
        });

        Button backButton = new Button("Regresar");
        backButton.setOnAction(e -> new AdminScreen(stage).show());

        VBox vbox = new VBox(10, label, userTable, idField, deleteButton, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 700, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Admin - Usuarios");
        stage.show();
    }

    private void loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList(db.getAllUsers());
        userTable.setItems(users);
    }
}
