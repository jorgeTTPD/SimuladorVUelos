import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class LectorArchivosTest {

    private GrafoViajes grafo;
    private EscritorArchivos escritor;
    private LectorArchivos lector;
    private String rutaBase;

    @BeforeEach
    public void setUp() {
        // CORRECCIÓN: Instanciar el escritor primero y pasárselo al nuevo constructor de GrafoViajes
        escritor = new EscritorArchivos("test_salida_lector.txt");
        escritor.abrir();
        grafo = new GrafoViajes(escritor); 
        lector = new LectorArchivos(grafo, escritor);
        rutaBase = System.getProperty("user.dir") + "/";
    }

    @AfterEach
    public void tearDown() {
        escritor.cerrar();
        new File(rutaBase + "test_salida_lector.txt").delete();
    }

    private String crearArchivoTemporal(String nombre, String contenido) throws IOException {
        String ruta = rutaBase + nombre;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            bw.write(contenido);
        }
        return nombre;
    }

    @Test
    public void lector_cargaTresAeropuertos() throws IOException {
        // CORRECCIÓN: Adaptado al formato estricto de 2 columnas (Ciudad,Pais) que tu método ahora procesa
        crearArchivoTemporal("test_aero1.txt",
            "Ciudad,Pais\n" +
            "La Paz,Bolivia\n" +
            "Santa Cruz,Bolivia\n" +
            "Cochabamba,Bolivia\n"
        );
        lector.cargarAeropuertos("test_aero1.txt");
        IteradorAeropuertos it = grafo.crearIterador();
        int count = 0;
        while (it.tieneSiguiente()) { it.siguiente(); count++; }
        assertEquals(3, count);
    }

    @Test
    public void lector_saltaEncabezadoAeropuertos() throws IOException {
        // CORRECCIÓN: Formato corregido a 2 columnas
        crearArchivoTemporal("test_aero2.txt",
            "Ciudad,Pais\n" +
            "La Paz,Bolivia\n"
        );
        lector.cargarAeropuertos("test_aero2.txt");
        IteradorAeropuertos it = grafo.crearIterador();
        int count = 0;
        while (it.tieneSiguiente()) { it.siguiente(); count++; }
        assertEquals(1, count);
    }

    @Test
    public void lector_saltaLineasVacias() throws IOException {
        // CORRECCIÓN: Formato corregido a 2 columnas
        crearArchivoTemporal("test_aero3.txt",
            "Ciudad,Pais\n" +
            "La Paz,Bolivia\n" +
            "\n" +
            "Santa Cruz,Bolivia\n"
        );
        lector.cargarAeropuertos("test_aero3.txt");
        IteradorAeropuertos it = grafo.crearIterador();
        int count = 0;
        while (it.tieneSiguiente()) { it.siguiente(); count++; }
        assertEquals(2, count);
    }

    @Test
    public void lector_lineaInvalidaNoRompe() throws IOException {
        // CORRECCIÓN: Formato corregido a 2 columnas
        crearArchivoTemporal("test_aero4.txt",
            "Ciudad,Pais\n" +
            "LINEA_INVALIDA_DE_UNA_SOLA_COLUMNA\n" +
            "La Paz,Bolivia\n"
        );
        assertDoesNotThrow(() -> lector.cargarAeropuertos("test_aero4.txt"));
    }

    @Test
    public void lector_archivoInexistenteNoRompe() {
        assertDoesNotThrow(() -> lector.cargarAeropuertos("no_existe.txt"));
    }

    @Test
    public void lector_procesaVuelo() throws IOException {
        // CORRECCIÓN: Ciudades adaptadas a las que procesará el lector
        crearArchivoTemporal("test_aero5.txt",
            "Ciudad,Pais\n" +
            "La Paz,Bolivia\n" +
            "Santa Cruz,Bolivia\n"
        );
        lector.cargarAeropuertos("test_aero5.txt");

        // CORRECCIÓN: Los métodos de prueba de vuelos ahora invocan 'cargarVuelos' en lugar del inexistente 'cargarEntrada'
        crearArchivoTemporal("test_vuelos1.txt",
            "Aerolinea,Codigo,Origen,Destino,TipoViaje,TipoDestino,Salida,Llegada,Capacidad,Costo,Promo,Penalizacion\n" +
            "BoA,101,La Paz,Santa Cruz,Directo,Nacional,480,600,150,500.0,Ninguna,Ninguna\n"
        );
        assertDoesNotThrow(() -> lector.cargarVuelos("test_vuelos1.txt"));
    }

    @Test
    public void lector_procesaVueloConPromocion() throws IOException {
        crearArchivoTemporal("test_aero6.txt",
            "Ciudad,Pais\n" +
            "La Paz,Bolivia\n" +
            "Cochabamba,Bolivia\n"
        );
        lector.cargarAeropuertos("test_aero6.txt");

        // CORRECCIÓN: Apunta a 'cargarVuelos' con el formato correspondiente a las cabeceras de aristas
        crearArchivoTemporal("test_vuelos2.txt",
            "Aerolinea,Codigo,Origen,Destino,TipoViaje,TipoDestino,Salida,Llegada,Capacidad,Costo,Promo,Penalizacion\n" +
            "Amaszonas,202,La Paz,Cochabamba,Directo,Nacional,480,570,100,250.0,PorTiempo,Ninguna\n"
        );
        assertDoesNotThrow(() -> lector.cargarVuelos("test_vuelos2.txt"));
    }

    @Test
    public void lector_procesaBusqueda() throws IOException {
        // Las operaciones de persistencia de operaciones de búsqueda escriben directamente en entrada.txt
        assertDoesNotThrow(() -> lector.guardarBusquedaEntrada("La Paz", "Santa Cruz", "COSTO"));
    }

    @Test
    public void lector_procesaReprogramacion() throws IOException {
        // Las operaciones de persistencia de operaciones de reprogramación escriben directamente en entrada.txt
        assertDoesNotThrow(() -> lector.guardarReprogramarEntrada("La Paz", "Santa Cruz", "Interna"));
    }
}