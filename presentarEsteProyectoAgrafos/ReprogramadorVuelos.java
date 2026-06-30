public class ReprogramadorVuelos {
    private GrafoViajes grafo;
    
    public ReprogramadorVuelos(GrafoViajes grafo) {
        this.grafo = grafo;
    }
    
    public void reprogramar(Viaje vueloCancelado, TipoPenalizacion causa) {
        String origen = vueloCancelado.getOrigen();
        String destino = vueloCancelado.getDestino();
        boolean bloqueado = false;
        
        IteradorAeropuertos it = grafo.crearIterador();
        while (it.tieneSiguiente()) {
            NodoAeropuerto nodo = it.siguiente();
            for (Viaje v : nodo.getViajesSalida()) {
                if (v.getOrigen().equals(origen) && v.getDestino().equals(destino)) {
                    v.setAsientosOcupados(v.getCapacidadTotal());
                    System.out.println("Vuelo " + v.getCodigoVuelo() + " bloqueado por reprogramacion.");
                    bloqueado = true;
                }
            }
        }
        
        if (!bloqueado) {
            System.out.println("No se encontro vuelo directo " + origen + " -> " + destino + " para bloquear.");
        }
        
        String motivo;
        if (causa == TipoPenalizacion.Interna) {
            motivo = "problema interno";
        } else if (causa == TipoPenalizacion.Externa) {
            motivo = "problema externo";
        } else {
            motivo = "sin causa definida";
        }
        
        System.out.println("Causa: " + motivo);
        System.out.println("Buscando ruta alternativa...");
        grafo.buscarRutaOptima(origen, destino, new OptimizarCosto(causa));
    }
}