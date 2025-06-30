package com.proyecto.estructuras;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.Objects;

public class mainController {
    @FXML
    public AnchorPane mainRootPane;
    @FXML
    private AnchorPane contentPane; // El AnchorPane donde se cargarán las vistas secundarias

    @FXML
    public Button btnRegisterStudent;
    @FXML
    public Button btnConsultStudent;
    @FXML
    public Button btnAssignSlots;
    @FXML
    public Button btnListings;

    // para inicializar el controlador (se llama automáticamente después de que se carga el FXML)
    @FXML
    public void initialize() {
        handleRegisterStudent(); // se carga la vista de registro al iniciar la app
    }

    @FXML
    private void handleRegisterStudent() {
        try {
            Parent registerView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/proyecto/estructuras/registerView.fxml")));
            contentPane.getChildren().setAll(registerView); // Carga la vista de registro en el contentPane
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de registro: " + e.getMessage());
        }
    }

    @FXML
    private void handleConsultStudent() {
        try {
            Parent consultView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/proyecto/estructuras/consultView.fxml")));
            contentPane.getChildren().setAll(consultView); // Carga la vista de consulta en el contentPane
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de consulta: " + e.getMessage());
        }
    }

    @FXML
    private void handleAssignSlots() {
        try {
            Parent assignView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/proyecto/estructuras/assignView.fxml")));
            contentPane.getChildren().setAll(assignView); // Carga la vista de asignación en el contentPane
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de asignación: " + e.getMessage());
        }
    }

    @FXML
    private void handleListings() {
        try {
            Parent listingView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/proyecto/estructuras/listingView.fxml")));
            contentPane.getChildren().setAll(listingView); // Carga la vista de listados en el contentPane
        } catch (IOException e) {
            System.err.println("Error al cargar la vista de listados: " + e.getMessage());
        }
    }
}