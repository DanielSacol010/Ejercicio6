import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Vista de consola. Nombre conservado por solicitud del usuario; ver decisiones. */
public class VistaSistema {
    private final Scanner entrada;

    // Constructor explicitado para inicializar la entrada de consola.
    public VistaSistema() {
        entrada = new Scanner(System.in, "UTF-8");
    }

    public void mostrarMenu() {
        System.out.println();
        System.out.println("=== SISTEMA DE INTEGRACIONES ===");
        System.out.println("1. Listar integraciones y conexiones");
        System.out.println("2. Buscar integracion y ver configuracion");
        System.out.println("3. Ordenar por latencia (mayor primero)");
        System.out.println("4. Simular cobro");
        System.out.println("5. Simular reintegro");
        System.out.println("6. Generar documento de demostracion");
        System.out.println("7. Consultar estado de demostracion");
        System.out.println("0. Salir");
    }

    public int leerEntero(String mensaje) {
        boolean valido = false;
        int valor = 0;
        do {
            String texto = leerTexto(mensaje);
            try {
                valor = Integer.parseInt(texto);
                valido = true;
            } catch (NumberFormatException error) {
                mostrarMensaje("Ingresa un numero entero valido.");
            }
        } while (!valido);
        return valor;
    }

    public double leerDecimal(String mensaje) {
        boolean valido = false;
        double valor = 0;
        do {
            String texto = leerTexto(mensaje).replace(',', '.');
            try {
                valor = Double.parseDouble(texto);
                valido = Double.isFinite(valor);
                if (!valido) {
                    mostrarMensaje("Ingresa un numero decimal finito.");
                }
            } catch (NumberFormatException error) {
                mostrarMensaje("Ingresa un numero decimal valido, sin separadores de miles.");
            }
        } while (!valido);
        return valor;
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        if (!entrada.hasNextLine()) {
            throw new NoSuchElementException("Fin de la entrada de consola.");
        }
        return entrada.nextLine().trim();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarResultado(ResultadoOperacion resultado) {
        mostrarMensaje("[" + resultado.getEstado() + "] " + resultado.getMensaje());
        if (resultado.getIdTransaccion() != null) {
            mostrarMensaje("ID de transaccion: " + resultado.getIdTransaccion());
        }
        if (resultado.getContenido() != null) {
            mostrarMensaje(resultado.getContenido());
        }
    }

    public void mostrarIntegraciones(List<Integracion> integraciones) {
        if (integraciones.isEmpty()) {
            mostrarMensaje("No hay integraciones registradas.");
        } else {
            mostrarMensaje("ID | Nombre | Latencia | Conexion");
            for (Integracion integracion : integraciones) {
                mostrarMensaje(integracion.toString());
            }
        }
    }

    public void mostrarDetalle(Integracion integracion) {
        mostrarMensaje(integracion.toString());
        mostrarMensaje("Tipo: " + integracion.getClass().getSimpleName());
        mostrarMensaje("Configuracion: " + integracion.getParametrosConfiguracion());
        mostrarMensaje("Transaccional: " + (integracion instanceof ITransaccional ? "Si" : "No"));
        mostrarMensaje("Documentable: " + (integracion instanceof IDocumentable ? "Si" : "No"));
        mostrarMensaje("Auditable: " + (integracion instanceof IAuditable ? "Si" : "No"));
    }
}
