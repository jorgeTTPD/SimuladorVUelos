import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodoAeropuertoTest {

    @Test
    public void nodo_nombreCorrecto() {
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        assertEquals("La Paz", nodo.getNombre());
    }

    @Test
    public void nodo_estadoInicialPesoInfinito() {
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        assertEquals(Double.MAX_VALUE, nodo.getPesoAcumulado());
    }

    @Test
    public void nodo_estadoInicialNoVisitado() {
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        assertFalse(nodo.isVisitado());
    }

    @Test
    public void nodo_estadoInicialSinViajeAnterior() {
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        assertNull(nodo.getViajeAnterior());
    }

    @Test
    public void nodo_agregarViaje() {
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        Viaje v = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        nodo.agregarViaje(v);
        assertEquals(1, nodo.getViajesSalida().size());
    }

    @Test
    public void nodo_reiniciarEstadoLimpiaValores() {
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        nodo.setPesoAcumulado(100.0);
        nodo.setVisitado(true);
        nodo.reiniciarEstado();
        assertEquals(Double.MAX_VALUE, nodo.getPesoAcumulado());
        assertFalse(nodo.isVisitado());
    }

    @Test
    public void nodo_setPesoAcumulado() {
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        nodo.setPesoAcumulado(250.0);
        assertEquals(250.0, nodo.getPesoAcumulado());
    }

    @Test
    public void nodo_setNodoAnterior() {
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        NodoAeropuerto anterior = new NodoAeropuerto("Cochabamba");
        nodo.setNodoAnterior(anterior);
        assertEquals("Cochabamba", nodo.getNodoAnterior().getNombre());
    }
}