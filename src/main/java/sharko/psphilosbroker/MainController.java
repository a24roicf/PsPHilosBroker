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
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(view, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
