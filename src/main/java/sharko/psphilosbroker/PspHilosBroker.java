package sharko.psphilosbroker;

/**
 *
 * @author dam2_alu03@inf.ald
 */
public class PspHilosBroker {

    public static void main(String[] args) {
        MainJFrame view = new MainJFrame();
        MainController mc = new MainController(view);
        view.setVisible(true);
    }
}
