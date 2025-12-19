package sharko.psphilosbroker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainController {

    private MainJFrame view;
    private Broker broker;
    private int contadorId = 1;

    public MainController(MainJFrame view) {
        this.view = view;
        this.broker = Broker.cargar();
        initController();
        cargarDatos();
        guardarAlSalir();
    }

    private void initController() {
        view.addCrearAgenteButtonListener(e -> crearAgente());
        view.addCrearOperacionListener(e -> crearOperacion());
    }

    private void guardarAlSalir() {
        view.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                broker.guardar();
            }
        });
    }

    private void crearAgente() {
        String nombre = view.getNombreAgente();
        double saldo = Double.parseDouble(view.getSaldoAgente());
        double activos = Double.parseDouble(view.getActivosAgente());
        Agente agente = new Agente(contadorId++, nombre, saldo, activos);
        broker.addAgente(agente);
        DefaultTableModel model = view.getModeloTablaAgentes();
        model.addRow(new Object[]{agente.getNombre(), agente.getSaldo(), agente.getActivos()});
        view.limpiarFormularioAgente();
        view.addAgenteCombo(agente);
    }

    private void crearOperacion() {
        Agente agente = view.getAgenteSeleccionado();
        String tipo = view.getTipoOperacion();
        double cantidad = Double.parseDouble(view.getCantidadOperacion());
        double precio = Double.parseDouble(view.getPrecioOperacion());
        boolean creada = agente.nuevaOperacion(tipo.toLowerCase(), precio, cantidad, broker);
        if (!creada) {
            return;
        }
        view.limpiarFormularioOperacion();
    }

    private void cargarDatos() {
        DefaultTableModel model = view.getModeloTablaAgentes();
        model.setRowCount(0);
        for (Agente agente : broker.getAgentes()) {
            model.addRow(new Object[]{agente.getNombre(), agente.getSaldo(), agente.getActivos()});
            view.addAgenteCombo(agente);
        }
    }
}
