package org.example.parcial2.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.parcial2.utils.Database;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

public class AdminAlbumesScreen {

    private final Stage stage;
    private final Database db;
    private final TableView<String[]> albumTable;
    private File imageFile = null; // Archivo de imagen seleccionado
    private ImageView albumCoverView; // Declaración al nivel de clase

    public AdminAlbumesScreen(Stage stage) {
        this.stage = stage;
        this.db = new Database();
        this.albumTable = new TableView<>();
    }

    public void show() {
        Label label = new Label("Administración de Álbumes");

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre del álbum");

        DatePicker fechaLanzamientoPicker = new DatePicker();
        fechaLanzamientoPicker.setPromptText("Fecha de lanzamiento");

        albumCoverView = new ImageView();
        albumCoverView.setFitWidth(100);
        albumCoverView.setFitHeight(100);
        albumCoverView.setPreserveRatio(true);

        Button selectImageButton = new Button("Seleccionar imagen");
        selectImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
            imageFile = fileChooser.showOpenDialog(stage);

            if (imageFile != null) {
                if (imageFile.getName().endsWith(".png") || imageFile.getName().endsWith(".jpg") || imageFile.getName().endsWith(".jpeg")) {
                    Image albumImage = new Image(imageFile.toURI().toString());
                    albumCoverView.setImage(albumImage);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Formato de archivo no soportado. Seleccione una imagen PNG, JPG o JPEG.");
                    alert.showAndWait();
                }
            }
        });

        Button addButton = new Button("Añadir álbum");
        addButton.setOnAction(e -> {
            String nombreAlbum = nombreField.getText();
            String fechaLanzamiento = (fechaLanzamientoPicker.getValue() != null) ? fechaLanzamientoPicker.getValue().toString() : null;

            if (db.addAlbum(nombreAlbum, fechaLanzamiento, imageFile)) {
                loadAlbums();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Álbum añadido exitosamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo añadir el álbum.");
                alert.showAndWait();
            }

            nombreField.clear();
            fechaLanzamientoPicker.setValue(null);
            albumCoverView.setImage(null);
            imageFile = null;
        });

        // Campo y botón para eliminar álbumes por ID
        TextField eliminarIdField = new TextField();
        eliminarIdField.setPromptText("ID del álbum a eliminar");

        Button deleteButton = new Button("Eliminar álbum");
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(eliminarIdField.getText());
                if (db.deleteAlbumById(id)) {
                    loadAlbums();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Álbum eliminado exitosamente.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No se encontró el álbum con el ID especificado.");
                    alert.showAndWait();
                }
                eliminarIdField.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ingrese un ID válido.");
                alert.showAndWait();
            }
        });

        Button albumSongsButton = new Button("Canciones de Álbum");
        albumSongsButton.setOnAction(e -> new AdminAlbumCancionScreen(stage).show());

        TableColumn<String[], String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));

        TableColumn<String[], String> nombreCol = new TableColumn<>("Nombre del Álbum");
        nombreCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));

        TableColumn<String[], String> fechaCol = new TableColumn<>("Fecha de Lanzamiento");
        fechaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));

        albumTable.getColumns().addAll(idCol, nombreCol, fechaCol);
        loadAlbums();

        Button backButton = new Button("Regresar");
        backButton.setOnAction(e -> new AdminScreen(stage).show());

        VBox vbox = new VBox(10, label, albumCoverView, selectImageButton, nombreField, fechaLanzamientoPicker, addButton, eliminarIdField, deleteButton, albumSongsButton, albumTable, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 700, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Admin - Álbumes");
        stage.show();
    }

    private void loadAlbums() {
        ObservableList<String[]> albums = FXCollections.observableArrayList(db.getAllAlbumsWithDetails());
        albumTable.setItems(albums);

        albumTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                byte[] imageBytes = db.getAlbumImageById(Integer.parseInt(newSelection[0]));
                if (imageBytes != null) {
                    Image albumImage = new Image(new ByteArrayInputStream(imageBytes));
                    albumCoverView.setImage(albumImage);
                } else {
                    albumCoverView.setImage(null);
                }
            }
        });
    }
}
