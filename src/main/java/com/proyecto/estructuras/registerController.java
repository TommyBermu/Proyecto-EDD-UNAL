package com.proyecto.estructuras;

import com.proyecto.estructuras.models.Estudiante;
import com.proyecto.estructuras.models.StudentDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class registerController {

    @FXML
    private TextField txtStudentId;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtSocioeconomicScore;
    @FXML
    public Button btnRegister;
    @FXML
    private Label lblMessage;

    private final StudentDataManager dataManager = StudentDataManager.getInstance();

    @FXML
    public void initialize() {
        // para inicializar con valores :)
    }

    @FXML
    private void handleRegister() {
        String idText = txtStudentId.getText();
        String fullName = txtFullName.getText();
        String scoreText = txtSocioeconomicScore.getText();

        // TODO **Tu lógica de validación e implementación aquí**, por ejemplo:

        if (idText.isEmpty() || fullName.isEmpty() || scoreText.isEmpty()) {
            lblMessage.setText("Error: Todos los campos son obligatorios.");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            int score = Integer.parseInt(scoreText);

            dataManager.addEstudiante(new Estudiante(Integer.parseInt(idText), fullName, score, false));

            lblMessage.setText("Estudiante " + fullName + " con ID " + id + " registrado con éxito.");
            lblMessage.setStyle("-fx-text-fill: green;");

            // Limpiar campos después del registro exitoso
            txtStudentId.clear();
            txtFullName.clear();
            txtSocioeconomicScore.clear();

        } catch (NumberFormatException e) {
            lblMessage.setText("Error: ID y Puntaje Socioeconómico deben ser números válidos.");
            lblMessage.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            // Capturar otras excepciones de tu lógica de negocio (ej. ID repetido)
            lblMessage.setText("Error al registrar estudiante: " + e.getMessage());
            lblMessage.setStyle("-fx-text-fill: red;");
        }
    }
}