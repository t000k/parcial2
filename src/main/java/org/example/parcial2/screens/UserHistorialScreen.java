package org.example.parcial2.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.parcial2.utils.Database;

public class UserHistorialScreen {

    private final Stage stage;
    private final Database db;
    private final int userId;
    private TableView<String[]> historialTable;

    public UserHistorialScreen(Stage stage, int userId) {
        this.stage = stage;
        this.db = new Database();
        this.userId = userId;
        this.historialTable = new TableView<>();
    }

    public void show() {
        Label label = new Label("Historial de Compras");

        TableColumn<String[], String> fechaCol = new TableColumn<>("Fecha");
        fechaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));

        TableColumn<String[], String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));

        TableColumn<String[], String> tipoCol = new TableColumn<>("Tipo");
        tipoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));

        TableColumn<String[], String> idCol = new TableColumn<>("ID Venta");
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));

        historialTable.getColumns().addAll(fechaCol, totalCol, tipoCol, idCol);
        loadHistorial();

        Button backButton = new Button("Regresar");
        backButton.setOnAction(e -> new UserScreen(stage, userId).show());

        VBox vbox = new VBox(10, label, historialTable, backButton);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(vbox, 500, 400);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Historial de Compras");
        stage.show();
    }

    private void loadHistorial() {
        ObservableList<String[]> historial = FXCollections.observableArrayList(db.getHistorialCompras(userId));
        historialTable.setItems(historial);
    }
}
