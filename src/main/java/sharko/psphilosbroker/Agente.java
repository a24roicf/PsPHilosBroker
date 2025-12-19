package sharko.psphilosbroker;

import java.io.Serializable;

/**
 *
 * @author dam2_alu03@inf.ald
 */
public class Agente implements Serializable {

    private int id;
    private String nombre;
    private Operaciones compra;
    private Operaciones venta;
    private double saldo;

    public Agente(int id, String nombre, Operaciones compra, Operaciones venta, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.compra = compra;
        this.venta = venta;
        this.saldo = saldo;
    }

    public Agente(int id, String nombre, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.saldo = saldo;
        this.compra = null;
        this.venta = null;
    }

    public boolean nuevaOperacion(String tipo, double limite, double cantidad) {
        switch (tipo) {
            case "compra":
                if (compra == null) {
                    compra = new Operaciones(tipo, limite, cantidad);
                    return true;
                } else {
                    System.out.println("Ya existe una operacion de compra para el agente " + getNombre());
                }
                break;
            case "venta":
                if (venta == null) {
                    venta = new Operaciones(tipo, limite, cantidad);
                    return true;
                } else {
                    System.out.println("Ya existe una operacion de venta para el agente " + getNombre());
                }
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Agente other = (Agente) obj;
        return this.id == other.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Operaciones getCompra() {
        return compra;
    }

    public void setCompra(Operaciones compra) {
        this.compra = compra;
    }

    public Operaciones getVenta() {
        return venta;
    }

    public void setVenta(Operaciones venta) {
        this.venta = venta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", compra=" + compra + ", venta=" + venta + ", saldo=" + saldo + '}';
    }
}
