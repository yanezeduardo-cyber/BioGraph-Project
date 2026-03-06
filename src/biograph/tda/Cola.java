package biograph.tda;

/**
 * Implementación simple de una Cola (FIFO) usando ListaEnlazada.
 * Útil para algoritmos como BFS en grafos.
 */
public class Cola<T> {
    private ListaEnlazada<T> lista;

    public Cola() {
        this.lista = new ListaEnlazada<>();
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param dato elemento a encolar.
     */
    public void encolar(T dato) {
        lista.insertar(dato); // Inserta al final
    }

    /**
     * Remueve y devuelve el elemento al frente de la cola.
     * @return elemento desencolado o null si la cola está vacía.
     */
    public T desencolar() {
        if (estaVacia()) return null;
        T dato = lista.getPrimero().getDato();
        lista.eliminar(dato); // Elimina el primero
        return dato;
    }

    /**
     * Verifica si la cola está vacía.
     * @return true si no hay elementos, false en caso contrario.
     */
    public boolean estaVacia() {
        return lista.getTamano() == 0;
    }

    /**
     * Obtiene el tamaño actual de la cola.
     * @return número de elementos en la cola.
     */
    public int getTamano() {
        return lista.getTamano();
    }
}
