import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GrafoViajesTest {

    private GrafoViajes crearGrafoBase() {
        GrafoViajes grafo = new GrafoViajes();
        grafo.agregarAeropuerto("La Paz");
        grafo.agregarAeropuerto("Cochabamba");
        grafo.agregarAeropuerto("Santa Cruz");
        return grafo;
    }

    @Test
    public void grafo_agregarUnAeropuerto() {
        GrafoViajes grafo = new GrafoViajes();
        grafo.agregarAeropuerto("La Paz");
        assertNotNull(grafo.crearIterador());
    }

    @Test
    public void grafo_agregarVariosAeropuertos() {
        GrafoViajes grafo = crearGrafoBase();
        IteradorAeropuertos it = grafo.crearIterador();
        int count = 0;
        while (it.tieneSiguiente()) { it.siguiente(); count++; }
        assertEquals(3, count);
    }

    @Test
    public void grafo_conectarViajeValido() {
        GrafoViajes grafo = crearGrafoBase();
        Viaje v = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertDoesNotThrow(() -> grafo.conectarViaje(v));
    }

    @Test
    public void grafo_conectarViajeOrigenInexistente() {
        GrafoViajes grafo = crearGrafoBase();
        Viaje v = new Viaje("BoA", 102, "Sucre", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertDoesNotThrow(() -> grafo.conectarViaje(v));
    }

    @Test
    public void grafo_rutaDirectaExiste() {
        GrafoViajes grafo = crearGrafoBase();
        Viaje v = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        grafo.conectarViaje(v);
        assertDoesNotThrow(() -> grafo.buscarRutaOptima("La Paz", "Santa Cruz", new OptimizarCosto()));
    }

    @Test
    public void grafo_origenInexistente() {
        GrafoViajes grafo = crearGrafoBase();
        assertDoesNotThrow(() -> grafo.buscarRutaOptima("Tokio", "Santa Cruz", new OptimizarCosto()));
    }
}