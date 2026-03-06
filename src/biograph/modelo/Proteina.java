package biograph.modelo;

import biograph.tda.ListaEnlazada;
import biograph.tda.Nodo;


/**
 * Representa una proteína en la red (Vértice del Grafo).
 */
public class Proteina {
    private String nombre;
    private ListaEnlazada<Arista> adyacentes; // Lista manual de sus conexiones

    public Proteina(String nombre) {
        this.nombre = nombre;
        this.adyacentes = new ListaEnlazada<>();
    }

    /**
     * Agrega una conexión desde esta proteína hacia otra.
     * Si ya existe una arista hacia el mismo destino, actualiza el peso.
     */
    public void agregarInteraccion(Proteina destino, int peso) {
        // Buscar si ya existe una arista hacia destino
        Nodo<Arista> actual = adyacentes.getPrimero();
        while (actual != null) {
            Arista arista = actual.getDato();
            if (arista.getDestino().equals(destino)) {
                // Actualizar peso si ya existe la conexión
                arista.setPeso(peso);
                return;
            }
            actual = actual.getSiguiente();
        }
        // Si no existe, insertar nueva arista
        Arista nuevaArista = new Arista(destino, peso);
        this.adyacentes.insertar(nuevaArista);
    }

    /**
     * Elimina la interacción hacia una proteína destino específica.
     * @param destino Proteína destino de la arista a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean eliminarInteraccion(Proteina destino) {
        Nodo<Arista> actual = adyacentes.getPrimero();
        Nodo<Arista> anterior = null;

        while (actual != null) {
            if (actual.getDato().getDestino().equals(destino)) {
                if (anterior == null) {
                    // Eliminar el primero
                    adyacentes.setPrimero(actual.getSiguiente());
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }
                adyacentes.setTamano(adyacentes.getTamano() - 1);
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;
    }

    /**
     * Obtiene la lista de proteínas con las que esta proteína interactúa.
     * @return Lista enlazada de proteínas destino.
     */
    public ListaEnlazada<Proteina> obtenerProteinasAdyacentes() {
        ListaEnlazada<Proteina> listaProteinas = new ListaEnlazada<>();
        Nodo<Arista> actual = adyacentes.getPrimero();
        while (actual != null) {
            listaProteinas.insertar(actual.getDato().getDestino());
            actual = actual.getSiguiente();
        }
        return listaProteinas;
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public ListaEnlazada<Arista> getAdyacentes() {
        return adyacentes;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Compara proteínas por nombre para evitar duplicados en la red.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Proteina)) return false;
        Proteina otra = (Proteina) obj;
        return nombre.equals(otra.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode();
    }

    @Override
    public String toString() {
        return nombre;
    }

}
