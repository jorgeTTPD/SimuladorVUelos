import java.util.ArrayList;
import java.util.List;

public class NodoAeropuerto {
    private String nombre;
    private List<Viaje> viajesSalida;
    private EstadoDijkstra estado;

    public NodoAeropuerto(String nombre) {
        this.nombre = nombre;
        this.viajesSalida = new ArrayList<>();
        reiniciarEstado(); 
    }

    public void reiniciarEstado() {
        this.estado = new EstadoDijkstra();
    }

    
    public String getNombre() { return nombre; }
    public List<Viaje> getViajesSalida() { return viajesSalida; }
    public void agregarViaje(Viaje v) { this.viajesSalida.add(v); }
    
    
    public double getPesoAcumulado() { 
        return this.estado.getPesoAcumulado(); 
    }
    public void setPesoAcumulado(double p) { 
        this.estado.setPesoAcumulado(p); 
    }
    
    public NodoAeropuerto getNodoAnterior() { 
        return this.estado.getNodoAnterior(); 
    }
    public void setNodoAnterior(NodoAeropuerto n) { 
        this.estado.setNodoAnterior(n); 
    }
    
    public boolean isVisitado() { 
        return this.estado.isVisitado(); 
    }
    public void setVisitado(boolean v) { 
        this.estado.setVisitado(v); 
    }
       public Viaje getViajeAnterior() { 
    return this.estado.getViajeAnterior(); 
    }

    public void setViajeAnterior(Viaje v) { 
        this.estado.setViajeAnterior(v); 
        }

    public EstadoDijkstra getEstado() { return estado; }
}