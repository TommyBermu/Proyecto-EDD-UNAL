package com.proyecto.estructuras.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this must be a Singleton class
 */
public class StudentDataManager {
    private static StudentDataManager instance;

    private static final String FILE_PATH = "students_data.json";

    private AVLHashMap<Integer, Estudiante> studentMap;
    private int cuposDisponibles;

    private StudentDataManager() {
        this.studentMap = new AVLHashMap<>();
        this.cuposDisponibles = 0;
        loadDataFromFile();
    }

    public static StudentDataManager getInstance() {
        return instance == null ? instance = new StudentDataManager() : instance;
    }

    public AVLHashMap<Integer, Estudiante> getStudentMap() {
        return studentMap;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
        saveDataToFile();
    }

    public void addEstudiante(Estudiante estudiante) {
        if (estudiante != null && studentMap.insert(estudiante.getId(), estudiante)) {
            saveDataToFile();
        } else {
            assert estudiante != null;
            System.err.println("Error: El estudiante con ID " + estudiante.getId() + " ya existe o es nulo.");
        }
    }

    public void removeEstudiante(Integer id) {
        if (studentMap.containsKey(id)) {
            studentMap.remove(id);
            saveDataToFile();
        } else {
            System.err.println("Error: Estudiante con ID " + id + " no encontrado para eliminar.");
        }
    }

    public void assignEstudiante(Integer id, boolean residencia) {
        studentMap.get(id).setResidencia(residencia);
        saveDataToFile();
    }

    public Estudiante getEstudiante(Integer id) {
        return studentMap.get(id);
    }

    public void updateEstudiante(Integer id, Estudiante updatedStudent) {
        if (studentMap.containsKey(id)) {
            studentMap.replace(id, updatedStudent);
            saveDataToFile();
        } else {
            System.err.println("Error: Estudiante con ID " + id + " no encontrado para actualizar.");
        }
    }

    public List<Estudiante> getAllEstudiantes() {
        return studentMap.getAllValues();
    }

    /**
     * Clase auxiliar para envolver los datos que queremos persistir en el JSON.
     * Esta clase es lo que Jackson serializará/deserializer.
     */
    private static class PersistentDataWrapper {
        public List<Estudiante> estudiantes;
        public int cuposDisponibles;

        // Constructor vacío es necesario para Jackson
        public PersistentDataWrapper() {
            this.estudiantes = new ArrayList<>();
        }

        public PersistentDataWrapper(List<Estudiante> estudiantes, int cuposDisponibles) {
            this.estudiantes = estudiantes;
            this.cuposDisponibles = cuposDisponibles;
        }

        // Getters y Setters para Jackson
        public List<Estudiante> getEstudiantes() { return estudiantes; }
        public void setEstudiantes(List<Estudiante> estudiantes) { this.estudiantes = estudiantes; }
        public int getCuposDisponibles() { return cuposDisponibles; }
        public void setCuposDisponibles(int cuposDisponibles) { this.cuposDisponibles = cuposDisponibles; }
    }


    private void loadDataFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(FILE_PATH);

        if (file.exists() && file.length() > 0) {
            try {
                PersistentDataWrapper loadedData = mapper.readValue(file, PersistentDataWrapper.class);

                this.studentMap.clear();
                this.cuposDisponibles = loadedData.getCuposDisponibles();

                for (Estudiante estudiante : loadedData.getEstudiantes()) {
                    this.studentMap.insert(estudiante.getId(), estudiante);
                }
                System.out.println("Datos cargados desde " + FILE_PATH);
            } catch (IOException e) {
                System.err.println("Error al cargar datos desde " + FILE_PATH + ": " + e.getMessage());
                this.studentMap = new AVLHashMap<>();
                this.cuposDisponibles = 0;
            }
        } else {
            System.err.println("Archivo de datos no encontrado o vacío. Se iniciará con datos vacíos.");
            this.studentMap = new AVLHashMap<>();
            this.cuposDisponibles = 0;
        }
    }

    public void saveDataToFile() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        File file = new File(FILE_PATH);

        try {
            PersistentDataWrapper dataToSave = new PersistentDataWrapper(studentMap.getAllValues(), this.cuposDisponibles);
            mapper.writeValue(file, dataToSave);
            System.out.println("Datos guardados en " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error al guardar datos en " + FILE_PATH + ": " + e.getMessage());
        }
    }
}
