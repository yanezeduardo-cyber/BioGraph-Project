package biograph.logica;

import biograph.modelo.*; // Para reconocer Proteina y Arista
import biograph.tda.*;    // Para reconocer Nodo, ListaEnlazada y Cola
import java.io.*;         // Para la lectura del CSV
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

/**
 * Representa un grafo no dirigido de proteínas y sus interacciones.
 * Cada proteína es un vértice y las interacciones son aristas con peso.
 */
public class GrafoProteico {
    private final Proteina[] vertices; // Arreglo para almacenar las proteínas (vértices)
    private int cantidad;        // Número actual de proteínas en el grafo

    /**
     * Constructor que inicializa el grafo con una capacidad máxima.
     * @param capacidad número máximo de proteínas que puede contener el grafo.
     */
    public GrafoProteico(int capacidad) {
        this.vertices = new Proteina[capacidad];
        this.cantidad = 0;
    }

    /**
     * Busca una proteína por nombre en el grafo; si no existe, la crea y agrega.
     * @param nombre nombre de la proteína a buscar o crear.
     * @return la proteína encontrada o creada.
     */
    public Proteina buscarOcrear(String nombre) {
        for (int i = 0; i < cantidad; i++) {
            if (vertices[i].getNombre().equals(nombre)) {
                return vertices[i];
            }
        }
        if (cantidad >= vertices.length) {
            throw new IllegalStateException("Capacidad máxima del grafo alcanzada.");
        }
        Proteina nueva = new Proteina(nombre);
        vertices[cantidad++] = nueva;
        return nueva;
    }

    /**
     * Carga las interacciones desde un archivo CSV con formato:
     * Proteina1,Proteina2,Peso
     * @param ruta ruta del archivo CSV.
     */
    public void cargarDesdeCSV(String ruta) {
        String linea;
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length != 3) {
                    System.err.println("Línea inválida (se esperan 3 campos): " + linea);
                    continue;
                }
                String nombre1 = partes[0].trim();
                String nombre2 = partes[1].trim();
                int peso;
                try {
                    peso = Integer.parseInt(partes[2].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Peso inválido en línea: " + linea);
                    continue;
                }

                Proteina p1 = buscarOcrear(nombre1);
                Proteina p2 = buscarOcrear(nombre2);

                // Grafo no dirigido: agregar interacción en ambos sentidos
                p1.agregarInteraccion(p2, peso);
                p2.agregarInteraccion(p1, peso);
            }
        } catch (IOException e) {
            System.err.println("Error leyendo archivo: " + e.getMessage());
        }
    }

    /**
     * Obtiene la proteína con más interacciones (HUB) y su número de conexiones.
     * @return resumen del análisis del HUB.
     */
    public String obtenerAnalisisHub() {
        if (cantidad == 0) return "No hay datos cargados.";

        Proteina hub = vertices[0];
        for (int i = 1; i < cantidad; i++) {
            if (vertices[i].getAdyacentes().getTamano() > hub.getAdyacentes().getTamano()) {
                hub = vertices[i];
            }
        }
        return "ANÁLISIS DE RED:\n" + 
               "La proteína central (HUB) es: " + hub.getNombre() + "\n" +
               "Número de interacciones: " + hub.getAdyacentes().getTamano();
    }

    /**
     * Imprime un resumen de todas las proteínas y su número de interacciones.
     */
    public void imprimirResumen() {
        System.out.println("Resumen de proteínas y sus interacciones:");
        for (int i = 0; i < cantidad; i++) {
            Proteina p = vertices[i];
            System.out.println("- " + p.getNombre() + ": " + p.getAdyacentes().getTamano() + " interacciones");
        }
    }

    /**
     * Obtiene la cantidad actual de proteínas en el grafo.
     * @return número de proteínas.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene la proteína en la posición dada.
     * @param indice índice en el arreglo de proteínas.
     * @return proteína en la posición o null si índice inválido.
     */
    public Proteina getProteina(int indice) {
        if (indice < 0 || indice >= cantidad) return null;
        return vertices[indice];
    }
    
    /**
 * Encuentra todas las proteínas conectadas a una inicial (Cluster).
 * Implementa el algoritmo BFS (Breadth-First Search).
 */
