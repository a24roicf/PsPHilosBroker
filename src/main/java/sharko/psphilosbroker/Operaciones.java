package sharko.psphilosbroker;

import java.io.Serializable;

public class Operaciones implements Runnable, Serializable {

    private String tipo;
    private double umbral;
    private double cantidad;
    private transient Thread hiloEjecutor;  //(transient) Para que no lo serialice (buscado en internet)
    private Broker broker;
    private Agente agente;

    public Operaciones(String tipo, double umbral, double cantidad, Broker broker, Agente agente) {
        this.tipo = tipo;
        this.umbral = umbral;
        this.cantidad = cantidad;
        this.broker = broker;
        this.agente = agente;
        hiloEjecutor = new Thread(this);
        hiloEjecutor.start();
    }

    public void restartThread(Broker broker, Agente agente) {
        this.broker = broker;
        this.agente = agente;
        hiloEjecutor = new Thread(this);
        hiloEjecutor.start();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (broker.getLock()) {
                double precioActual = broker.getPrecioActual();
                if (tipo.equalsIgnoreCase("compra") && precioActual <= umbral) {
                    if (agente.getSaldo() >= cantidad * precioActual) {
                        agente.setSaldo(agente.getSaldo() - cantidad * precioActual);
                        agente.setActivos(agente.getActivos() + cantidad);
                        broker.setPrecioActual(precioActual + 0.1);
                        break;
                    }
                } else if (tipo.equalsIgnoreCase("venta") && precioActual >= umbral) {
                    if (agente.getActivos() >= cantidad) {
                        agente.setActivos(agente.getActivos() - cantidad);
                        agente.setSaldo(agente.getSaldo() + cantidad * precioActual);
                        broker.setPrecioActual(precioActual - 0.1);
                        break;
                    }
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
