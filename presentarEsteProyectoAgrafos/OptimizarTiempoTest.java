import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptimizarTiempoTest {

    private final OptimizarTiempo criterio = new OptimizarTiempo();
    private final NodoAeropuerto nodo = new NodoAeropuerto("La Paz");

    @Test
    public void tiempo_vueloNormal() {
        Viaje v = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(120.0, criterio.calcularPeso(v, nodo), 0.01);
    }

    @Test
    public void tiempo_vueloCorto() {
        Viaje v = new Viaje("BoA", 102, "La Paz", "Cochabamba",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 510, 150, 200.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(30.0, criterio.calcularPeso(v, nodo), 0.01);
    }

    @Test
    public void tiempo_vueloLargo() {
        Viaje v = new Viaje("BoA", 103, "La Paz", "Miami",
            TipoViaje.Directo, TipoDestino.Internacional,
            480, 960, 300, 1500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(480.0, criterio.calcularPeso(v, nodo), 0.01);
    }

    @Test
    public void tiempo_vueloPassaMedianoche() {
        Viaje v = new Viaje("BoA", 104, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            1380, 60, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(120.0, criterio.calcularPeso(v, nodo), 0.01);
    }
}