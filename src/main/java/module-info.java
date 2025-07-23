module com.proyecto.estructuras {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.logging;
    requires org.jetbrains.annotations;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens com.proyecto.estructuras to javafx.fxml;
    opens com.proyecto.estructuras.models to com.fasterxml.jackson.databind;
    exports com.proyecto.estructuras;
    exports com.proyecto.estructuras.models;
}