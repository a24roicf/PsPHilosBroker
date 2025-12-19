package sharko.psphilosbroker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Broker implements Serializable {

    private static final String BROKER = "broker.dat";

    private double precioActual;
    private double maximo;
    private double minimo;

    private List<Agente> agentes;
    private List<Operaciones> operacionesPendientes;
    private List<Operaciones> operacionesEjecutadas;

    public Broker(double precioInicial) {
        this.precioActual = precioInicial;
        this.maximo = precioInicial;
        this.minimo = precioInicial;
        agentes = new ArrayList<>();
        operacionesPendientes = new ArrayList<>();
        operacionesEjecutadas = new ArrayList<>();
    }

    public Broker() {
        agentes = new ArrayList<>();
        operacionesPendientes = new ArrayList<>();
        operacionesEjecutadas = new ArrayList<>();
    }

    public boolean existeAgente(String nombre) {
        return agentes.stream().anyMatch(a -> a.getNombre().equalsIgnoreCase(nombre));
    }

    public boolean addAgente(Agente a) {
        if (existeAgente(a.getNombre())) {
            return false;
        }
        agentes.add(a);
        return true;
    }

    public List<Agente> getAgentes() {
        return agentes;
    }

    public void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BROKER))) {
            oos.writeObject(this);
            System.out.println("Broker guardado correctamente.");
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
            return (Broker) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new Broker();
        }
    }
}