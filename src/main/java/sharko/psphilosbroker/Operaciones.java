package sharko.psphilosbroker;

/**
 *
 * @author dam2_alu03@inf.ald
 */
public class Operaciones implements Runnable{
    private String tipo;
    private double umbral;
    private double cantidad;
    private Thread hiloEjecutor;

    public Operaciones(String tipo, double umbral, double cantidad) {
        this.tipo = tipo;
        this.umbral = umbral;
        this.cantidad = cantidad;
        hiloEjecutor = new Thread(this);
        hiloEjecutor.start();
    }

    @Override
    public void run() {
        //Bucle
            //Comprobar precio del broker
                //Si    Pedir el lock Sumar o restar liberar lock
                //No    Duermo
    }
    
    public void compra(){
        
    }
    
    public void venta(){
        
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getUmbral() {
        return umbral;
    }

    public void setUmbral(double umbral) {
        this.umbral = umbral;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
}
