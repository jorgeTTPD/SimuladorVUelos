public class OptimizarTiempo implements TipoOptimizacion {
    @Override
    public double calcularPeso(Viaje viaje, NodoAeropuerto nodoActual) {
        return (double) viaje.getDuracionMinutos();
    }
}