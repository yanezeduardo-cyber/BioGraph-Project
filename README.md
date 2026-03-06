# Proyecto 1 – Análisis de Redes de Interacción Proteica

**Materia:** Estructura de Datos  
**Grupo:** Sección 3  

**Integrantes:**
* **William Santaella**
* **Eduardo Yanez**

**Lenguaje:** Java  
**Entorno:** NetBeans 17 / JDK 17  
**Fecha:** 6 de marzo de 2026  


---

## Descripción General
Este proyecto implementa un analizador de redes biológicas, donde cada proteína se representa como un nodo y sus interacciones como aristas. El sistema identifica proteínas críticas (HUBs) y permite encontrar la comunicación más eficiente entre ellas.

La aplicación incluye una interfaz gráfica (Swing + GraphStream) que permite:
* Cargar y visualizar el grafo desde un archivo CSV (`maestro (2).csv`).
* Identificar automáticamente el HUB de la red (proteína con más conexiones).
* Calcular la Ruta más Corta entre dos proteínas (Dijkstra).
* Detectar Clusters de proteínas relacionadas (BFS).

---

## ✅ Requerimientos del Proyecto

| Código | Descripción | Estado |
| :--- | :--- | :---: |
| **A** | **Carga Dinámica**: Uso de `JFileChooser` para cargar el dataset `maestro (2).csv` y alertas de guardado preventivo. | ✅ |
| **B** | **Gestión de la Red**: Capacidad de agregar nuevas proteínas y eliminar nodos. | ✅ |
| **C** | **Persistencia (Repositorio)**: Actualización del archivo CSV en disco tras realizar modificaciones en la memoria. | ✅ |
| **D** | **Visualización Dinámica**: Representación gráfica del interactoma reflejando cambios en tiempo real (altas/bajas). | ✅ |
| **E** | **Detección de Complejos**: Identificación de grupos de proteínas aisladas mediante el algoritmo BFS. | ✅ |
| **F** | **Ruta Metabólica**: Cálculo del camino de menor resistencia entre dos proteínas usando Dijkstra. | ✅ |
| **G** | **Análisis de Centralidad**: Identificación del HUB (proteína con mayor grado) como diana terapéutica primaria. | ✅ |

### 🛠️ Requerimientos Técnicos

| # | Descripción | Estado |
| :--- | :--- | :---: |
| **1** | **Grafo No Dirigido**: Implementación basada en lista de adyacencia para modelar interacciones biológicas. | ✅ |
| **2** | **TDA Propios**: Estructuras `ListaEnlazada` y `Cola` desarrolladas desde cero sin usar `java.util`. | ✅ |
| **3** | **Interfaz Gráfica**: Aplicación 100% basada en Java Swing, sin uso de consola para entrada/salida. | ✅ |
| **4** | **Persistencia de Datos**: Carga y guardado de archivos CSV mediante el componente `JFileChooser`. | ✅ |
| **5** | **Documentación Interna**: Uso de Javadoc en todas las clases y métodos públicos del sistema. | ✅ |
| **6** | **Arquitectura del Sistema**: Diagrama de clases detallado que refleja la abstracción del problema biológico. | ✅ |

---

## 🧠 Algoritmos Implementados
1. **Dijkstra**: Utilizado para la ruta más corta, optimizado para trabajar sobre nuestra `ListaEnlazada` de adyacencias.
2. **BFS (Breadth-First Search)**: Empleado para la detección de clusters, utilizando la estructura `Cola` desarrollada manualmente.

---

## 💻 Instrucciones de Uso

Para garantizar el correcto funcionamiento del sistema BioGraph, siga estos pasos:
1. **Carga de la Red**: 
   * Haga clic en el botón **"Cargar CSV"**.
   * Seleccione el archivo `maestro (2).csv`. El programa procesará automáticamente las proteínas y sus interacciones.
2. **Visualización Dinámica**: 
   * Presione **"Ver Grafo"** para abrir la ventana de GraphStream.
   * Podrá observar la estructura física de la red proteica en tiempo real.
3. **Análisis Bioinformático**:
   * **Identificar HUB**: Calcula y resalta la proteína con mayor grado de centralidad en la red.
   * **Ruta más Corta**: Encuentra el camino mínimo entre dos proteínas seleccionadas usando el algoritmo de Dijkstra.
   * **Buscar Cluster**: Ejecuta una búsqueda por niveles (BFS) para agrupar proteínas con alta conectividad.
4. **Gestión de Datos**:
   * Utilice **"Eliminar Proteína"** para descartar nodos específicos de la red.
   * Presione **"Guardar Cambios"** antes de salir para asegurar que la estructura modificada se mantenga en memoria.
