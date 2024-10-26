package org.example.parcial2.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.parcial2.utils.Database;

public class AdminAlbumCancionScreen {

    private final Stage stage;
    private final Database db;
    private final TableView<String[]> songTable;

    public AdminAlbumCancionScreen(Stage stage) {
        this.stage = stage;
        this.db = new Database();
        this.songTable = new TableView<>();
    }

    public void show() {
        Label label = new Label("Canciones del Álbum");

        ComboBox<String[]> albumBox = new ComboBox<>(FXCollections.observableArrayList(db.getAllAlbums()));
        albumBox.setPromptText("Seleccione un álbum");
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

        Label albumLabel = new Label("Canciones del Álbum: ");

        albumBox.setOnAction(e -> {
            if (albumBox.getValue() != null) {
                String albumName = albumBox.getValue()[1];
                int albumId = Integer.parseInt(albumBox.getValue()[0]);
                albumLabel.setText("Canciones del Álbum: " + albumName);
                loadSongsForAlbum(albumId);
            }
        });

        // Configuración de la tabla de canciones
        TableColumn<String[], String> idCol = new TableColumn<>("ID Canción");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));

        TableColumn<String[], String> tituloCol = new TableColumn<>("Título");
        tituloCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));

        TableColumn<String[], String> duracionCol = new TableColumn<>("Duración");
        duracionCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));

        songTable.getColumns().addAll(idCol, tituloCol, duracionCol);

        Button backButton = new Button("Regresar");
        backButton.setOnAction(e -> new AdminAlbumesScreen(stage).show());

        VBox vbox = new VBox(10, label, albumBox, albumLabel, songTable, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 700, 500);
        stage.setScene(scene);
        stage.setTitle("Canciones de Álbum");
        stage.show();
    }

    private void loadSongsForAlbum(int albumId) {
        ObservableList<String[]> songs = FXCollections.observableArrayList(db.getSongsByAlbumId(albumId));
        songTable.setItems(songs);
    }
}
