# Sistema de Gestión de Residencias UNAL

## Descripción del Proyecto

Este sistema ayuda a la Universidad Nacional de Colombia (UNAL) a asignar cupos de residencia a estudiantes. La prioridad se basa en el puntaje socioeconómico, donde un menor puntaje indica mayor necesidad. La aplicación cuenta con una interfaz gráfica para el registro, consulta, y gestión de estudiantes y cupos, asegurando que todos los datos persistan en un archivo JSON.

## Funcionalidades Clave

* **Registro y Gestión de Estudiantes**: Altas, bajas y modificaciones de estudiantes, incluyendo su ID, nombre y puntaje socioeconómico.
* **Gestión de Cupos**: Definición y seguimiento de los cupos de residencia disponibles.
* **Consulta por ID**: Acceso rápido a la información de cualquier estudiante.
* **Persistencia de Datos**: Los datos se guardan y cargan automáticamente desde un archivo JSON.

## Estructura de Datos Central

El sistema utiliza una implementación manual de una estructura mixta (AVL + HashMap) bautizada como AVLHashMap para manejar eficientemente los datos de los estudiantes. El ID del estudiante actúa como la clave, permitiendo búsquedas rápidas y el estudiante es el valor, este mismo es usado para organizar el AVL, organizándose por medio del puntaje asociado, manteniendo la información organizada.

## Tecnologías Utilizadas

* **Java 21**: Lenguaje de programación.
* **JavaFX**: Interfaz gráfica de usuario (UI).
* **Jackson**: Serialización y deserialización de datos a JSON.

## Cómo Ejecutar el Proyecto

1.  **Clonar el Repositorio**: `git clone https://github.com/TommyBermu/Proyecto-EDD-UNAL.git`
2.  **Configurar Dependencias**: Asegúrate de que las dependencias de JavaFX y Jackson estén en tu `pom.xml` (Maven) o `build.gradle` (Gradle).
3.  **Configurar `module-info.java`**: Verifica que los paquetes `com.proyecto.estructuras.model` y `com.proyecto.estructuras.data` estén `opens` a `com.fasterxml.jackson.databind`.
4.  **Compilar y Ejecutar**: Desde tu IDE o usando `mvn clean javafx:run` (Maven) / `gradlew run` (Gradle).
