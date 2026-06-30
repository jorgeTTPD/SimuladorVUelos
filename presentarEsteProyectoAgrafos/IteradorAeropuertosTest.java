

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class IteradorAeropuertosTest {

    @Test
    public void iterador_listaVaciaNoPoseeElementos() {
        List<NodoAeropuerto> lista = new ArrayList<>();
        IteradorAeropuertos it = new IteradorAeropuertos(lista);
        assertFalse(it.tieneSiguiente());
    }

    @Test
    public void iterador_listaConUnElemento() {
        List<NodoAeropuerto> lista = new ArrayList<>();
        lista.add(new NodoAeropuerto("La Paz"));
        IteradorAeropuertos it = new IteradorAeropuertos(lista);
        assertTrue(it.tieneSiguiente());
    }

    @Test
    public void iterador_recorreTodosLosElementos() {
        List<NodoAeropuerto> lista = new ArrayList<>();
        lista.add(new NodoAeropuerto("La Paz"));
        lista.add(new NodoAeropuerto("Cochabamba"));
        lista.add(new NodoAeropuerto("Santa Cruz"));
        IteradorAeropuertos it = new IteradorAeropuertos(lista);
        int count = 0;
        while (it.tieneSiguiente()) { it.siguiente(); count++; }
        assertEquals(3, count);
    }

    @Test
    public void iterador_siguienteDevuelveNuloCuandoTermina() {
        List<NodoAeropuerto> lista = new ArrayList<>();
        lista.add(new NodoAeropuerto("La Paz"));
        IteradorAeropuertos it = new IteradorAeropuertos(lista);
        it.siguiente();
        assertNull(it.siguiente());
    }
}