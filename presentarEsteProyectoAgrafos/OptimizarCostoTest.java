import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptimizarCostoTest {

    private final OptimizarCosto criterio = new OptimizarCosto();
    private final NodoAeropuerto nodo = new NodoAeropuerto("La Paz");

    @Test
    public void costo_sinPromocionNiPenalizacion() {
        Viaje v = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertEquals(500.0, criterio.calcularPeso(v, nodo), 0.01);
    }

    @Test
    public void costo_conPromocionPorTiempo() {
        Viaje v = new Viaje("BoA", 102, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.PorTiempo, TipoPenalizacion.Ninguna);
        assertEquals(450.0, criterio.calcularPeso(v, nodo), 0.01);
    }

    @Test
    public void costo_conPromocionPorDistancia() {
        Viaje v = new Viaje("BoA", 103, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.PorDistancia, TipoPenalizacion.Ninguna);
        assertEquals(425.0, criterio.calcularPeso(v, nodo), 0.01);
    }

    @Test
    public void costo_conPenalizacionInterna() {
        Viaje v = new Viaje("BoA", 104, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Interna);
        assertEquals(550.0, criterio.calcularPeso(v, nodo), 0.01);
    }

    @Test
    public void costo_conPenalizacionExterna() {
        Viaje v = new Viaje("BoA", 105, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Externa);
        assertEquals(600.0, criterio.calcularPeso(v, nodo), 0.01);
    }
}