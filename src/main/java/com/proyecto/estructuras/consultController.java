package com.proyecto.estructuras;

import com.proyecto.estructuras.models.Estudiante;
import com.proyecto.estructuras.models.StudentDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Optional;

public class consultController {

    @FXML
    private TextField txtSearchId;
    @FXML
    public Button btnSearch;
    @FXML
    private Label lblMessage;
    @FXML
    private GridPane studentDetailsGrid; // Contenedor para los detalles del estudiante
    @FXML
    private Label lblStudentId;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblSocioeconomicScore;
    @FXML
    private Label lblResidenceStatus;
    @FXML
    private HBox actionButtons; // Contenedor para los botones de acción (modificar/eliminar)
    @FXML
    public Button btnModifyScore;
    @FXML
    public Button btnDeleteStudent;

    private Estudiante currentStudent; // Variable para almacenar el estudiante actualmente consultado

    private final StudentDataManager dataManager = StudentDataManager.getInstance();

    @FXML
    public void initialize() {
        // Inicialmente, oculta los detalles del estudiante y los botones de acción
        studentDetailsGrid.setVisible(false);
        studentDetailsGrid.setManaged(false);
        actionButtons.setVisible(false);
        actionButtons.setManaged(false);
    }

    @FXML
    private void handleSearch() {
        String searchIdText = txtSearchId.getText();
        lblMessage.setText("");
        studentDetailsGrid.setVisible(false);
        studentDetailsGrid.setManaged(false);
        actionButtons.setVisible(false);
        actionButtons.setManaged(false);

        if (searchIdText.isEmpty()) {
            lblMessage.setText("Por favor, ingrese un ID para buscar.");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int id = Integer.parseInt(searchIdText);
            Estudiante estudianteEncontrado = dataManager.getEstudiante(id);

            if (estudianteEncontrado != null) {
                currentStudent = estudianteEncontrado;
                lblStudentId.setText(String.valueOf(currentStudent.getId()));
                lblFullName.setText(currentStudent.getNombre());
                lblSocioeconomicScore.setText(String.valueOf(currentStudent.getPuntaje()));
                lblResidenceStatus.setText(currentStudent.getResidencia() ? "Asignada" : "Sin asignar");

                studentDetailsGrid.setVisible(true);
                studentDetailsGrid.setManaged(true);
                actionButtons.setVisible(true);
                actionButtons.setManaged(true);
                lblMessage.setText("Estudiante encontrado.");
                lblMessage.setStyle("-fx-text-fill: green;");
            } else {
                lblMessage.setText("Estudiante con ID " + id + " no encontrado.");
                lblMessage.setStyle("-fx-text-fill: red;");
            }

        } catch (NumberFormatException e) {
            lblMessage.setText("Error: El ID debe ser un número válido.");
            lblMessage.setStyle("-fx-text-fill: red;");
        } catch (Exception e) {
            lblMessage.setText("Error al buscar estudiante: " + e.getMessage());
            lblMessage.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleModifyScore() {
        if (currentStudent == null) {
            lblMessage.setText("No hay estudiante seleccionado para modificar.");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(String.valueOf(currentStudent.getPuntaje()));
        dialog.setTitle("Modificar Puntaje Socioeconómico");
        dialog.setHeaderText("Modificar Puntaje para " + currentStudent.getNombre());
        dialog.setContentText("Nuevo Puntaje:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newScoreText -> {
            try {
                int newScore = Integer.parseInt(newScoreText);

                dataManager.updateEstudiante(currentStudent.getId(), new Estudiante(currentStudent.getId(), currentStudent.getNombre(), newScore, currentStudent.getResidencia()));

                currentStudent.setPuntaje(newScore);
                lblSocioeconomicScore.setText(String.valueOf(newScore));
                lblMessage.setText("Puntaje de " + currentStudent.getNombre() + " actualizado con éxito.");
                lblMessage.setStyle("-fx-text-fill: green;");

            } catch (NumberFormatException e) {
                lblMessage.setText("Error: El puntaje debe ser un número válido.");
                lblMessage.setStyle("-fx-text-fill: red;");
            } catch (Exception e) {
                lblMessage.setText("Error al modificar puntaje: " + e.getMessage());
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        });
    }

    @FXML
    private void handleDeleteStudent() {
        if (currentStudent == null) {
            lblMessage.setText("No hay estudiante seleccionado para eliminar.");
            lblMessage.setStyle("-fx-text-fill: red;");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Está seguro que desea eliminar a " + currentStudent.getNombre() + "?");
        alert.setContentText("Esta acción es irreversible.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {

                dataManager.removeEstudiante(currentStudent.getId());

                lblMessage.setText("Estudiante " + currentStudent.getNombre() + " eliminado con éxito.");
                lblMessage.setStyle("-fx-text-fill: green;");

                // Limpiar la UI después de la eliminación
                txtSearchId.clear();
                studentDetailsGrid.setVisible(false);
                studentDetailsGrid.setManaged(false);
                actionButtons.setVisible(false);
                actionButtons.setManaged(false);
                currentStudent = null; // Reiniciar el estudiante actual
            } catch (Exception e) {
                lblMessage.setText("Error al eliminar estudiante: " + e.getMessage());
                lblMessage.setStyle("-fx-text-fill: red;");
            }
        }
    }
}