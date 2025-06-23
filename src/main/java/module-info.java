module com.proyecto.estructuras {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.proyecto.estructuras to javafx.fxml;
    exports com.proyecto.estructuras;
}