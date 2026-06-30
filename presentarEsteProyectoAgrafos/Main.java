 

public class Main {
    public static void main(String[] args) {
        EscritorArchivos escritor = new EscritorArchivos("salida.txt");
        escritor.abrir();
        
        GrafoViajes grafo = new GrafoViajes(escritor);
        LectorArchivos lector = new LectorArchivos(grafo, escritor);
        
        lector.cargarAeropuertos("aeropuertos.txt");
        lector.cargarVuelos("vuelos.txt");
        
        // Lanzar interfaz pasando grafo, lector y escritor
        java.awt.EventQueue.invokeLater(() -> {
            new Interfaz(grafo, lector, escritor).setVisible(true);
        });
    }
}
