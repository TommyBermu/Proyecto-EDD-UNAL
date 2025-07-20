Sistema de Asignación de Residencias Universitarias UNAL
Este proyecto implementa un sistema de consola en Java para gestionar la asignación de residencias universitarias, priorizando a los estudiantes con mayor necesidad socioeconómica. Utiliza estructuras de datos eficientes como HashMap y MinHeap para garantizar un rendimiento óptimo en las operaciones de registro, consulta, asignación y listado.

Características
Registro de Estudiantes: Permite añadir nuevos estudiantes al sistema con un ID único, nombre completo y puntaje socioeconómico (PBM).

Consulta de Estudiantes: Busca estudiantes por su ID, mostrando sus datos y ofreciendo opciones para modificar su puntaje socioeconómico o eliminar su registro.

Asignación de Cupos: Asigna automáticamente cupos de residencia a los estudiantes con los puntajes socioeconómicos más bajos, utilizando una MinHeap para la priorización.

Listados: Genera diferentes tipos de listados de estudiantes:

Todos los estudiantes ordenados por puntaje socioeconómico (ascendente).

Estudiantes con residencia asignada.

Estudiantes sin residencia asignada.

Persistencia de Datos: Guarda y carga automáticamente los datos del sistema en un archivo JSON, asegurando que la información no se pierda entre sesiones.

Estructuras de Datos Utilizadas
El sistema se basa en la combinación de dos estructuras de datos clave:

HashMap (Mapa de Estudiantes):

Se utiliza para almacenar a los estudiantes, usando su ID como clave para un acceso y búsqueda rápidos (O(1) en promedio).

Permite la consulta, modificación y eliminación eficiente de registros de estudiantes.

MinHeap (Cola de Prioridad):

Organiza a los estudiantes según su puntaje socioeconómico (PBM), donde el estudiante con el PBM más bajo tiene la máxima prioridad (es decir, está en la raíz de la heap).

Facilita la extracción eficiente del estudiante con mayor necesidad para la asignación de cupos (O(
logn)).

Se mantiene sincronizada con el HashMap para reflejar los cambios en el puntaje socioeconómico o la eliminación de estudiantes.

Requisitos del Sistema
Java Development Kit (JDK) 8 o superior.

Librería Gson de Google para la serialización y deserialización de JSON.

_________________________________________________________________________________________________________________________________

TENEMOS 1 GRAN PROBLEMA: 
1) Si organizamos el AVL por puntaje y hay puntajes repetidos, entonces para elimiinar por ID debemos buscar en O(m) donde, m es la cantidad de nodos que comparten el mismo puntaje.
2) Se puede implementar esta solución: Como pueden haber muchos estudiantes con el mismo PBM en el avl, podemos dejar el valor de los nodos del avl, como unicos, es decir sin repeticiones, y en cada nodo hacer un arreglo de estudiantes que tengan el mismo PBM. Esto para poder hacer un HashMap con estos elementos de PBM repetido y buscar por ID del estudiante en esta estructura de Hash. De esta manera el Hashmap sería O(1) y el avl O(log n) resultando en O(log n) para la eliminación de un estudiante.
