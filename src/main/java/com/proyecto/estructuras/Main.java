package com.proyecto.estructuras;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image; // Importa Image
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(@NotNull Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainView.fxml"));
        stage.setTitle("Sistema de asignación de residencias UNAL");
        try {

            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/proyecto/estructuras/images/app_icon.png")));
            stage.getIcons().add(icon);

        } catch (NullPointerException e) {
            System.err.println("Error: No se pudo cargar el ícono de la aplicación. Asegúrate de que 'app_icon.png' esté en src/main/resources/images/");
            System.err.println("Error ejecutando main: " + e.getMessage());
        }
        Scene scene = new Scene(fxmlLoader.load(), 920, 620);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}