import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GestorIntegraciones {
    private final List<Integracion> integraciones;


    public GestorIntegraciones() {
        integraciones = new ArrayList<Integracion>();
    }

    /** Vista no modificable de la misma lista, sin copiar sus elementos. */
    public List<Integracion> getIntegraciones() {
        return Collections.unmodifiableList(integraciones);
    }

    public boolean registrar(Integracion integracion) {
        if (integracion == null || buscar(integracion.getId()) != null) {
            return false;
        }
        integraciones.add(integracion);
        return true;
    }

    public Integracion buscar(int id) {
        Integracion encontrada = null;
        int posicion = 0;
        while (posicion < integraciones.size() && encontrada == null) {
            Integracion actual = integraciones.get(posicion);
            if (actual.getId() == id) {
                encontrada = actual;
            }
            posicion++;
        }
        return encontrada;
    }

    public void ordenarPorLatencia() {
        Collections.sort(integraciones);
    }

    public ResultadoOperacion cobrar(int idIntegracion, double monto) {
        Integracion integracion = buscar(idIntegracion);
        if (integracion == null) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "No existe una integracion con ese ID.", null, null);
        }
        if (!(integracion instanceof ITransaccional)) {
            return new ResultadoOperacion(EstadoResultado.OMITIDA,
                    "Esta integracion no permite cobros.", null, null);
        }
        return procesarTransaccion((ITransaccional) integracion, monto);
    }

    public ResultadoOperacion reintegrar(int idIntegracion, int idTransaccion, double monto) {
        Integracion integracion = buscar(idIntegracion);
        if (integracion == null) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "No existe una integracion con ese ID.", null, null);
        }
        // La capacidad se comprueba antes de convertir: logistica se omite sin excepcion.
        if (!(integracion instanceof ITransaccional)) {
            return new ResultadoOperacion(EstadoResultado.OMITIDA,
                    "Esta integracion no permite reintegros.", null, null);
        }
        return procesarTransaccion((ITransaccional) integracion, idTransaccion, monto);
    }

    public ResultadoOperacion generarDocumento(int idIntegracion, String datos) {
        Integracion integracion = buscar(idIntegracion);
        if (integracion == null) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "No existe una integracion con ese ID.", null, null);
        }
        if (!(integracion instanceof IDocumentable)) {
            return new ResultadoOperacion(EstadoResultado.OMITIDA,
                    "Esta integracion no genera documentos.", null, null);
        }
        return documentar((IDocumentable) integracion, datos);
    }

    public ResultadoOperacion consultarEstado(int idIntegracion, String referencia) {
        Integracion integracion = buscar(idIntegracion);
        if (integracion == null) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "No existe una integracion con ese ID.", null, null);
        }
        if (!(integracion instanceof IAuditable)) {
            return new ResultadoOperacion(EstadoResultado.OMITIDA,
                    "Esta integracion no permite consultas de estado.", null, null);
        }
        return auditar((IAuditable) integracion, referencia);
    }

    // Sobrecarga: el primer conjunto de parametros representa un cobro.
    private ResultadoOperacion procesarTransaccion(ITransaccional servicio, double monto) {
        return servicio.cobrar(monto);
    }

    // Sobrecarga: el segundo conjunto representa un reintegro del cobro indicado.
    private ResultadoOperacion procesarTransaccion(ITransaccional servicio,
            int idTransaccion, double monto) {
        return servicio.reintegrar(idTransaccion, monto);
    }

    private ResultadoOperacion documentar(IDocumentable servicio, String datos) {
        return servicio.generarDocumento(datos);
    }

    private ResultadoOperacion auditar(IAuditable servicio, String referencia) {
        return servicio.consultarEstado(referencia);
    }
}
