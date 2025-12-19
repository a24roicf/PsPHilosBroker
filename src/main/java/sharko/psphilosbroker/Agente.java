package sharko.psphilosbroker;

import java.io.Serializable;

public class Agente implements Serializable {

    private int id;
    private String nombre;
    private Operaciones compra;
    private Operaciones venta;
    private double saldo;
    private double activos;

    public Agente(int id, String nombre, double saldo, double activos) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.activos = activos;
        this.compra = null;
        this.venta = null;
    }

    public boolean nuevaOperacion(String tipo, double limite, double cantidad, Broker broker) {
        switch (tipo.toLowerCase()) {
            case "compra":
                if (compra == null) {
                    compra = new Operaciones(tipo, limite, cantidad, broker, this);
                    System.out.println("[AGENTE " + nombre + "] Nueva COMPRA -> Cantidad= "
                        + cantidad + " Precio límite = " + limite);
                    return true;
                }
                break;
            case "venta":
                if (venta == null) {
                    venta = new Operaciones(tipo, limite, cantidad, broker, this);
                    System.out.println("[AGENTE " + nombre + "] Nueva VENTA -> Cantidad="
                        + cantidad + " Precio límite = " + limite);
                    return true;
                }
                System.out.println("[AGENTE " + nombre + "] Ya existe una VENTA");
                break;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getActivos() {
        return activos;
    }

    public void setActivos(double activos) {
        this.activos = activos;
    }

    public Operaciones getCompra() {
        return compra;
    }

    public Operaciones getVenta() {
        return venta;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
