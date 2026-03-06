package biograph.tda;

public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public String toString() {
        return dato.toString();
    }

    /**
     * Imprime la cadena de nodos desde este nodo hasta el final.
     * Formato: dato1 -> dato2 -> ... -> null
     */
    public void imprimirCadena() {
        Nodo<T> actual = this;
        while (actual != null) {
            System.out.print(actual.getDato() + " -> ");
            actual = actual.getSiguiente();
        }
        System.out.println("null");
    }

    /**
     * Cuenta cuántos nodos hay desde este nodo hasta el final.
     * @return número de nodos incluyendo este.
     */
    public int contarNodos() {
        int contador = 0;
        Nodo<T> actual = this;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }
}
