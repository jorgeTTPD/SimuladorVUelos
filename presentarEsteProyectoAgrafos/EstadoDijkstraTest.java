
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstadoDijkstraTest {

    @Test
    public void estado_valorInicialPesoEsInfinito() {
        EstadoDijkstra estado = new EstadoDijkstra();
        assertEquals(Double.MAX_VALUE, estado.getPesoAcumulado());
    }

    @Test
    public void estado_valorInicialNoVisitado() {
        EstadoDijkstra estado = new EstadoDijkstra();
        assertFalse(estado.isVisitado());
    }

    @Test
    public void estado_valorInicialNodoAnteriorNulo() {
        EstadoDijkstra estado = new EstadoDijkstra();
        assertNull(estado.getNodoAnterior());
    }

    @Test
    public void estado_valorInicialViajeAnteriorNulo() {
        EstadoDijkstra estado = new EstadoDijkstra();
        assertNull(estado.getViajeAnterior());
    }

    @Test
    public void estado_setPesoAcumulado() {
        EstadoDijkstra estado = new EstadoDijkstra();
        estado.setPesoAcumulado(100.0);
        assertEquals(100.0, estado.getPesoAcumulado());
    }

    @Test
    public void estado_setVisitado() {
        EstadoDijkstra estado = new EstadoDijkstra();
        estado.setVisitado(true);
        assertTrue(estado.isVisitado());
    }

    @Test
    public void estado_setNodoAnterior() {
        EstadoDijkstra estado = new EstadoDijkstra();
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        estado.setNodoAnterior(nodo);
        assertEquals("La Paz", estado.getNodoAnterior().getNombre());
    }

    @Test
    public void estado_setViajeAnterior() {
        EstadoDijkstra estado = new EstadoDijkstra();
        Viaje v = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        estado.setViajeAnterior(v);
        assertEquals(101, estado.getViajeAnterior().getCodigoVuelo());
    }
}