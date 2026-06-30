public class OptimizarEscala implements TipoOptimizacion {
    @Override
     public double calcularPeso(Viaje viaje, NodoAeropuerto nodoActual) {
        if (nodoActual.getPesoAcumulado() == 0.0) {
            return 0.0; // primervuelo nosuma escala
        }
        return 1.0; // cada conex intermedia suma 1
    }
}