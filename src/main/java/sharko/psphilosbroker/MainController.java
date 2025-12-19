package sharko.psphilosbroker;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class MainController {

    private MainJFrame view;
    private Broker broker;
    private int contadorId = 1;

    public MainController(MainJFrame view) {
        this.view = view;
        this.broker = new Broker();

        initController();
    }

    private void initController() {
        view.addCrearAgenteButtonListener(e -> crearAgente());
        view.addCrearOperacionListener(e -> crearOperacion());
    }

    private void crearAgente() {
        String nombre = view.getNombreAgente();
        String saldoTxt = view.getSaldoAgente();

        if (nombre.isEmpty()) {
            mostrarError("El nombre no puede estar vacío");
            return;
        }

        double saldo;
        try {
            saldo = Double.parseDouble(saldoTxt);
        } catch (NumberFormatException e) {
            mostrarError("El saldo debe ser numérico");
            return;
        }

        if (saldo < 0) {
            mostrarError("El saldo no puede ser negativo");
            return;
        }

        if (broker.existeAgente(nombre)) {
            mostrarError("Ya existe un agente con ese nombre");
            return;
        }

        Agente agente = new Agente(contadorId++, nombre, saldo);
        broker.addAgente(agente);

        DefaultTableModel model = view.getModeloTablaAgentes();
        model.addRow(new Object[]{
            agente.getNombre(),
            agente.getSaldo(),
            0
        });

        view.limpiarFormularioAgente();
        view.addAgenteCombo(agente);
    }

    private void crearOperacion() {
        Agente agente = view.getAgenteSeleccionado();

        if (agente == null) {
            mostrarError("Selecciona un agente");
            return;
        }

        String tipo = view.getTipoOperacion();
        String cantidadTxt = view.getCantidadOperacion();
        String precioTxt = view.getPrecioOperacion();

        double cantidad, precio;
        try {
            cantidad = Double.parseDouble(cantidadTxt);
            precio = Double.parseDouble(precioTxt);
        } catch (NumberFormatException e) {
            mostrarError("Cantidad y precio tienen que ser numéricos");
            return;
        }

        if (cantidad <= 0 || precio <= 0) {
            mostrarError("Cantidad y precio tienen que ser mayores que 0");
            return;
        }

        boolean creada = agente.nuevaOperacion(
                tipo.toLowerCase(),
                precio,
                cantidad
        );

        if (!creada) {
            mostrarError("El agente ya tiene una orden de " + tipo);
            return;
        }

        JOptionPane.showMessageDialog(
                view,
                "Operación de " + tipo + " registrada correctamente"
        );

        view.limpiarFormularioOperacion();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(view, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
