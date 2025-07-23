
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

# Asignación de Residencias Universitarias de la Universidad Nacional

## Descripción del Proyecto

Este proyecto aborda la problemática de la asignación eficiente, justa y priorizada de residencias universitarias en la Universidad Nacional de Colombia. Dada la alta demanda, el sistema busca garantizar una distribución equitativa de cupos, priorizando a los estudiantes con menor puntaje socioeconómico (PBM), lo que indica una mayor necesidad de alojamiento. 

La solución permite registrar, consultar y modificar la información de los estudiantes solicitantes, asegurando un proceso transparente y eficiente.

## MVP Implementado

El sistema implementa una solución que organiza a los estudiantes por su puntaje socioeconómico, donde un puntaje más bajo significa mayor prioridad. Para ello, se utiliza una estructura híbrida llamada `AVLHashMap`, que combina las ventajas de las tablas hash y los árboles AVL.

### Funcionalidades Principales

* **Registrar un nuevo estudiante:**
  * Ingreso de ID (numérico) como clave en la tabla hash.
  * Ingreso de nombre completo.
  * Ingreso de puntaje socioeconómico (PBM) utilizado para ordenar a los estudiantes en el árbol AVL.

* **Consultar estudiante:**
  * Búsqueda rápida por ID en tiempo constante O(1) gracias a la tabla hash.
  * Modificación o eliminación de la información del estudiante.

* **Asignar cupos:**
  * Ingreso del número de cupos disponibles.
  * Proceso automático de asignación, priorizando a los estudiantes con puntajes socioeconómicos más bajos. El árbol AVL permite extraer al estudiante con el puntaje más bajo de manera eficiente en O(log n).

* **Listados disponibles:**
  * Por puntaje socioeconómico en orden ascendente.
  * Estudiantes con residencia asignada.
  * Estudiantes sin residencia asignada.

## Diseño e Implementación

### Diagrama General del Flujo del Sistema

![Diagrama del flujo del sistema](https://github.com/TommyBermu/Proyecto-EDD-UNAL/blob/bb969830507ffe8eccf51a5b205f7d567b9571fd/Diagrama%20de%20flujo.png)  


### Clases Principales

* **`AVLHashMap`**: Combina las estructuras `HashMap` y `AVL` para gestionar pares clave-valor. Cada nodo almacena la clave (`key`), el valor (`data`), y referencias para el árbol (`left`, `right`, `parent`, `height`) y la tabla hash (`next`).
* **`Estudiante`**: Representación de un estudiante con atributos como `id`, `nombre`, `puntaje` y `residencia`. Implementa la interfaz `Comparable<Estudiante>` para permitir su ordenamiento.
* **`StudentDataManager`**: Gestiona a los estudiantes registrados y la lógica de asignación de cupos. Utiliza un patrón Singleton.
* **Controladores**: `assignController`, `consultController`, `listingController`, `mainController`, `registerController`. Gestionan la interacción entre el usuario y la lógica del sistema.

## Entorno de Desarrollo y Operación

* **Lenguaje de programación**: Java 21
* **Control de versiones**: GitHub
* **Entorno de desarrollo**: IntelliJ IDEA Ultimate
* **Interfaz gráfica de usuario (UI)**: JavaFX
* **Gestión de dependencias**: Maven / Gradle
* **Serialización JSON**: Jackson

## Análisis de Rendimiento y Complejidad

Se creó una clase `AVLHashMapBenchmark` para medir el rendimiento de los métodos principales de la estructura, evaluando tiempos de ejecución para diferentes tamaños de entrada (n = 10³ a 10⁷).

### Tiempos de Ejecución (en milisegundos)

| Método                | n = 1.000 | n = 10.000 | n = 100.000 | n = 1.000.000 | n = 10.000.000 |
|-----------------------|-----------|------------|-------------|---------------|----------------|
| `insert`              | 4,06 ms   | 9,32 ms    | 73,6 ms     | 843,49 ms     | 7255,99 ms     |
| `removeFromHashMap`   | 0,01 ms   | 0,01 ms    | 0,05 ms     | 0,39 ms       | 2,14 ms        |
| `removeFromAVL`       | 1,06 ms   | 1,69 ms    | 0,56 ms     | 0,51 ms       | 0,5 ms         |
| `getAllValues`        | 0,31 ms   | 0,53 ms    | 2,56 ms     | 18,92 ms      | 198,69 ms      |
| `getNode`             | 0,01 ms   | 0,05 ms    | 0,09 ms     | 0,5 ms        | 4,43 ms        |
| `replace`             | 0,17 ms   | 0,09 ms    | 0,33 ms     | 3,12 ms       | 15,67 ms       |

### Comparación de Complejidad Teórica y Empírica

| Método               | Complejidad Teórica                 | Observación Empírica                                          |
|----------------------|--------------------------------------|----------------------------------------------------------------|
| `insert`             | O(log n) individual, O(n log n) total | Crecimiento cercano a O(n log n)                             |
| `removeFromHashMap`  | O(1) promedio, O(n) peor caso         | Comportamiento casi constante en promedio                    |
| `removeFromAVL`      | O(log n)                             | Tiempo estable por llamada                                   |
| `getAllValues`       | O(n)                                 | Tiempo de ejecución aproximadamente lineal                   |
| `getNode`            | O(1) promedio, O(n) peor caso         | Comportamiento constante promedio                            |
| `replace`            | O(log n)                             | Crecimiento coherente con O(log n)                           |

## Conclusiones

Los resultados obtenidos confirmaron de forma consistente los comportamientos asintóticos esperados:
- Las operaciones sobre el árbol AVL (`insert`, `removeFromAVL`, `replace`) siguieron un patrón O(log n).
- Las operaciones sobre la tabla hash (`getNode`, `removeFromHashMap`) mostraron tiempos promedio constantes.
- Las operaciones lineales como `getAllValues` crecieron proporcionalmente a n.
- Las gráficas log–log de las curvas facilitaron el análisis de los resultados obtenidos.


## Integrantes y Roles

| Integrante                         | Rol                                         |
|------------------------------------|----------------------------------------------|
| Maria Catalina Rodriguez Cardona  | Diseño de UI e implementación de estructuras |
| Julian David Velandia Neuta       | Diseño de estructuras e integración con UI   |
| David Alejandro Herrera Novoa     | Desarrollo de persistencia de datos          |
| Deiber David Gongora Hurtado      | Desarrollo de herramientas de prueba         |
| Tomas Alejandro Bermudez Guaqueta | Diseño de UI y estructuras                   |

## Referencias Bibliográficas

- Kane, D. Binary Search Trees: AVL Tree Implementation. University of California, San Diego. http://bit.ly/algospecialization  
- Levin, M. Hash Tables: Hash Functions. Higher School of Economics. https://goo.gl/KAfKJT  
- Miller, M. R. (2020). Hash Tables. Carnegie Mellon University. https://www.cs.cmu.edu/~mrmiller/15-121/Slides/25-HashTables.pdf  
- University of Michigan. AVL Trees. EECS 380: Data Structures and Algorithms. https://www.eecs.umich.edu/courses/eecs380/ALG/AVL.html  

