package org.example.parcial2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.parcial2.screens.LoginScreen;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Crear una instancia de LoginScreen y obtener la escena de login
        LoginScreen loginScreen = new LoginScreen();
        Scene loginScene = loginScreen.getLoginScene(primaryStage);

        // Configurar la escena en el stage principal
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}