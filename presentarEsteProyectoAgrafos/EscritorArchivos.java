
import java.io.*;

public class EscritorArchivos {

    private String rutaArchivo;
    private BufferedWriter bw;

    public EscritorArchivos(String rutaArchivo) {
        this.rutaArchivo = System.getProperty("user.dir") + "/" + rutaArchivo;
    }

    public void abrir() {
        try {
            bw = new BufferedWriter(new FileWriter(this.rutaArchivo));
            escribirLinea("-----REPORTE DE ITINERARIOS DE VUELO ---");
            escribirLinea("----------------------------------------");
            escribirLinea("");
        } catch (IOException e) {
            System.out.println("error al abrir salida.txt: " + e.getMessage());
        }
    }

    public void escribirLinea(String linea) {
        try {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("error al escribir: " + e.getMessage());
        }
    }

    public void cerrar() {
        try {
            if (bw != null) {
                escribirLinea("");
                escribirLinea("----- FIN DEL REPORTE --------");
                bw.close();
                System.out.println("Reporte concluido");
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar salida.txt: " + e.getMessage());
        }
    }

    public void escribirVueloCargado(int codigo, String origen, String destino) {
        escribirLinea("Vuelo cargado: " + codigo + " | " + origen + " -> " + destino);
    }

    public void escribirEncabezadoBusqueda(String origen, String destino, String criterio) {
        escribirLinea("");
        escribirLinea("-----BUSQUEDA: " + origen + " -> " + destino + " [" + criterio + "] ----");
    }

    public void escribirResultadoRuta(double pesoTotal, String unidad, String itinerario) {
        escribirLinea("Costo total de la ruta optimizada: " + pesoTotal + " " + unidad + ".");
        escribirLinea("Itinerario de vuelo:");
        escribirLinea(itinerario);
    }

    public void escribirReprogramacion(String origen, String destino, String causa) {
        escribirLinea("");
        escribirLinea("----- REPROGRAMACION: " + origen + " -> " + destino + " ----");
        escribirLinea("Causa: " + causa);
        escribirLinea("Buscando ruta alternativa");
    }

    public void escribirSinRuta() {
        escribirLinea("No se existe una ruta disponible.");
    }
}