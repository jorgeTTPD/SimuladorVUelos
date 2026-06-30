
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptimizarEscalaTest {

    private final OptimizarEscala criterio = new OptimizarEscala();

    @Test
    public void escala_primerVueloSinEspera() {
        NodoAeropuerto nodo = new NodoAeropuerto("La Paz");
        nodo.setPesoAcumulado(0.0); // primer vuelo
        Viaje v = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(0.0, criterio.calcularPeso(v, nodo), 0.01);
    }

    @Test
    public void escala_tiempoEsperaPositivo() {
        NodoAeropuerto nodo = new NodoAeropuerto("Cochabamba");
        nodo.setPesoAcumulado(600.0); // llegó a las 600 min
        Viaje v = new Viaje("BoA", 102, "Cochabamba", "Santa Cruz",
            TipoViaje.EscalaDirecta, TipoDestino.Nacional,
            660, 780, 150, 300.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(60.0, criterio.calcularPeso(v, nodo), 0.01); // 660 - 600
    }

    @Test
    public void escala_tiempoEsperaConSaltoDeDia() {
        NodoAeropuerto nodo = new NodoAeropuerto("Cochabamba");
        nodo.setPesoAcumulado(1400.0); // llegó tarde
        Viaje v = new Viaje("BoA", 103, "Cochabamba", "Santa Cruz",
            TipoViaje.EscalaDirecta, TipoDestino.Nacional,
            60, 180, 150, 300.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(100.0, criterio.calcularPeso(v, nodo), 0.01); // 60 - 1400 + 1440
    }

    @Test
    public void escala_sinEsperaVueloInmediato() {
        NodoAeropuerto nodo = new NodoAeropuerto("Cochabamba");
        nodo.setPesoAcumulado(480.0);
        Viaje v = new Viaje("BoA", 104, "Cochabamba", "Santa Cruz",
            TipoViaje.EscalaDirecta, TipoDestino.Nacional,
            480, 600, 150, 300.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(0.0, criterio.calcularPeso(v, nodo), 0.01);
    }
}