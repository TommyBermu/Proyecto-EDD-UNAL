package com.proyecto.estructuras;

import com.proyecto.estructuras.models.Estudiante;
import com.proyecto.estructuras.models.StudentDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    public Button btnAllStudents;
    @FXML
    public Button btnAssignedStudents;
    @FXML
    public Button btnUnassignedStudents;

    private final ObservableList<Estudiante> studentData = FXCollections.observableArrayList();

    private final StudentDataManager dataManager = StudentDataManager.getInstance();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colScore.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPuntaje()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResidencia() ? "Asignada" : "Sin asignar"));
        tblStudents.setItems(studentData);

        handleShowAllStudents();
    }

    @FXML
    private void handleShowAllStudents() {
        List<Estudiante> all = dataManager.getAllEstudiantes();
        all.sort(Comparator.comparingDouble(Estudiante::getPuntaje));
        studentData.setAll(all);
    }

    @FXML
    private void handleShowAssignedStudents() {
        List<Estudiante> assigned = dataManager.getAllEstudiantes().stream()
                .filter(Estudiante::getResidencia)
                .collect(Collectors.toList());
        studentData.setAll(assigned);
    }

    @FXML
    private void handleShowUnassignedStudents() {
        List<Estudiante> unassigned = dataManager.getAllEstudiantes().stream()
                .filter(s -> !s.getResidencia())
                .collect(Collectors.toList());
        studentData.setAll(unassigned);
    }
}