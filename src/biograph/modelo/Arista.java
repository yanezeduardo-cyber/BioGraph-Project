package biograph.modelo;

/**
 * Representa una conexión (interacción) en la red de proteínas.
 * En teoría de grafos, esto es una Arista Valorada.
 */
public class Arista {
    private Proteina destino; // La proteína con la que se conecta
    private int peso;         // La fuerza de la interacción (del archivo CSV)

    public Arista(Proteina destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    // Getters
    public Proteina getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    // Setter por si necesitas actualizar el peso en el futuro
    public void setPeso(int peso) {
        this.peso = peso;
    }

    // Setter para actualizar el destino si fuera necesario
    public void setDestino(Proteina destino) {
        this.destino = destino;
    }

    /**
     * Compara esta arista con otra para determinar si son iguales.
     * Dos aristas son iguales si apuntan al mismo destino y tienen el mismo peso.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Arista)) return false;
        Arista otra = (Arista) obj;
        return peso == otra.peso && destino.equals(otra.destino);
    }

    /**
     * Genera un código hash basado en destino y peso.
     * Útil para colecciones que usan hashing (HashSet, HashMap).
     */
    @Override
    public int hashCode() {
        int resultado = 17;
        resultado = 31 * resultado + destino.hashCode();
        resultado = 31 * resultado + peso;
        return resultado;
    }

    /**
     * Representación en String de la arista para facilitar la depuración y visualización.
     */
    @Override
    public String toString() {
        return "Arista{destino=" + destino + ", peso=" + peso + "}";
    }
}
