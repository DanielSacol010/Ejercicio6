/** Driver: mantiene un controlador y arranca su ejecucion. */
public class Main {
    private final ControladorIntegraciones controlador = new ControladorIntegraciones();

    public static void main(String[] args) {
        Main programa = new Main();
        programa.controlador.iniciar();
    }
}
