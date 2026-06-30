import java.util.List;

public class IteradorAeropuertos {
    private List<NodoAeropuerto> lista;
    private int posicion;

    public IteradorAeropuertos(List<NodoAeropuerto> lista) {
        this.lista = lista;
        this.posicion = 0; 
    }

    public boolean tieneSiguiente() {
        return this.posicion < this.lista.size();
    }

    public NodoAeropuerto siguiente() {
        if (tieneSiguiente()) {
            NodoAeropuerto nodo = this.lista.get(this.posicion);
            this.posicion++; 
            return nodo;
        }
        return null; 
    }
}