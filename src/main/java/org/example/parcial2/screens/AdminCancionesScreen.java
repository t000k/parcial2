package org.example.parcial2.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.parcial2.utils.Database;

public class AdminCancionesScreen {

    private final Stage stage;
    private final Database db;
    private final TableView<String[]> songTable;

    public AdminCancionesScreen(Stage stage) {
        this.stage = stage;
        this.db = new Database();
        this.songTable = new TableView<>();
    }

    public void show() {
        Label label = new Label("Administración de Canciones");

        // Campos para ingresar datos de la canción
        TextField tituloField = new TextField();
        tituloField.setPromptText("Título de la canción");

        TextField duracionField = new TextField();
        duracionField.setPromptText("Duración (HH:MM:SS)");

        // Configuración de ComboBox para mostrar solo los nombres en lugar de caracteres raros
        ComboBox<String[]> generoBox = new ComboBox<>(FXCollections.observableArrayList(db.getAllGenres()));
        generoBox.setPromptText("Seleccione el género");
        generoBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String[] item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item[1]);
            }
        });
        generoBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String[] item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item[1]);
            }
        });

        ComboBox<String[]> artistaBox = new ComboBox<>(FXCollections.observableArrayList(db.getAllArtists()));
        artistaBox.setPromptText("Seleccione el artista");
        artistaBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String[] item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item[1]);
            }
        });
        artistaBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String[] item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item[1]);
            }
        });

        ComboBox<String[]> albumBox = new ComboBox<>(FXCollections.observableArrayList(db.getAllAlbums()));
        albumBox.setPromptText("Seleccione el álbum");
        albumBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(String[] item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item[1]);
            }
        });
        albumBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(String[] item, boolean empty) {
                super.updateItem(item, empty);
                setText((item == null || empty) ? null : item[1]);
            }
        });

        Button addButton = new Button("Añadir canción");
        addButton.setOnAction(e -> {
            try {
                String titulo = tituloField.getText();
                String duracion = duracionField.getText();
                int generoId = Integer.parseInt(generoBox.getValue()[0]);
                int artistaId = Integer.parseInt(artistaBox.getValue()[0]);
                int albumId = Integer.parseInt(albumBox.getValue()[0]);

                if (db.addSong(titulo, duracion, generoId, artistaId, albumId)) {
                    loadSongs();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Canción añadida exitosamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo añadir la canción.");
                    alert.showAndWait();
                }

                tituloField.clear();
                duracionField.clear();
                generoBox.setValue(null);
                artistaBox.setValue(null);
                albumBox.setValue(null);

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ingrese un valor válido para los campos.");
                alert.showAndWait();
            }
        });

        // Campo y botón para eliminar canciones por ID
        TextField eliminarIdField = new TextField();
        eliminarIdField.setPromptText("ID de la canción a eliminar");

        Button deleteButton = new Button("Eliminar canción");
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(eliminarIdField.getText());
                if (db.deleteSongById(id)) {
                    loadSongs();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Canción eliminada exitosamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No se encontró la canción con el ID especificado.");
                    alert.showAndWait();
                }
                eliminarIdField.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ingrese un ID válido.");
                alert.showAndWait();
            }
        });

        // Configuración de la tabla de canciones
        TableColumn<String[], String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));

        TableColumn<String[], String> tituloCol = new TableColumn<>("Título");
        tituloCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));

        TableColumn<String[], String> duracionCol = new TableColumn<>("Duración (HH:MM:SS)");
        duracionCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));

        TableColumn<String[], String> generoCol = new TableColumn<>("Género");
        generoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));

        TableColumn<String[], String> artistaCol = new TableColumn<>("Artista");
        artistaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[4]));

        TableColumn<String[], String> albumCol = new TableColumn<>("Álbum");
        albumCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[5]));

        songTable.getColumns().addAll(idCol, tituloCol, duracionCol, generoCol, artistaCol, albumCol);
        loadSongs();

        // Botón para regresar a la pantalla principal de administración
        Button backButton = new Button("Regresar");
        backButton.setOnAction(e -> new AdminScreen(stage).show());

        VBox vbox = new VBox(10, label, tituloField, duracionField, generoBox, artistaBox, albumBox, addButton,
                eliminarIdField, deleteButton, songTable, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 700, 600);
        stage.setScene(scene);
        stage.setTitle("Admin - Canciones");
        stage.show();
    }

    private void loadSongs() {
        ObservableList<String[]> songs = FXCollections.observableArrayList(db.getAllSongsWithDetails());
        songTable.setItems(songs);
    }
}
