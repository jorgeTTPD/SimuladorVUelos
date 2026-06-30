import java.util.ArrayList;
import java.util.List;

public class GrafoViajes {

    private List<NodoAeropuerto> aeropuertos;
    private EscritorArchivos escritor;
    private String ultimoResultado = "";

    
    public GrafoViajes(EscritorArchivos escritor) {
        this.aeropuertos = new ArrayList<>();
        this.escritor = escritor;
    }

    
    public GrafoViajes() {
        this.aeropuertos = new ArrayList<>();
        this.escritor = null;
    }

    public String getUltimoResultado() {
        return ultimoResultado;
    }

    public void agregarAeropuerto(String nombre) {
        this.aeropuertos.add(new NodoAeropuerto(nombre));
    }

    public void conectarViaje(Viaje viaje) {
        for (NodoAeropuerto nodo : this.aeropuertos) {
            if (nodo.getNombre().equals(viaje.getOrigen())) {
                nodo.agregarViaje(viaje);
                return;
            }
        }
        System.out.println("Aeropuerto origen no encontrado: " + viaje.getOrigen());
    }

    public IteradorAeropuertos crearIterador() {
        return new IteradorAeropuertos(this.aeropuertos);
    }

    public void buscarRutaOptima(String origen, String destino, TipoOptimizacion criterio) {

        //reiniciar - nodos
        IteradorAeropuertos it = crearIterador();
        while (it.tieneSiguiente()) {
            it.siguiente().reiniciarEstado();
        }

        // buscar nodo origen
        NodoAeropuerto nodoOrigen = null;
        for (NodoAeropuerto nodo : aeropuertos) {
            if (nodo.getNombre().equals(origen)) {
                nodoOrigen = nodo;
                break;
            }
        }

        if (nodoOrigen == null) {
            System.out.println("Aeropuerto de origen no existe: " + origen);
            ultimoResultado = "Aeropuerto de origen no existe: " + origen + "\n";
            if (escritor != null) escritor.escribirSinRuta();
            return;
        }

        nodoOrigen.setPesoAcumulado(0.0);

        // Dijkstra
        while (true) {
            NodoAeropuerto actual = null;
            double menorPeso = Double.MAX_VALUE;

            for (NodoAeropuerto nodo : aeropuertos) {
                if (!nodo.isVisitado() && nodo.getPesoAcumulado() < menorPeso) {
                    menorPeso = nodo.getPesoAcumulado();
                    actual = nodo;
                }
            }

            if (actual == null || actual.getNombre().equals(destino)) break;

            actual.setVisitado(true);

            for (Viaje viaje : actual.getViajesSalida()) {
                NodoAeropuerto vecino = buscarNodoPorNombre(viaje.getDestino());
                if (vecino == null || vecino.isVisitado()) continue;

                if (!viaje.tieneAsientosDisponibles()) {
                    System.out.println("Vuelo " + viaje.getCodigoVuelo() + " descartado: sin asientos.");
                    continue;
                }

                double pesoViaje    = criterio.calcularPeso(viaje, actual);
                double pesoTotalRuta = actual.getPesoAcumulado() + pesoViaje;

                if (pesoTotalRuta < vecino.getPesoAcumulado()) {
                    vecino.setPesoAcumulado(pesoTotalRuta);
                    vecino.setNodoAnterior(actual);
                    vecino.setViajeAnterior(viaje);
                }
            }
        }

        imprimirRuta(destino, criterio);
    }

    private NodoAeropuerto buscarNodoPorNombre(String nombre) {
        for (NodoAeropuerto n : aeropuertos) {
            if (n.getNombre().equals(nombre)) return n;
        }
        return null;
    }

           private void imprimirRuta(String destino, TipoOptimizacion criterio) {
        NodoAeropuerto destNodo = buscarNodoPorNombre(destino);
    
        if (destNodo == null || destNodo.getPesoAcumulado() == Double.MAX_VALUE) {
            System.out.println("No existe una ruta disponible.");
            ultimoResultado = "No existe una ruta disponible.\n";
            if (escritor != null) escritor.escribirSinRuta();
            return;
        }
    
        String etiquetaUnidad = "minutos";
        if (criterio instanceof OptimizarCosto)  etiquetaUnidad = "USD";
        if (criterio instanceof OptimizarEscala) etiquetaUnidad = "escalas";
    
        // recosntrircamino
        List<NodoAeropuerto> caminoInvertido = new ArrayList<>();
        NodoAeropuerto actual = destNodo;
        while (actual != null) {
            caminoInvertido.add(actual);
            actual = actual.getNodoAnterior();
        }
        List<NodoAeropuerto> camino = invertirLista(caminoInvertido);
    
        // itinerario
        StringBuilder itinerario = new StringBuilder();
        for (int i = 0; i < camino.size(); i++) {
            itinerario.append(camino.get(i).getNombre());
            if (i < camino.size() - 1) itinerario.append(" -> ");
        }
    
        // Construccionj resiltado
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------\n");
        sb.append("Ruta: ").append(itinerario).append("\n");
        sb.append("Costo total: ")
          .append(String.format("%.2f", destNodo.getPesoAcumulado()))
          .append(" ").append(etiquetaUnidad).append("\n");
        sb.append("Vuelos:\n");
    
        for (int i = 0; i < camino.size() - 1; i++) {
            Viaje v = camino.get(i + 1).getViajeAnterior();
            if (v != null) {
                // descuento
                String descuento = "";
                if (v.getTipoPromocion() == TipoPromocion.PorTiempo) {
                    descuento = " [Descuento 10% PorTiempo]";
                } else if (v.getTipoPromocion() == TipoPromocion.PorDistancia) {
                    descuento = " [Descuento 15% PorDistancia]";
                } else {
                    descuento = " [Sin descuento]";
                }
    
                sb.append(String.format(
                    "  Vuelo %d | %s -> %s | Salida: %d | Llegada: %d | %.2f USD%s\n",
                    v.getCodigoVuelo(), v.getOrigen(), v.getDestino(),
                    v.getHoraSalida(), v.getHoraLlegada(),
                    v.getCostoNormal(), descuento
                ));
            }
        }
        sb.append("------------------------------\n");
    
        this.ultimoResultado = sb.toString();
        if (escritor != null) {
            escritor.escribirLinea(ultimoResultado);
        }
        System.out.println(ultimoResultado);
     }
        private List<NodoAeropuerto> invertirLista(List<NodoAeropuerto> lista) {
            List<NodoAeropuerto> invertida = new ArrayList<>();
            for (int i = lista.size() - 1; i >= 0; i--) {
                invertida.add(lista.get(i));
            }
            return invertida;
     }
}