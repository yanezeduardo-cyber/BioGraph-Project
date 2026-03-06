package biograph.tda;

public class ListaEnlazada<T> {
    private Nodo<T> primero;
    private int tamano;

    public ListaEnlazada() {
        this.primero = null;
        this.tamano = 0;
    }

    // Método para agregar datos al final de la lista
    public void insertar(T nuevoDato) {
        Nodo<T> nuevoNodo = new Nodo<>(nuevoDato);
        if (primero == null) {
            primero = nuevoNodo;
        } else {
            Nodo<T> aux = primero;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevoNodo);
        }
        tamano++;
    }

    // Método para insertar al inicio de la lista
    public void insertarAlInicio(T nuevoDato) {
        Nodo<T> nuevoNodo = new Nodo<>(nuevoDato);
        nuevoNodo.setSiguiente(primero);
        primero = nuevoNodo;
        tamano++;
    }

    // Método para buscar un dato en la lista, devuelve el nodo o null si no se encuentra
    public Nodo<T> buscar(T dato) {
        Nodo<T> actual = primero;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    // Método para eliminar el primer nodo que contenga el dato especificado
    public boolean eliminar(T dato) {
        if (primero == null) return false;

        if (primero.getDato().equals(dato)) {
            primero = primero.getSiguiente();
            tamano--;
            return true;
        }

        Nodo<T> actual = primero;
        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamano--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    // Método para obtener el tamaño de la lista
    public int getTamano() {
        return tamano;
    }

    // Método para obtener el primer nodo
    public Nodo<T> getPrimero() {
        return primero;
    }

    // Método para establecer el primer nodo (necesario para manipulación externa)
    public void setPrimero(Nodo<T> primero) {
        this.primero = primero;
    }

    // Método para establecer el tamaño (necesario para manipulación externa)
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    // Método para imprimir toda la lista
    public void imprimirLista() {
        if (primero == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        Nodo<T> actual = primero;
        while (actual != null) {
            System.out.print(actual.getDato() + " -> ");
            actual = actual.getSiguiente();
        }
        System.out.println("null");
    }

    // Método para vaciar la lista
    public void vaciar() {
        primero = null;
        tamano = 0;
    }
    
      /**
    * Método especializado para el grafo.
    * Busca en la lista de Aristas y elimina aquella cuyo destino sea la proteína 'p'. 
    * Se asume que esta lista enlazada almacena objetos de tipo Arista,
    * y que cada Arista tiene un método getDestino() que retorna la Proteina destino. 
    * @param p La proteína destino cuya conexión (arista) se desea eliminar.
    */
    public void eliminarConexionCon(biograph.modelo.Proteina p) {
        if (primero == null) {
          // La lista está vacía, no hay conexiones que eliminar
           return;
        }

        // Caso 1: La arista a eliminar es la primera de la lista
        if (primero.getDato() instanceof biograph.modelo.Arista) {
            biograph.modelo.Arista primeraArista = (biograph.modelo.Arista) primero.getDato();
            if (primeraArista.getDestino() == p) {
                // Se elimina el primer nodo de la lista
                primero = primero.getSiguiente();
                tamano--;
                return; // Terminamos porque solo hay una conexión por pareja
            }
        }

        // Caso 2: Buscar en el resto de la lista
        Nodo<T> actual = primero;
        while (actual.getSiguiente() != null) {
            T datoSiguiente = actual.getSiguiente().getDato();
            if (datoSiguiente instanceof biograph.modelo.Arista) {
                biograph.modelo.Arista aristaSiguiente = (biograph.modelo.Arista) datoSiguiente;
                if (aristaSiguiente.getDestino() == p) {
                    // Saltamos el nodo siguiente para eliminarlo de la lista
                    actual.setSiguiente(actual.getSiguiente().getSiguiente());
                    tamano--;
                    return; // Terminamos porque solo hay una conexión por pareja
                }
            }
            actual = actual.getSiguiente();
        }
    }

}
