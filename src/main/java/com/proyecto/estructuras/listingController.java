package com.proyecto.estructuras;

import com.proyecto.estructuras.models.Estudiante;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class listingController {

    @FXML
    private TableView<Estudiante> tblStudents;
    @FXML
    private TableColumn<Estudiante, Number> colId;
    @FXML
    private TableColumn<Estudiante, String> colName;
    @FXML
    private TableColumn<Estudiante, Number> colScore;
    @FXML
    private TableColumn<Estudiante, String> colStatus;

    @FXML
    private Button btnAllStudents;
    @FXML
    private Button btnAssignedStudents;
    @FXML
    private Button btnUnassignedStudents;

    private ObservableList<Estudiante> studentData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colScore.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPuntaje()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstado() ? "Asignada" : "Sin asignar"));
        tblStudents.setItems(studentData);

        // TODO Cargar algunos datos de prueba al iniciar
        loadMockData();
        handleShowAllStudents();
    }

    @FXML
    private void handleShowAllStudents() {
        // TODO **Tu lógica para obtener todos los estudiantes de tu estructura de datos** Y ordenarlos por puntaje socioeconómico creciente
        List<Estudiante> all = getStudentsFromSystemMock();
        all.sort(Comparator.comparingDouble(Estudiante::getPuntaje));
        studentData.setAll(all);
    }

    @FXML
    private void handleShowAssignedStudents() {
        // TODO **Tu lógica para obtener solo los estudiantes con residencia asignada**
        List<Estudiante> assigned = getStudentsFromSystemMock().stream()
                .filter(Estudiante::getEstado)
                .collect(Collectors.toList());
        studentData.setAll(assigned);
    }

    @FXML
    private void handleShowUnassignedStudents() {
        // TODO **Tu lógica para obtener solo los estudiantes sin residencia asignada**
        List<Estudiante> unassigned = getStudentsFromSystemMock().stream()
                .filter(s -> !s.getEstado())
                .collect(Collectors.toList());
        studentData.setAll(unassigned);
    }

    // TODO --- Mock Data (Reemplaza con la obtención real de tus estudiantes) ---
    private List<Estudiante> getStudentsFromSystemMock() {
        List<Estudiante> students = new ArrayList<>();
        students.add(new Estudiante(101, "Ana García", 30, true));
        students.add(new Estudiante(102, "Luis Martínez", 25, true));
        students.add(new Estudiante(103, "Sofía Ramírez", 40, false));
        students.add(new Estudiante(104, "Pedro Gómez", 20, true));
        students.add(new Estudiante(105, "Laura Fernández", 35, false));
        students.add(new Estudiante(106, "Carlos Ruiz", 28, false));
        return students;
    }

    private void loadMockData() {
        studentData.addAll(getStudentsFromSystemMock());
    }
}