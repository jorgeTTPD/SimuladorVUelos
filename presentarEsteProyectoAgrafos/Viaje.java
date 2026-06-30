public class Viaje {
    private String aerolinea;
    private int codigoVuelo;
    private String origen;
    private String destino;
    private int retrasoMinutos; 
    private TipoViaje tipoViaje;
    private TipoDestino tipoDestino; 
    private int horaSalida;
    private int horaLlegada;
    private int capacidadTotal;
    private int asientosOcupados;
    private double costoNormal;
    private TipoPromocion tipoPromocion;
    private TipoPenalizacion tipoPenalizacion;

    public Viaje(String aerolinea, int codigoVuelo, String origen, String destino, 
                 TipoViaje tipoViaje, TipoDestino tipoDestino, 
                 int horaSalida, int horaLlegada, int capacidadTotal,
                 double costoNormal, TipoPromocion tipoPromocion, TipoPenalizacion tipoPenalizacion) {
        this.aerolinea = aerolinea;
        this.codigoVuelo = codigoVuelo;
        this.origen = origen;
        this.destino = destino;
        this.tipoViaje = tipoViaje;
        this.tipoDestino = tipoDestino; 
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.capacidadTotal = capacidadTotal;
        this.asientosOcupados = 0; 
        this.retrasoMinutos = 0;
        this.costoNormal = costoNormal;
        this.tipoPromocion = tipoPromocion;
        this.tipoPenalizacion = tipoPenalizacion;
    }

    public int getDuracionMinutos() {
        int duracion = this.horaLlegada - this.horaSalida;
        if (duracion < 0) {
            duracion += 1440; 
        }
        return duracion;
    }
    
    
        public boolean tieneAsientosDisponibles() {
        return this.asientosOcupados < this.capacidadTotal;
    }
    
    public boolean ocuparAsiento() {
        if (tieneAsientosDisponibles()) {
            this.asientosOcupados++;
            return true;
        }
        return false;
    }
    
    public void liberarAsiento() {
        if (this.asientosOcupados > 0) {
            this.asientosOcupados--;
        }
    }
    public int getRetrasoMinutos() { return retrasoMinutos; }

    public void aplicarRetraso(int minutos) {
        this.retrasoMinutos = minutos;
    }
    
    public int getHoraSalidaReal() {
        return (this.horaSalida + this.retrasoMinutos) % 1440;
    }
    
    public int getHoraLlegadaReal() {
        return (this.horaLlegada + this.retrasoMinutos) % 1440;
    }
    public String getAerolinea() { return aerolinea; }
    public int getCodigoVuelo() { return codigoVuelo; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public TipoViaje getTipoViaje() { return tipoViaje; }
    public TipoDestino getTipoDestino() { return tipoDestino; } 
    public int getHoraSalida() { return horaSalida; }
    public int getHoraLlegada() { return horaLlegada; }
    public int getCapacidadTotal() { return capacidadTotal; }
    public int getAsientosOcupados() { return asientosOcupados; }
    public void setAsientosOcupados(int asientos) { this.asientosOcupados = asientos; }
    public double getCostoNormal() { return costoNormal; }
    public TipoPromocion getTipoPromocion() { return tipoPromocion; }
    public TipoPenalizacion getTipoPenalizacion() { return tipoPenalizacion; }
    public void setTipoPenalizacion(TipoPenalizacion tipoPenalizacion) { this.tipoPenalizacion = tipoPenalizacion; }
}