
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReprogramadorVuelosTest {

    private GrafoViajes crearGrafoConVuelos() {
        GrafoViajes grafo = new GrafoViajes();
        grafo.agregarAeropuerto("La Paz");
        grafo.agregarAeropuerto("Santa Cruz");
        Viaje v1 = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        Viaje v2 = new Viaje("Amaszonas", 202, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            700, 820, 100, 400.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        grafo.conectarViaje(v1);
        grafo.conectarViaje(v2);
        return grafo;
    }

    @Test
    public void reprogramar_porProblemaInterno() {
        GrafoViajes grafo = crearGrafoConVuelos();
        ReprogramadorVuelos rep = new ReprogramadorVuelos(grafo);
        Viaje vCancelado = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        rep.reprogramar(vCancelado, TipoPenalizacion.Interna);
        assertEquals(TipoPenalizacion.Interna, vCancelado.getTipoPenalizacion());
    }

    @Test
    public void reprogramar_porProblemaExterno() {
        GrafoViajes grafo = crearGrafoConVuelos();
        ReprogramadorVuelos rep = new ReprogramadorVuelos(grafo);
        Viaje vCancelado = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        rep.reprogramar(vCancelado, TipoPenalizacion.Externa);
        assertEquals(TipoPenalizacion.Externa, vCancelado.getTipoPenalizacion());
    }

    @Test
    public void reprogramar_liberaAsiento() {
        GrafoViajes grafo = crearGrafoConVuelos();
        ReprogramadorVuelos rep = new ReprogramadorVuelos(grafo);
        Viaje vCancelado = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        vCancelado.ocuparAsiento();
        rep.reprogramar(vCancelado, TipoPenalizacion.Interna);
        assertEquals(0, vCancelado.getAsientosOcupados());
    }

    @Test
    public void reprogramar_sinRutaAlternativa() {
        GrafoViajes grafo = new GrafoViajes();
        grafo.agregarAeropuerto("La Paz");
        grafo.agregarAeropuerto("Santa Cruz");
        ReprogramadorVuelos rep = new ReprogramadorVuelos(grafo);
        Viaje vCancelado = new Viaje("BoA", 101, "La Paz", "Santa Cruz",
            TipoViaje.Directo, TipoDestino.Nacional,
            480, 600, 150, 500.0, TipoPromocion.Ninguna, TipoPenalizacion.Ninguna);
        assertDoesNotThrow(() -> rep.reprogramar(vCancelado, TipoPenalizacion.Externa));
    }
}