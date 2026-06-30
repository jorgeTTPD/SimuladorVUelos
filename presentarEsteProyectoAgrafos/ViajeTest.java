

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ViajeTest {

    private Viaje crearViajeBase() {
        return new Viaje(
            "BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150,
            500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna
        );
    }

    @Test
    public void duracion_vueloNormal() {
        Viaje v = crearViajeBase();
        assertEquals(120, v.getDuracionMinutos());
    }

    @Test
    public void duracion_vueloQuePasaMedianoche() {
        Viaje v = new Viaje("BoA", 102, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            1380, 60, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(120, v.getDuracionMinutos());
    }

    @Test
    public void duracion_vueloMuyCorto() {
        Viaje v = new Viaje("BoA", 103, "La Paz", "Cochabamba",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 510, 150, 200.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(30, v.getDuracionMinutos());
    }

    @Test
    public void duracion_salidaYLlegadaIguales() {
        Viaje v = new Viaje("BoA", 104, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 480, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(0, v.getDuracionMinutos());
    }

    @Test
    public void asientos_vueloConEspacio() {
        Viaje v = crearViajeBase();
        assertTrue(v.tieneAsientosDisponibles());
    }

    @Test
    public void asientos_vueloLleno() {
        Viaje v = new Viaje("BoA", 105, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 2, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        v.ocuparAsiento();
        v.ocuparAsiento();
        assertFalse(v.tieneAsientosDisponibles());
    }

    @Test
    public void asientos_ocuparReduceDisponibilidad() {
        Viaje v = crearViajeBase();
        v.ocuparAsiento();
        assertEquals(1, v.getAsientosOcupados());
    }

    @Test
    public void asientos_liberarAumentaDisponibilidad() {
        Viaje v = crearViajeBase();
        v.ocuparAsiento();
        v.liberarAsiento();
        assertEquals(0, v.getAsientosOcupados());
    }

    @Test
    public void retraso_sinRetrasoHoraIgual() {
        Viaje v = crearViajeBase();
        assertEquals(480, v.getHoraSalidaReal());
    }

    @Test
    public void retraso_aplicarRetrasoSuma() {
        Viaje v = crearViajeBase();
        v.aplicarRetraso(30);
        assertEquals(510, v.getHoraSalidaReal());
    }

    @Test
    public void retraso_llegadaConRetraso() {
        Viaje v = crearViajeBase();
        v.aplicarRetraso(60);
        assertEquals(660, v.getHoraLlegadaReal());
    }

    @Test
    public void retraso_pasaMedianoche() {
        Viaje v = new Viaje("BoA", 106, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            1400, 1430, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        v.aplicarRetraso(60);
        assertEquals(20, v.getHoraSalidaReal());
    }
}