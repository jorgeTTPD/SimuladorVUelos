

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class EscritorArchivosTest {

    private EscritorArchivos escritor;
    private String rutaBase;
    private String nombreArchivo;

    @BeforeEach
    public void setUp() {
        rutaBase = System.getProperty("user.dir") + "/";
        nombreArchivo = "test_salida.txt";
        escritor = new EscritorArchivos(nombreArchivo);
    }

    @AfterEach
    public void tearDown() {
        new File(rutaBase + nombreArchivo).delete();
    }

    // ─── Auxiliar para leer el archivo generado ───
    private String leerArchivo() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaBase + nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                sb.append(linea).append("\n");
            }
        }
        return sb.toString();
    }

    @Test
    public void escritor_abrirCreaArchivo() {
        escritor.abrir();
        escritor.cerrar();
        assertTrue(new File(rutaBase + nombreArchivo).exists());
    }

    @Test
    public void escritor_abrirEscribeEncabezado() throws IOException {
        escritor.abrir();
        escritor.cerrar();
        String contenido = leerArchivo();
        assertTrue(contenido.contains("REPORTE DE ITINERARIOS DE VUELO"));
    }

    @Test
    public void escritor_cerrarEscribeFin() throws IOException {
        escritor.abrir();
        escritor.cerrar();
        String contenido = leerArchivo();
        assertTrue(contenido.contains("FIN DEL REPORTE"));
    }

    @Test
    public void escritor_escribirLineaGuardaContenido() throws IOException {
        escritor.abrir();
        escritor.escribirLinea("Linea de prueba");
        escritor.cerrar();
        String contenido = leerArchivo();
        assertTrue(contenido.contains("Linea de prueba"));
    }

    @Test
    public void escritor_escribirVueloCargado() throws IOException {
        escritor.abrir();
        escritor.escribirVueloCargado(101, "LPB - La Paz", "VVI - Santa Cruz");
        escritor.cerrar();
        String contenido = leerArchivo();
        assertTrue(contenido.contains("Vuelo cargado: 101 | LPB - La Paz -> VVI - Santa Cruz"));
    }

    @Test
    public void escritor_escribirEncabezadoBusqueda() throws IOException {
        escritor.abrir();
        escritor.escribirEncabezadoBusqueda("LPB - La Paz", "VVI - Santa Cruz", "COSTO");
        escritor.cerrar();
        String contenido = leerArchivo();
        assertTrue(contenido.contains("BUSQUEDA: LPB - La Paz -> VVI - Santa Cruz [COSTO]"));
    }

    @Test
    public void escritor_escribirResultadoRuta() throws IOException {
        escritor.abrir();
        escritor.escribirResultadoRuta(500.0, "Bs / USD", "LPB - La Paz ➔ VVI - Santa Cruz");
        escritor.cerrar();
        String contenido = leerArchivo();
        assertTrue(contenido.contains("500.0 Bs / USD"));
        assertTrue(contenido.contains("LPB - La Paz ➔ VVI - Santa Cruz"));
    }

    @Test
    public void escritor_escribirReprogramacion() throws IOException {
        escritor.abrir();
        escritor.escribirReprogramacion("LPB - La Paz", "VVI - Santa Cruz", "Interna");
        escritor.cerrar();
        String contenido = leerArchivo();
        assertTrue(contenido.contains("REPROGRAMACION: LPB - La Paz -> VVI - Santa Cruz"));
        assertTrue(contenido.contains("Causa: Interna"));
    }

    @Test
    public void escritor_escribirSinRuta() throws IOException {
        escritor.abrir();
        escritor.escribirSinRuta();
        escritor.cerrar();
        String contenido = leerArchivo();
        assertTrue(contenido.contains("No se encontro una ruta disponible"));
    }

    @Test
    public void escritor_multipleLineas() throws IOException {
        escritor.abrir();
        escritor.escribirLinea("Linea 1");
        escritor.escribirLinea("Linea 2");
        escritor.escribirLinea("Linea 3");
        escritor.cerrar();
        String contenido = leerArchivo();
        assertTrue(contenido.contains("Linea 1"));
        assertTrue(contenido.contains("Linea 2"));
        assertTrue(contenido.contains("Linea 3"));
    }
}