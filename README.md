# Asignación de Residencias Universitarias de la Universidad Nacional

## Descripción del Proyecto
[cite_start]Este proyecto aborda la problemática de la asignación eficiente, justa y priorizada de residencias universitarias en la Universidad Nacional de Colombia[cite: 20, 21]. [cite_start]Dada la alta demanda, el sistema busca garantizar una distribución equitativa de cupos, priorizando a los estudiantes con menor puntaje socioeconómico (PBM), lo que indica una mayor necesidad de alojamiento[cite: 22, 34]. [cite_start]La solución permite registrar, consultar y modificar la información de los estudiantes solicitantes, asegurando un proceso transparente y eficiente[cite: 23].

## MVP Implementado
[cite_start]El sistema implementa una solución que organiza a los estudiantes por su puntaje socioeconómico, donde un puntaje más bajo significa mayor prioridad[cite: 33, 34]. [cite_start]Para ello, se utiliza una estructura híbrida `AVLHashMap` que combina las ventajas de las tablas hash y los árboles AVL[cite: 42].

### Funcionalidades Principales:
* **Registrar un nuevo estudiante:**
    * [cite_start]Ingreso de ID (numérico) que se usa como clave en la tabla hash[cite: 44, 45].
    * [cite_start]Ingreso de nombre completo[cite: 46].
    * [cite_start]Ingreso de puntaje socioeconómico (PBM) que se usa para ordenar a los estudiantes en el árbol AVL[cite: 47, 48].
* **Consultar estudiante:**
    * [cite_start]Búsqueda rápida por ID en tiempo constante $O(1)$ gracias a la tabla hash[cite: 50, 51].
    * [cite_start]Modificación o eliminación del puntaje socioeconómico[cite: 52].
* **Asignar cupos:**
    * [cite_start]Ingreso del número de cupos disponibles[cite: 55].
    * [cite_start]Proceso automático de asignación, priorizando a los estudiantes con los puntajes socioeconómicos más bajos[cite: 56]. [cite_start]El árbol AVL permite extraer al estudiante con el puntaje más bajo de manera eficiente en $O(log~n)$[cite: 57].
* **Listados:**
    * [cite_start]Se presentan tres opciones de listados[cite: 59].
    * [cite_start]Listado por puntaje socioeconómico en orden ascendente[cite: 60].
    * [cite_start]Listado de estudiantes con residencia asignada[cite: 61].
    * [cite_start]Listado de estudiantes a quienes no se ha asignado una residencia[cite: 62].

## Diseño e Implementación

