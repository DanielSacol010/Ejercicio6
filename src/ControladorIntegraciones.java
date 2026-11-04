import java.util.NoSuchElementException;

/** Coordina vista y modelo.*/
public class ControladorIntegraciones {
    private final VistaSistema vista;
    private final GestorIntegraciones gestor;
    private final CargadorInicial cargador;

    public ControladorIntegraciones() {
        vista = new VistaSistema();
        gestor = new GestorIntegraciones();
        cargador = new CargadorInicial();
    }

    public void iniciar() {
        cargador.cargarEn(gestor);
        vista.mostrarMensaje("Se cargaron " + gestor.getIntegraciones().size() + " integraciones.");
        int opcion = -1;
        do {
            try {
                vista.mostrarMenu();
                opcion = vista.leerEntero("Selecciona una opcion: ");
                procesarOpcion(opcion);
            } catch (NoSuchElementException finEntrada) {
                // Salida controlada al cerrar la entrada, incluso a mitad de una solicitud.
                opcion = 0;
                vista.mostrarMensaje("Fin de la entrada. Programa finalizado.");
            }
        } while (opcion != 0);
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 0: vista.mostrarMensaje("Programa finalizado."); break;
            case 1: listarIntegraciones(); break;
            case 2: buscarIntegracion(); break;
            case 3: ordenarIntegraciones(); break;
            case 4: realizarCobro(); break;
            case 5: realizarReintegro(); break;
            case 6: generarDocumento(); break;
            case 7: consultarEstado(); break;
            default: vista.mostrarMensaje("Opcion no valida. Elige un numero de 0 a 7.");
        }
    }

    private void listarIntegraciones() {
        vista.mostrarIntegraciones(gestor.getIntegraciones());
    }

    private void buscarIntegracion() {
        int id = vista.leerEntero("ID de la integracion: ");
        Integracion integracion = gestor.buscar(id);
        if (integracion == null) {
            vista.mostrarMensaje("No existe una integracion con ese ID.");
        } else {
            vista.mostrarDetalle(integracion);
        }
    }

    private void ordenarIntegraciones() {
        gestor.ordenarPorLatencia();
        vista.mostrarMensaje("Ordenadas de mayor a menor latencia.");
        listarIntegraciones();
    }

    private void realizarCobro() {
        int id = vista.leerEntero("ID de la integracion de pago: ");
        double monto = vista.leerDecimal("Monto del cobro en GTQ: ");
        vista.mostrarResultado(gestor.cobrar(id, monto));
    }

    private void realizarReintegro() {
        int id = vista.leerEntero("ID de la integracion: ");
        int idTransaccion = vista.leerEntero("ID del cobro original: ");
        double monto = vista.leerDecimal("Monto a reintegrar en GTQ: ");
        vista.mostrarResultado(gestor.reintegrar(id, idTransaccion, monto));
    }

    private void generarDocumento() {
        int id = vista.leerEntero("ID de la integracion documental: ");
        String datos = vista.leerTexto("Datos de demostracion para el documento: ");
        vista.mostrarResultado(gestor.generarDocumento(id, datos));
    }

    private void consultarEstado() {
        int id = vista.leerEntero("ID de la integracion: ");
        String referencia = vista.leerTexto("Referencia del pago, envio o factura: ");
        vista.mostrarResultado(gestor.consultarEstado(id, referencia));
    }
}