public String obtenerCluster(String nombreInicio) {
    Proteina inicio = null;
    for (int i = 0; i < cantidad; i++) {
        if (vertices[i].getNombre().equals(nombreInicio)) {
            inicio = vertices[i];
            break;
        }
    }

    if (inicio == null) return "Proteína no encontrada.";

    // Usamos nuestra Cola y una lista para marcados
    Cola<Proteina> cola = new Cola<>();
    ListaEnlazada<Proteina> visitados = new ListaEnlazada<>();

    cola.encolar(inicio);
    visitados.insertar(inicio);

    String resultado = "Cluster de " + nombreInicio + ":\n";

    while (!cola.estaVacia()) {
        Proteina actual = cola.desencolar();
        resultado += "- " + actual.getNombre() + "\n";

        Nodo<Arista> it = actual.getAdyacentes().getPrimero();
        while (it != null) {
            Proteina vecino = it.getDato().getDestino();
            if (visitados.buscar(vecino) == null) {
                visitados.insertar(vecino);
                cola.encolar(vecino);
            }
            it = it.getSiguiente();
        }
    }
    return resultado;
   }
        /**
    * Busca una proteína en el arreglo de vértices por su nombre.
    * La búsqueda es insensible a mayúsculas y espacios en blanco al inicio o final.
    * @param nombre El nombre de la proteína a buscar.
    * @return La instancia de Proteina si se encuentra; null si no existe.
    */
    public Proteina buscarProteina(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            // Validar que el nombre no sea nulo ni vacío
            return null;
        }

        String nombreBuscado = nombre.trim().toLowerCase();

        // Recorrer el arreglo de proteínas (vertices)
        for (int i = 0; i < cantidad; i++) {
            Proteina actual = vertices[i];
            if (actual != null) {
                // Comparar nombres ignorando mayúsculas/minúsculas y espacios
                String nombreActual = actual.getNombre();
                if (nombreActual != null && nombreActual.trim().toLowerCase().equals(nombreBuscado)) {
                    return actual; // Proteína encontrada
                }
            }
        }

        // No se encontró ninguna proteína con ese nombre
        return null;
    }
    
    public String obtenerRutaMasCorta(String nombreOrigen, String nombreDestino) {
    // Buscar las proteínas origen y destino por nombre
    Proteina origen = buscarProteina(nombreOrigen);
    Proteina destino = buscarProteina(nombreDestino);

    // Validar que ambas proteínas existan
    if (origen == null || destino == null) {
        return "Una o ambas proteínas no existen.";
    }

    int n = cantidad; // cantidad total de vértices (proteínas)
    int[] distancias = new int[n]; // distancias mínimas desde el origen
    Proteina[] padres = new Proteina[n]; // para reconstruir el camino
    boolean[] visitados = new boolean[n]; // nodos ya procesados

    // Inicializar distancias con infinito y visitados con falso
    for (int i = 0; i < n; i++) {
        distancias[i] = Integer.MAX_VALUE;
        visitados[i] = false;
        padres[i] = null;
    }

    // Buscar índice del nodo origen en el arreglo vertices
    int idxOrigen = -1;
    for (int i = 0; i < n; i++) {
        if (vertices[i] == origen) {
            idxOrigen = i;
            break;
        }
    }

    if (idxOrigen == -1) {
        return "La proteína origen no está en la lista de vértices.";
    }

    distancias[idxOrigen] = 0; // distancia al origen es 0

    // Algoritmo de Dijkstra
    for (int i = 0; i < n - 1; i++) {
        // Encontrar el nodo no visitado con la distancia mínima
        int u = -1;
        int minDist = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if (!visitados[j] && distancias[j] <= minDist) {
                minDist = distancias[j];
                u = j;
            }
        }

        // Si no hay nodo accesible, terminar
        if (u == -1) break;

        visitados[u] = true;

        // Relajar las aristas adyacentes al nodo u
        Nodo<Arista> it = vertices[u].getAdyacentes().getPrimero();
        while (it != null) {
            Proteina v = it.getDato().getDestino();
            int peso = it.getDato().getPeso();

            // Buscar índice del nodo destino v
            int idxV = -1;
            for (int k = 0; k < n; k++) {
                if (vertices[k] == v) {
                    idxV = k;
                    break;
                }
            }

            // Si el nodo destino está en la lista y no está visitado
            if (idxV != -1 && !visitados[idxV] && distancias[u] != Integer.MAX_VALUE) {
                int nuevaDist = distancias[u] + peso;
                if (nuevaDist < distancias[idxV]) {
                    distancias[idxV] = nuevaDist;
                    padres[idxV] = vertices[u];
                }
            }

            it = it.getSiguiente();
        }
    }

    // Buscar índice del destino para reconstruir el camino
    int idxDestino = -1;
    for (int i = 0; i < n; i++) {
        if (vertices[i] == destino) {
            idxDestino = i;
            break;
        }
    }

    if (idxDestino == -1) {
        return "La proteína destino no está en la lista de vértices.";
    }

    // Si la distancia al destino es infinito, no hay camino
    if (distancias[idxDestino] == Integer.MAX_VALUE) {
        return "No existe un camino entre las proteínas indicadas.";
    }

    // Reconstruir y formatear el camino desde origen a destino
    return formatearCamino(padres, vertices, idxOrigen, idxDestino, distancias[idxDestino]);
    }