### Diagrama General del Flujo del Sistema
![Diagrama del flujo del sistema](https://i.imgur.com/your-image-url.png)
*(Reemplaza la URL de la imagen con la de tu diagrama de flujo)*

### Clases Principales
* [cite_start]**`AVLHashMap`**: Combina las estructuras `HashMap` y `AVL` para gestionar pares clave-valor[cite: 89]. [cite_start]Cada elemento está representado por un nodo que almacena la clave (`key`), el valor (`data`), y referencias para el árbol (`left`, `right`, `parent`, `height`) y la tabla hash (`next`)[cite: 90, 91, 92, 93, 94, 96, 97].
* [cite_start]**`Estudiante`**: Representación de un estudiante con atributos como `id`, `nombre`, `puntaje`, y `residencia`[cite: 109]. [cite_start]Implementa la interfaz `Comparable<Estudiante>` para ser comparada con otros estudiantes según su puntaje[cite: 110].
* [cite_start]**`StudentDataManager`**: Gestiona a los estudiantes registrados y la lógica de cupos[cite: 111]. [cite_start]Utiliza un patrón Singleton para garantizar una única instancia[cite: 113].
* [cite_start]**Controladores**: (`assignController`, `consultController`, `listingController`, `mainController`, `registerController`) Su finalidad es permitir que la interfaz gráfica interprete y gestione las acciones del usuario, enlazando las operaciones con la lógica del sistema[cite: 114].

## Entorno de Desarrollo y Operación
* [cite_start]**Lenguaje de programación:** Java 21 [cite: 26]
* [cite_start]**Control de versiones:** Github [cite: 27]
* [cite_start]**Entorno de desarrollo:** IntelliJ IDEA Ultimate [cite: 28]
* [cite_start]**Interfaz gráfica de usuario (UI):** JavaFX [cite: 29]
* [cite_start]**Gestión de dependencias:** Maven/Gradle [cite: 30]
* [cite_start]**Librería para serialización/deserialización JSON:** Jackson [cite: 31]

## Análisis de Rendimiento y Complejidad
[cite_start]Para la medición de los tiempos de ejecución se creó una clase llamada `AVLHashMapBenchmark`, que generaba datos para probar varios de los métodos principales de la clase `AVLHashMap`, calculando el tiempo para $n$ datos (con $n=10^{3}, 10^{4}, 10^{5}, 10^{6}, 10^{7}$)[cite: 119, 120, 121].

### Tiempos de Ejecución (en milisegundos)
| Método | $n=1.000$ | $n=10.000$ | $n=100.000$ | $n=1.000.000$ | $n=10.000.000$ |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `insert` | 4,06 ms | 9,32 ms | 73,6 ms | 843,49 ms | 7255,99 ms |
| `removeFromHashMap` | 0,01 ms | 0,01 ms | 0,05 ms | 0,39 ms | 2,14 ms |
| `removeFromAVL` | 1,06 ms | 1,69 ms | 0,56 ms | 0,51 ms | 0,5 ms |
| `getAllValues` | 0,31 ms | 0,53 ms | 2,56 ms | 18,92 ms | 198,69 ms |
| `getNode` | 0,01 ms | 0,05 ms | 0,09 ms | 0,5 ms | 4,43 ms |
| `replace` | 0,17 ms | 0,09 ms | 0,33 ms | 3,12 ms | 15,67 ms |
[cite_start]*Tabla 1. Comparación de tiempos obtenidos para cada método*[cite: 133].

### Comparación de Complejidad Teórica y Empírica
| Método | Complejidad Teórica | Observación Empírica |
| :--- | :--- | :--- |
| `insert` | $O(log~n)$ por inserción, pero en general es de $O(n \cdot log~n)$ acumulado | [cite_start]Crece más rápido que lineal y muy cercano a $n \cdot log~n$[cite: 156]. |
| `removeFromHashMap` | $O(1)$ en promedio, pero $O(n)$ en el peor caso | [cite_start]Comportamiento plano en `log-log` pero constante en promedio[cite: 156]. |
| `removeFromAVL` | $O(log~n)$ | [cite_start]El tiempo por llamada es estable con $(log_{2}n)$[cite: 156]. |
| `getAllValues` | $O(n)$ | [cite_start]El tiempo crece de forma aproximadamente lineal[cite: 156]. |
| `getNode` | $O(1)$ en promedio, pero $O(n)$ en el peor caso | [cite_start]Se mantiene en fracciones de ms y sigue casi constante en escala[cite: 156]. |
| `replace` | $O(log~n)$ | [cite_start]El crecimiento demuestra una complejidad de $O(log_{2}n)$[cite: 156]. |
[cite_start]*Tabla 2. Comparación de las complejidades teóricas con las obtenidas empíricamente*[cite: 157].

## Conclusiones
[cite_start]Las mediciones confirmaron de manera consistente los comportamientos asintóticos esperados[cite: 258]. [cite_start]Las operaciones basadas en AVL (`insert`, `removeFromAVL`, `replace`) mostraron un comportamiento similar a $O(log~n)$[cite: 259]. [cite_start]Las búsquedas y eliminaciones en la tabla hash (`getNode`, `removeFromHashMap`) se mantuvieron en un rango constante promedio[cite: 260]. [cite_start]Las operaciones lineales (`getAllValues`) crecieron proporcionalmente a n[cite: 261].

## Integrantes y Roles
| Integrante | Rol |
| :--- | :--- |
| Maria Catalina Rodriguez Cardona | [cite_start]Diseñadora de UI e implementación con las estructuras[cite: 116]. |
| Julian David Velandia Neuta | [cite_start]Diseñador de Estructuras e integración con la interfaz[cite: 116]. |
| David Alejandro Herrera Novoa | [cite_start]Desarrollador de persistencia de datos y estructuras[cite: 116]. |
| Deiber David Gongora Hurtado | [cite_start]Desarrollador de Herramientas de Prueba[cite: 116]. |
| Tomas Alejandro Bermudez Guaqueta | [cite_start]Diseñador de UI y Estructuras[cite: 116]. |

## Referencias Bibliográficas
* Kane, D. (s.f.). Binary Search Trees: AVL Tree Implementation [Diapositivas]. [cite_start]University of California, San Diego. http://bit.ly/algospecialization [cite: 283]
* Levin, M. (s.f.). Hash Tables: Hash Functions [Diapositivas]. [cite_start]Higher School of Economics. https://goo.gl/KAfKJT [cite: 284]
* Miller, M. R. (2020). Hash Tables [Diapositivas]. [cite_start]Carnegie Mellon University. https://www.cs.cmu.edu/~mrmiller/15-121/Slides/25-HashTables.pdf [cite: 285]
* University of Michigan. (1998). AVL Trees. [cite_start]EECS 380: Data Structures and Algorithms. https://www.eecs.umich.edu/courses/eecs380/ALG/AVL.html [cite: 286]
