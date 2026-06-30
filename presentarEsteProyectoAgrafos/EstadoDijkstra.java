public class EstadoDijkstra {
    private double pesoAcumulado;        
    private NodoAeropuerto nodoAnterior; 
    private Viaje viajeAnterior;         
    private boolean visitado;           
    public EstadoDijkstra() {
        this.pesoAcumulado = Double.MAX_VALUE; 
        this.nodoAnterior = null;
        this.viajeAnterior = null; 
        this.visitado = false;
    }  
    public double getPesoAcumulado() { return pesoAcumulado; }
    public void setPesoAcumulado(double pesoAcumulado) { this.pesoAcumulado = pesoAcumulado; }
    public NodoAeropuerto getNodoAnterior() { return nodoAnterior; }
    public void setNodoAnterior(NodoAeropuerto nodoAnterior) { this.nodoAnterior = nodoAnterior; }
    public Viaje getViajeAnterior() { return viajeAnterior; }
    public void setViajeAnterior(Viaje viajeAnterior) { this.viajeAnterior = viajeAnterior; }
    public boolean isVisitado() { return visitado; }
    public void setVisitado(boolean visitado) { this.visitado = visitado; }
}