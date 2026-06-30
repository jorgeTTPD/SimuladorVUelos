 

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class Interfaz extends javax.swing.JFrame {

    private GrafoViajes grafo;
    private LectorArchivos lector;
    private EscritorArchivos escritor;
    private List<String> listaAeropuertos = new ArrayList<>();

    public Interfaz(GrafoViajes grafo, LectorArchivos lector, EscritorArchivos escritor) {
        this.grafo    = grafo;
        this.lector   = lector;
        this.escritor = escritor;
        initComponents();
        cargarAeropuertosEnCombos();
        configurarCriterios();
        configurarCausas();
        configurarAutocompletado();
    }

    private void cargarAeropuertosEnCombos() {
        IteradorAeropuertos it = grafo.crearIterador();
        while (it.tieneSiguiente()) {
            listaAeropuertos.add(it.siguiente().getNombre());
        }

        DefaultComboBoxModel<String> m1 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> m2 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> m4 = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> m5 = new DefaultComboBoxModel<>();

        for (String a : listaAeropuertos) {
            m1.addElement(a);
            m2.addElement(a);
            m4.addElement(a);
            m5.addElement(a);
        }

        jComboBoxOrigen.setModel(m1);
        jComboBoxDestino.setModel(m2);
        jComboBoxOrigCancelado.setModel(m4);
        jComboBoxDestCancelado.setModel(m5);
    }

    private void configurarCriterios() {
        jComboBoxOptimizacion.setModel(new DefaultComboBoxModel<>(
            new String[]{"COSTO", "TIEMPO", "ESCALA"}
        ));
    }

    private void configurarCausas() {
        jComboBoxCausa.setModel(new DefaultComboBoxModel<>(
            new String[]{"Interna", "Externa", "Ninguna"}
        ));
    }

    private void configurarAutocompletado() {
        configurarFiltro(jComboBoxOrigen);
        configurarFiltro(jComboBoxDestino);
        configurarFiltro(jComboBoxOrigCancelado);
        configurarFiltro(jComboBoxDestCancelado);
    }

    private void configurarFiltro(javax.swing.JComboBox<String> combo) {
        combo.setEditable(true);
        combo.getEditor().getEditorComponent().addKeyListener(
            new java.awt.event.KeyAdapter() {
                public void keyReleased(java.awt.event.KeyEvent e) {
                    String texto = combo.getEditor().getItem().toString().toLowerCase();
                    DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
                    for (String a : listaAeropuertos) {
                        if (a.toLowerCase().startsWith(texto)) {
                            modelo.addElement(a);
                        }
                    }
                    combo.setModel(modelo);
                    combo.getEditor().setItem(texto);
                    if (modelo.getSize() > 0) combo.showPopup();
                }
            }
        );
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanelPrincipal = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jLabelOrigen = new javax.swing.JLabel();
        jLabelDestino = new javax.swing.JLabel();
        jLabelOptimizacion = new javax.swing.JLabel();
        
        jComboBoxOrigen = new javax.swing.JComboBox<>();
        jComboBoxDestino = new javax.swing.JComboBox<>();
        jComboBoxOptimizacion = new javax.swing.JComboBox<>();
        
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaResultado = new javax.swing.JTextArea();
        
        jButtonObtener = new javax.swing.JButton();
        jButtonLimpiar = new javax.swing.JButton();
        jButtonCerrar = new javax.swing.JButton();
        
        jLabelReprogramacion = new javax.swing.JLabel();
        jLabelOrigCancelado = new javax.swing.JLabel();
        jLabelDestCancelado = new javax.swing.JLabel();
        jLabelCausa = new javax.swing.JLabel();
        
        jComboBoxOrigCancelado = new javax.swing.JComboBox<>();
        jComboBoxDestCancelado = new javax.swing.JComboBox<>();
        jComboBoxCausa = new javax.swing.JComboBox<>();
        jButtonReprogramar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Itinerario de Vuelos");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                escritor.cerrar();
                System.exit(0);
            }
        });

        
        jPanelPrincipal.setBackground(new java.awt.Color(165, 42, 42));

        jLabelTitulo.setBackground(new java.awt.Color(245, 245, 220));
        jLabelTitulo.setFont(new java.awt.Font("sansserif", 0, 24)); 
        jLabelTitulo.setForeground(new java.awt.Color(154, 205, 50)); // Texto verde claro
        jLabelTitulo.setText(" Itinerario de vuelos ");
        jLabelTitulo.setOpaque(true);

        jLabelOrigen.setBackground(new java.awt.Color(255, 165, 0)); // Fondo Naranja
        jLabelOrigen.setFont(new java.awt.Font("sansserif", 0, 16)); 
        jLabelOrigen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOrigen.setText("Origen");
        jLabelOrigen.setOpaque(true);

        jLabelDestino.setBackground(new java.awt.Color(255, 165, 0));
        jLabelDestino.setFont(new java.awt.Font("sansserif", 0, 16)); 
        jLabelDestino.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDestino.setText("Destino");
        jLabelDestino.setOpaque(true);

        jLabelOptimizacion.setBackground(new java.awt.Color(255, 165, 0));
        jLabelOptimizacion.setFont(new java.awt.Font("sansserif", 0, 16)); 
        jLabelOptimizacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOptimizacion.setText("Tipo de Optimizacion");
        jLabelOptimizacion.setOpaque(true);

        jTextAreaResultado.setColumns(20);
        jTextAreaResultado.setRows(6);
        jTextAreaResultado.setEditable(false);
        jTextAreaResultado.setFont(new java.awt.Font("Monospaced", 0, 12));
        jScrollPane1.setViewportView(jTextAreaResultado);

        jButtonObtener.setFont(new java.awt.Font("sansserif", 0, 16)); 
        jButtonObtener.setText("Obtener");
        jButtonObtener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonObtenerActionPerformed(evt);
            }
        });

        jButtonLimpiar.setText("Limpiar");
        jButtonLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextAreaResultado.setText("");
            }
        });

        jButtonCerrar.setText("CERRAR");
        jButtonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                escritor.cerrar();
                System.exit(0);
            }
        });

        jLabelReprogramacion.setBackground(new java.awt.Color(245, 245, 220));
        jLabelReprogramacion.setFont(new java.awt.Font("sansserif", 0, 16)); 
        jLabelReprogramacion.setText(" REPROGRAMACION ");
        jLabelReprogramacion.setOpaque(true);

        jLabelOrigCancelado.setBackground(new java.awt.Color(255, 165, 0));
        jLabelOrigCancelado.setFont(new java.awt.Font("sansserif", 0, 15)); 
        jLabelOrigCancelado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelOrigCancelado.setText("Origen Cancelado");
        jLabelOrigCancelado.setOpaque(true);

        jLabelDestCancelado.setBackground(new java.awt.Color(255, 165, 0));
        jLabelDestCancelado.setFont(new java.awt.Font("sansserif", 0, 15)); 
        jLabelDestCancelado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelDestCancelado.setText("Destino Cancelado");
        jLabelDestCancelado.setOpaque(true);

        jLabelCausa.setBackground(new java.awt.Color(255, 165, 0));
        jLabelCausa.setFont(new java.awt.Font("sansserif", 0, 15)); 
        jLabelCausa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCausa.setText("Causa");
        jLabelCausa.setOpaque(true);

        jButtonReprogramar.setText("REPROGRAMAR");
        jButtonReprogramar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReprogramarActionPerformed(evt);
            }
        });

        
        javax.swing.GroupLayout jPanelPrincipalLayout = new javax.swing.GroupLayout(jPanelPrincipal);
        jPanelPrincipal.setLayout(jPanelPrincipalLayout);
        jPanelPrincipalLayout.setHorizontalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                .addGap(25)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxOrigen, 0, 130, Short.MAX_VALUE)
                            .addComponent(jLabelOrigen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15)
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxDestino, 0, 130, Short.MAX_VALUE)
                            .addComponent(jLabelDestino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15)
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelOptimizacion, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                            .addComponent(jComboBoxOptimizacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(jButtonObtener, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                            .addComponent(jButtonCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)))
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxOrigCancelado, 0, 125, Short.MAX_VALUE)
                                    .addComponent(jLabelOrigCancelado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20)
                                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxDestCancelado, 0, 125, Short.MAX_VALUE)
                                    .addComponent(jLabelDestCancelado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20)
                                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxCausa, 0, 110, Short.MAX_VALUE)
                                    .addComponent(jLabelCausa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(20)
                                .addComponent(jButtonReprogramar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                                .addGap(158, 158, 158)
                                .addComponent(jLabelReprogramacion)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(25))
        );
        jPanelPrincipalLayout.setVerticalGroup(
            jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                .addGap(15) 
                .addComponent(jLabelTitulo)
                .addGap(18) 
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOptimizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxOptimizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonObtener, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)) // Alto de consola compacto
                    .addGroup(jPanelPrincipalLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButtonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jButtonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15)
                .addComponent(jLabelReprogramacion)
                .addGap(12)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOrigCancelado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE) // Altura de etiquetas corregida
                    .addComponent(jLabelDestCancelado, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCausa, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxOrigCancelado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxDestCancelado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCausa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReprogramar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)) // Ajustada la altura del botón
                .addContainerGap(20, 20)) // Margen inferior pegado al borde
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void jButtonObtenerActionPerformed(java.awt.event.ActionEvent evt) {
        String origen   = (String) jComboBoxOrigen.getSelectedItem();
        String destino  = (String) jComboBoxDestino.getSelectedItem();
        String criterio = (String) jComboBoxOptimizacion.getSelectedItem();

        try {
            if (origen == null || destino == null)
                throw new Exception("Seleccione origen y destino.");
            if (origen.trim().equals(destino.trim()))
                throw new Exception("El origen y destino no pueden ser iguales.");

            TipoOptimizacion crit;
            switch (criterio) {
                case "COSTO":  crit = new OptimizarCosto();  break;
                case "TIEMPO": crit = new OptimizarTiempo(); break;
                case "ESCALA": crit = new OptimizarEscala(); break;
                default: throw new Exception("Criterio desconocido.");
            }

            lector.guardarBusquedaEntrada(origen, destino, criterio);
            escritor.escribirEncabezadoBusqueda(origen, destino, criterio);

            jTextAreaResultado.append("\n----- BUSQUEDA: " + origen + " -> " + destino
                            + " [" + criterio + "] -----\n");

            grafo.buscarRutaOptima(origen, destino, crit);
            jTextAreaResultado.append(grafo.getUltimoResultado());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                "Error de búsqueda", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void jButtonReprogramarActionPerformed(java.awt.event.ActionEvent evt) {
        String origen  = (String) jComboBoxOrigCancelado.getSelectedItem();
        String destino = (String) jComboBoxDestCancelado.getSelectedItem();
        String causa   = (String) jComboBoxCausa.getSelectedItem();

        try {
            if (origen == null || destino == null)
                throw new Exception("Seleccione origen y destino.");
            if (origen.trim().equals(destino.trim()))
                throw new Exception("El origen y destino no pueden ser iguales.");

            TipoPenalizacion penal = TipoPenalizacion.valueOf(causa);

            lector.guardarReprogramarEntrada(origen, destino, causa);
            escritor.escribirReprogramacion(origen, destino, causa);

            jTextAreaResultado.append("\n----- REPROGRAMACION: " + origen + " -> " + destino + " -----\n");
            jTextAreaResultado.append("Causa: " + causa + "\n");
            jTextAreaResultado.append("Buscando ruta alternativa...\n");

            Viaje ref = new Viaje("N/A", 0, origen, destino,
                TipoViaje.Directo, TipoDestino.Nacional,
                0, 0, 0, 0.0, TipoPromocion.Ninguna, penal);

            ReprogramadorVuelos rep = new ReprogramadorVuelos(grafo);
            rep.reprogramar(ref, penal);

            jTextAreaResultado.append(grafo.getUltimoResultado());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                "Error de reprogramación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private javax.swing.JButton jButtonReprogramar;
    private javax.swing.JButton jButtonObtener;
    private javax.swing.JButton jButtonLimpiar;
    private javax.swing.JButton jButtonCerrar;
    private javax.swing.JComboBox<String> jComboBoxOrigen;
    private javax.swing.JComboBox<String> jComboBoxDestino;
    private javax.swing.JComboBox<String> jComboBoxOptimizacion;
    private javax.swing.JComboBox<String> jComboBoxOrigCancelado;
    private javax.swing.JComboBox<String> jComboBoxDestCancelado;
    private javax.swing.JComboBox<String> jComboBoxCausa;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelReprogramacion;
    private javax.swing.JLabel jLabelOrigen;
    private javax.swing.JLabel jLabelDestino;
    private javax.swing.JLabel jLabelOptimizacion;
    private javax.swing.JLabel jLabelOrigCancelado;
    private javax.swing.JLabel jLabelDestCancelado;
    private javax.swing.JLabel jLabelCausa;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaResultado;
}