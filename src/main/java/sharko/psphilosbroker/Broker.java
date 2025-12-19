package sharko.psphilosbroker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Broker implements Serializable {

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

    public Broker(){
        agentes = new ArrayList<>();
    }
    
    public boolean existeAgente(String nombre) {
        return agentes.stream().anyMatch(a -> a.getNombre().equalsIgnoreCase(nombre));
    }
    
    public boolean addAgente(Agente a) {
        if (existeAgente(a.getNombre())) return false;
        agentes.add(a);
        return true;
    }

    public List<Agente> getAgentes() {
        return agentes;
    }
}
