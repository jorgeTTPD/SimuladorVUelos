import java.io.*;

public class LectorArchivos {

    private GrafoViajes grafo;
    private EscritorArchivos escritor;

    public LectorArchivos(GrafoViajes grafo, EscritorArchivos escritor) {
        this.grafo = grafo;
        this.escritor = escritor;
    }

    public void cargarAeropuertos(String rutaArchivo) {
        String ruta = System.getProperty("user.dir") + "/" + rutaArchivo;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { primeraLinea = false; continue; }
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(",");
                if (partes.length != 2) {
                    System.out.println("Linea invalida en aeropuertos.txt: " + linea);
                    continue;
                }

                String ciudad = partes[0].trim();
                String pais   = partes[1].trim();

                grafo.agregarAeropuerto(ciudad);
                System.out.println("Aeropuerto: " + ciudad + " | " + pais);
            }

        } catch (FileNotFoundException e) {
            System.out.println(" no se encontra el archivo " + ruta);
        } catch (IOException e) {
            System.out.println("error leer " + ruta + ": " + e.getMessage());
        }
    }

   
    public void cargarVuelos(String rutaArchivo) {
        String ruta = System.getProperty("user.dir") + "/" + rutaArchivo;

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { primeraLinea = false; continue; }
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(",");
                procesarVuelo(partes);
            }

        } catch (FileNotFoundException e) {
            System.out.println("no se encontro el archivo " + ruta);
        } catch (IOException e) {
            System.out.println("error " + ruta + ": " + e.getMessage());
        }
    }

    private void procesarVuelo(String[] partes) {
        try {
            String aerolinea        = partes[0].trim();
            int codigoVuelo         = Integer.parseInt(partes[1].trim());
            String origen           = partes[2].trim();
            String destino          = partes[3].trim();
            TipoViaje tipoViaje     = TipoViaje.valueOf(partes[4].trim());
            TipoDestino tipoDestino = TipoDestino.valueOf(partes[5].trim());
            int horaSalida          = Integer.parseInt(partes[6].trim());
            int horaLlegada         = Integer.parseInt(partes[7].trim());
            int capacidad           = Integer.parseInt(partes[8].trim());
            double costo            = Double.parseDouble(partes[9].trim());
            TipoPromocion promo     = TipoPromocion.valueOf(partes[10].trim());
            TipoPenalizacion penal  = TipoPenalizacion.valueOf(partes[11].trim());

            Viaje v = new Viaje(aerolinea, codigoVuelo, origen, destino,
                tipoViaje, tipoDestino, horaSalida, horaLlegada,
                capacidad, costo, promo, penal);

            grafo.conectarViaje(v);
            System.out.println("Vuelo cargado: " + codigoVuelo + " | " + origen + " -> " + destino);
            escritor.escribirVueloCargado(codigoVuelo, origen, destino);

        } catch (Exception e) {
            System.out.println("error procesar vuelo: " + e.getMessage());
        }
    }
    
    public void guardarBusquedaEntrada(String origen, String destino, String criterio) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("entrada.txt", true))) {
            bw.write("BUSQUEDA," + origen + "," + destino + "," + criterio);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("error guardar entrada.txt: " + e.getMessage());
        }
    }
    
    public void guardarReprogramarEntrada(String origen, String destino, String causa) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("entrada.txt", true))) {
            bw.write("REPROGRAMAR," + origen + "," + destino + "," + causa);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar en entrada.txt: " + e.getMessage());
        }
    }
}