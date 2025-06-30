package com.proyecto.estructuras;

import com.proyecto.estructuras.models.Estudiante;
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
import java.util.Comparator;
import java.util.List;

public class assignController {

    @FXML
    private TextField txtAvailableSlots;
    @FXML
    private Button btnUpdateSlots;
    @FXML
    private Button btnExecuteAssignment;
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

    private int availableSlots = 0; // Número de cupos disponibles

    @FXML
    public void initialize() {
        colAssignedId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colAssignedName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colAssignedScore.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPuntaje()));

        colUnassignedId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colUnassignedName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colUnassignedScore.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPuntaje()));

        tblAssignedStudents.setItems(assignedStudents);
        tblUnassignedStudents.setItems(unassignedStudents);

        // TODO Cargar datos de prueba o estado inicial (hacerlo desde un json o base de datos)
        loadInitialDataMock();
        updateTables();
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
            availableSlots = slots;
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

        // TODO **Tu lógica de asignación aquí**
        // 1. Obtener la lista de estudiantes de tu estructura de datos (ej. Min-Heap)
        // 2. Ordenarlos por puntaje socioeconómico (menor puntaje = mayor prioridad)
        // 3. Asignar cupos a los primeros 'availableSlots' estudiantes
        // 4. Actualizar el estado de residencia de cada estudiante

        // Simulación de la lógica de asignación:
        // Primero, limpia las listas de asignados y no asignados
        assignedStudents.clear();
        unassignedStudents.clear();

        List<Estudiante> studentsForAssignment = new ArrayList<>(allStudents);

        // TODO Ordenar estudiantes por puntaje socioeconómico de forma creciente (menor puntaje = mayor necesidad) hacerlo con heapSort con el minHeap
        studentsForAssignment.sort(Comparator.comparingDouble(Estudiante::getPuntaje));

        int assignedCount = 0;
        for (Estudiante student : studentsForAssignment) {
            if (assignedCount < availableSlots) {
                student.setEstado(true);
                assignedStudents.add(student);
                assignedCount++;
            } else {
                student.setEstado(false);
                unassignedStudents.add(student);
            }
        }

        lblAssignmentSummary.setText("Asignación completada. Se asignaron " + assignedCount + " de " + availableSlots + " cupos.");
        lblAssignmentSummary.setStyle("-fx-text-fill: green;");

        tblAssignedStudents.setItems(assignedStudents);
        tblUnassignedStudents.setItems(unassignedStudents);
    }

    // TODO --- Mock Data (Reemplaza con la carga real de tus estudiantes) ---
    private void loadInitialDataMock() {
        allStudents.add(new Estudiante(101, "Ana García", 30, false));
        allStudents.add(new Estudiante(102, "Luis Martínez", 25, false));
        allStudents.add(new Estudiante(103, "Sofía Ramírez", 40, false));
        allStudents.add(new Estudiante(104, "Pedro Gómez", 20, false));
        allStudents.add(new Estudiante(105, "Laura Fernández", 35, false));
    }

    // TODO para actualizar las tablas (util si la lista allStudents cambia)
    private void updateTables() {
        assignedStudents.clear();
        unassignedStudents.clear();
        for (Estudiante student : allStudents) {
            if (student.getEstado()) {
                assignedStudents.add(student);
            } else {
                unassignedStudents.add(student);
            }
        }
        tblAssignedStudents.setItems(assignedStudents);
        tblUnassignedStudents.setItems(unassignedStudents);
    }
}