public class OptimizarCosto implements TipoOptimizacion {

    private TipoPenalizacion penalizacionExtra;

    public OptimizarCosto() {
        this.penalizacionExtra = TipoPenalizacion.Ninguna;
    }

    public OptimizarCosto(TipoPenalizacion penalizacionExtra) {
        this.penalizacionExtra = penalizacionExtra;
    }

    @Override
    public double calcularPeso(Viaje viaje, NodoAeropuerto nodoActual) {
    double costo = viaje.getCostoNormal();

    // promociones solo en búsqueda normal
    if (penalizacionExtra == TipoPenalizacion.Ninguna) {
        if (viaje.getTipoPromocion() == TipoPromocion.PorTiempo) {
            costo *= 0.90;
        } else if (viaje.getTipoPromocion() == TipoPromocion.PorDistancia) {
            costo *= 0.85;
        }
    }

    // ajuste por reprogramación
    if (penalizacionExtra == TipoPenalizacion.Interna) {
        costo *= 0.90; // aerolínea compensa
    } else if (penalizacionExtra == TipoPenalizacion.Externa) {
        costo *= 1.20; // pasajero asume
    }

    return costo;
}
}