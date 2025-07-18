package com.proyecto.estructuras;

import com.proyecto.estructuras.models.Estudiante;
import com.proyecto.estructuras.models.StudentDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.ArrayList;
import java.util.List;

public class assignController {

    @FXML
    private TextField txtAvailableSlots;
    @FXML
    public Button btnUpdateSlots;
    @FXML
    public Button btnExecuteAssignment;
    @FXML
    private Label lblAssignmentSummary;
    @FXML
    private TableView<Estudiante> tblAssignedStudents;
    @FXML
    private TableColumn<Estudiante, Number> colAssignedId;
    @FXML
    private TableColumn<Estudiante, String> colAssignedName;
    @FXML
    private TableColumn<Estudiante, Number> colAssignedScore;
    @FXML
    private TableView<Estudiante> tblUnassignedStudents;
    @FXML
    private TableColumn<Estudiante, Number> colUnassignedId;
    @FXML
    private TableColumn<Estudiante, String> colUnassignedName;
    @FXML
    private TableColumn<Estudiante, Number> colUnassignedScore;

    private ObservableList<Estudiante> allStudents = FXCollections.observableArrayList();
    private ObservableList<Estudiante> assignedStudents = FXCollections.observableArrayList();
    private ObservableList<Estudiante> unassignedStudents = FXCollections.observableArrayList();

    private final StudentDataManager dataManager = StudentDataManager.getInstance();

    private int availableSlots = dataManager.getCuposDisponibles();

    @FXML
    public void initialize() {
        txtAvailableSlots.setText(String.valueOf(availableSlots));

        colAssignedId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colAssignedName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colAssignedScore.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPuntaje()));

        colUnassignedId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colUnassignedName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colUnassignedScore.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPuntaje()));

        allStudents = FXCollections.observableArrayList(dataManager.getAllEstudiantes());
        for (Estudiante student : allStudents)
            if (student.getResidencia())
                assignedStudents.add(student);
            else
                unassignedStudents.add(student);

        tblAssignedStudents.setItems(assignedStudents);
        tblUnassignedStudents.setItems(unassignedStudents);
    }

    @FXML
    private void handleUpdateSlots() {
        String slotsText = txtAvailableSlots.getText();
        if (slotsText.isEmpty()) {
            lblAssignmentSummary.setText("Error: Ingrese un número de cupos válido.");
            lblAssignmentSummary.setStyle("-fx-text-fill: red;");
            return;
        }
        try {
            int slots = Integer.parseInt(slotsText);
            if (slots < 0) {
                lblAssignmentSummary.setText("Error: El número de cupos no puede ser negativo.");
                lblAssignmentSummary.setStyle("-fx-text-fill: red;");
                return;
            }
            dataManager.setCuposDisponibles(availableSlots = slots);
            lblAssignmentSummary.setText("Cupos disponibles actualizados a: " + availableSlots);
            lblAssignmentSummary.setStyle("-fx-text-fill: green;");
        } catch (NumberFormatException e) {
            lblAssignmentSummary.setText("Error: El número de cupos debe ser un número entero.");
            lblAssignmentSummary.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleExecuteAssignment() {
        if (allStudents.isEmpty()) {
            lblAssignmentSummary.setText("No hay estudiantes registrados para asignar cupos.");
            lblAssignmentSummary.setStyle("-fx-text-fill: orange;");
            return;
        }

        if (availableSlots <= 0) {
            lblAssignmentSummary.setText("Error: Debe haber cupos disponibles (> 0) para realizar la asignación.");
            lblAssignmentSummary.setStyle("-fx-text-fill: red;");
            return;
        }

        assignedStudents.clear();
        unassignedStudents.clear();

        int assignedCount = 0;
        for (Estudiante student : allStudents) {
            if (assignedCount < availableSlots) {
                dataManager.assignEstudiante(student.getId(), true);
                assignedStudents.add(student);
                assignedCount++;
            } else {
                dataManager.assignEstudiante(student.getId(), false);
                unassignedStudents.add(student);
            }
        }

        lblAssignmentSummary.setText("Asignación completada. Se asignaron " + assignedCount + " de " + availableSlots + " cupos.");
        lblAssignmentSummary.setStyle("-fx-text-fill: green;");

        tblAssignedStudents.setItems(assignedStudents);
        tblUnassignedStudents.setItems(unassignedStudents);
    }
}