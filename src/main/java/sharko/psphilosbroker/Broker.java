package sharko.psphilosbroker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Broker implements Serializable {

    private static final String BROKER = "broker.dat";
    private double precioActual;
    private double maximo;
    private double minimo;
    private List<Agente> agentes;
    private transient Object lock = new Object();     //(transient) Para que no lo serialice (buscado en internet)

    public Broker(double precioInicial) {
        this.precioActual = precioInicial;
        this.maximo = precioInicial;
        this.minimo = precioInicial;
        agentes = new ArrayList<>();
    }

    public Broker() {
        this(100.0);
    }

    public Object getLock() {
        if (lock == null) {
            lock = new Object();
        }
        return lock;
    }

    public boolean existeAgente(String nombre) {
        return agentes.stream().anyMatch(a -> a.getNombre().equalsIgnoreCase(nombre));
    }

    public boolean addAgente(Agente a) {
        if (existeAgente(a.getNombre())) {
            return false;
        }
        agentes.add(a);
        System.out.println("[BROKER] Agente creado: " + a.getNombre()
                + " | Saldo = " + a.getSaldo()
                + " | Activos = " + a.getActivos());
        return true;
    }

    public List<Agente> getAgentes() {
        return agentes;
    }

    public double getPrecioActual() {
        synchronized (lock) {
            return precioActual;
        }
    }

    public void setPrecioActual(double precio) {
        synchronized (lock) {
            this.precioActual = precio;
        }
    }

    public int operacionesActivas() {
        int count = 0;
        for (Agente a : agentes) {
            if (a.getCompra() != null) {
                count++;
            }
            if (a.getVenta() != null) {
                count++;
            }
        }
        return count;
    }

    public void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BROKER))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Broker cargar() {
        File f = new File(BROKER);
        if (!f.exists()) {
            return new Broker();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BROKER))) {
            Broker b = (Broker) ois.readObject();
            for (Agente a : b.getAgentes()) {
                if (a.getCompra() != null) {
                    a.getCompra().restartThread(b, a);
                }
                if (a.getVenta() != null) {
                    a.getVenta().restartThread(b, a);
                }
            }
            return b;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Broker();
        }
    }
}
