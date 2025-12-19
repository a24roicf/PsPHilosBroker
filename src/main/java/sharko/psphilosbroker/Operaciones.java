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

        System.out.println("[HILO] Operación " + tipo.toUpperCase()
                + " iniciada para " + agente.getNombre());
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

                System.out.println("[HILO] " + agente.getNombre()
                        + " quiere " + tipo
                        + " | Precio actual = " + precioActual
                        + " | Umbral = " + umbral);

                if (tipo.equalsIgnoreCase("compra") && precioActual <= umbral) {
                    if (agente.getSaldo() >= cantidad * precioActual) {
                        agente.setSaldo(agente.getSaldo() - cantidad * precioActual);
                        agente.setActivos(agente.getActivos() + cantidad);
                        broker.setPrecioActual(precioActual + 0.5);

                        System.out.println("[COMPRA] ejecutada por "
                                + agente.getNombre()
                                + " | Cantidad = " + cantidad
                                + " | Precio = " + precioActual);

                        break;
                    }
                } else if (tipo.equalsIgnoreCase("venta") && precioActual >= umbral) {
                    if (agente.getActivos() >= cantidad) {
                        agente.setActivos(agente.getActivos() - cantidad);
                        agente.setSaldo(agente.getSaldo() + cantidad * precioActual);
                        broker.setPrecioActual(precioActual - 0.5);

                        System.out.println("[VENTA] ejecutada por "
                                + agente.getNombre()
                                + " | Cantidad = " + cantidad
                                + " | Precio = " + precioActual);

                        break;
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (tipo.equalsIgnoreCase("compra")) {
            agente.limpiarCompra();
        } else {
            agente.limpiarVenta();
        }

        System.out.println("[HILO] Operación finalizada para " + agente.getNombre());
    }
}