/**
 * Método para reconstruir el camino desde origen a destino usando el arreglo padres,
 * sin usar librerías externas.
 * @param padres arreglo con el nodo padre de cada vértice en el camino más corto
 * @param vertices arreglo de todos los vértices (proteínas)
 * @param idxOrigen índice del nodo origen
 * @param idxDestino índice del nodo destino
 * @param distanciaTotal distancia total del camino más corto
 * @return String con la ruta formateada y la distancia total
 */
private String formatearCamino(Proteina[] padres, Proteina[] vertices, int idxOrigen, int idxDestino, int distanciaTotal) {
    int n = vertices.length;
    Proteina[] caminoInverso = new Proteina[n];
    int contador = 0;

    int actual = idxDestino;

    // Reconstruir el camino hacia atrás
    while (actual != idxOrigen) {
        caminoInverso[contador] = vertices[actual];
        contador++;

        Proteina padre = padres[actual];
        if (padre == null) {
            return "No se pudo reconstruir el camino.";
        }

        // Buscar índice del padre sin usar librerías
        actual = -1;
        for (int i = 0; i < n; i++) {
            if (vertices[i] == padre) {
                actual = i;
                break;
            }
        }
        if (actual == -1) {
            return "Error al reconstruir el camino.";
        }
    }

    // Agregar el origen al camino
    caminoInverso[contador] = vertices[idxOrigen];
    contador++;

    // Construir la cadena resultado recorriendo el arreglo en orden inverso (de origen a destino)
    String resultado = "Ruta más corta: ";
    for (int i = contador - 1; i >= 0; i--) {
        resultado += caminoInverso[i].getNombre();
        if (i > 0) {
            resultado += " -> ";
        }
    }
    resultado += "\nDistancia total: " + distanciaTotal;

    return resultado;
}
    /**
 * Guarda el estado actual del grafo en el archivo CSV.
 * Recorre todas las proteínas y sus aristas para reconstruir el archivo.
 * Cumple con el Requerimiento C (Actualizar repositorio).
 * 
 * @param ruta La ruta del archivo CSV (puede ser la misma que usaste para cargar).
 */
    public void guardarEnCSV(String ruta) {
        // Usamos FileWriter y PrintWriter para escribir el archivo
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
        
            // Para evitar duplicar aristas (A-B y B-A) en un grafo no dirigido,
            // llevaremos un registro de qué parejas ya escribimos.
            ListaEnlazada<String> parejasEscritas = new ListaEnlazada<>();
        
            for (int i = 0; i < cantidad; i++) {
                Proteina p1 = vertices[i];
                if (p1 == null) continue;

                // Recorrer los adyacentes de la proteína actual
                Nodo<Arista> it = p1.getAdyacentes().getPrimero();
                while (it != null) {
                    Proteina p2 = it.getDato().getDestino();
                    int peso = it.getDato().getPeso();

                    String nombre1 = p1.getNombre();
                    String nombre2 = p2.getNombre();

                    // Creamos un identificador único para la pareja (ej: "P1-P2")
                    // Ordenamos alfabéticamente para que P1-P2 se trate igual que P2-P1
                    String idPareja = (nombre1.compareTo(nombre2) < 0) 
                                    ? nombre1 + "-" + nombre2 
                                    : nombre2 + "-" + nombre1;

                    // Si esta pareja de proteínas no ha sido escrita aún en el archivo
                    if (parejasEscritas.buscar(idPareja) == null) {
                        pw.println(nombre1 + "," + nombre2 + "," + peso);
                        parejasEscritas.insertar(idPareja); // La marcamos como escrita
                    }
                
                    it = it.getSiguiente();
                }
            }
            System.out.println("Repositorio CSV actualizado exitosamente.");
        
        } catch (IOException e) {
            System.err.println("Error al guardar en el archivo CSV: " + e.getMessage());
        }
    }
    
    public void eliminarProteina(String nombre) {
        Proteina p = buscarProteina(nombre);
        if (p == null) return;

        // 1. Quitar conexiones de los demás hacia esta proteína
        for (int i = 0; i < cantidad; i++) {
            vertices[i].getAdyacentes().eliminarConexionCon(p);
        }

        // 2. Quitar del arreglo y compactar (Swap with last)
        for (int i = 0; i < cantidad; i++) {
            if (vertices[i] == p) {
                vertices[i] = vertices[cantidad - 1];
                vertices[cantidad - 1] = null;
                cantidad--;
                break;
            }
        }
    }
    
    /*
     * Busca y devuelve solo el nombre de la proteína con más conexiones.
     */
    public String obtenerNombreHub() {
        if (cantidad == 0) return "";
        
        Proteina hub = vertices[0];
        for (int i = 1; i < cantidad; i++) {
            if (vertices[i].getAdyacentes().getTamano() > hub.getAdyacentes().getTamano()) {
                hub = vertices[i];
            }
        }
        return hub.getNombre();
    }

    public void mostrarRedVisual() {
        // 1. Configurar el motor de renderizado para que use Java Swing
        System.setProperty("org.graphstream.ui", "swing");
    
        // 2. Crear el objeto grafo de la librería
        Graph graph = new SingleGraph("RedProteica");

        // 3. Definir el estilo (CSS) para que se vea "atractivo"
        String css = "node { fill-color: #4472C4; size: 25px; label-size: 14px; text-alignment: at-right; } " +
                    "node.hub { fill-color: #C00000; size: 40px; } " + // El HUB será rojo y grande
                    "edge { fill-color: #A6A6A6; width: 2px; label-size: 12px; }";
        graph.setAttribute("ui.stylesheet", css);

        // 4. Agregar los Nodos (Proteínas) desde la estructura manual
        for (int i = 0; i < cantidad; i++) {
            Proteina p = vertices[i];
            Node n = graph.addNode(p.getNombre());
            n.setAttribute("ui.label", p.getNombre());
        
            // Si esta proteína es el HUB, le ponemos la clase especial
            if (p.getNombre().equals(this.obtenerNombreHub())) {
                n.setAttribute("ui.class", "hub");
            }
        }

        // 5. Agregar las Aristas (Interacciones)
        // Usamos una lista para no repetir líneas entre A-B y B-A
        biograph.tda.ListaEnlazada<String> aristasAgregadas = new biograph.tda.ListaEnlazada<>();

        for (int i = 0; i < cantidad; i++) {
            Proteina p1 = vertices[i];
            biograph.tda.Nodo<Arista> aux = p1.getAdyacentes().getPrimero();
        
            while (aux != null) {
                Proteina p2 = aux.getDato().getDestino();
                String id = (p1.getNombre().compareTo(p2.getNombre()) < 0) 
                            ? p1.getNombre() + p2.getNombre() : p2.getNombre() + p1.getNombre();

                if (aristasAgregadas.buscar(id) == null) {
                    Edge e = graph.addEdge(id, p1.getNombre(), p2.getNombre());
                    e.setAttribute("ui.label", aux.getDato().getPeso()); // Muestra el peso en la línea
                    aristasAgregadas.insertar(id);
                }
                aux = aux.getSiguiente();
            }
        }

        // 6. Mostrar la ventana con el grafo interactivo
        graph.display();
    }
}


