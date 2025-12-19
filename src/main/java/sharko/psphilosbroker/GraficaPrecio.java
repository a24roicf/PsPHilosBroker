package sharko.psphilosbroker;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.knowm.xchart.*;
/**
 *
 * @author Usuario
 */
public class GraficaPrecio {

    private static final int MAX_PUNTOS = 100;

    private List<Integer> ejeX = new ArrayList<>();
    private List<Double> ejeY = new ArrayList<>();

    private XYChart chart;
    private XChartPanel<XYChart> panel;

    private double precioActual = 100.0;
    private int tiempo = 0;

    public GraficaPrecio() {
        chart = new XYChartBuilder()
                .width(600)
                .height(220)
                .title("Precio de la acción")
                .xAxisTitle("Tiempo")
                .yAxisTitle("Precio")
                .build();

        ejeX.add(0);
        ejeY.add(precioActual);
        
        chart.addSeries("Precio", ejeX, ejeY);
        panel = new XChartPanel<>(chart);

        iniciarSimulacion();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void iniciarSimulacion() {
        Timer timer = new Timer(2000, e -> actualizarPrecio());
        timer.start();
    }

    private void actualizarPrecio() {
        // Simulación simple
        precioActual += (Math.random() - 0.5);

        ejeX.add(tiempo++);
        ejeY.add(precioActual);

        if (ejeX.size() > MAX_PUNTOS) {
            ejeX.remove(0);
            ejeY.remove(0);
        }

        chart.updateXYSeries("Precio", ejeX, ejeY, null);
        panel.revalidate();
        panel.repaint();
    }
}

