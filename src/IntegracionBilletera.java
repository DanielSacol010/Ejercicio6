import java.util.concurrent.ThreadLocalRandom;

/** Integracion de billetera. Simula el proveedor*/
public class IntegracionBilletera extends Integracion implements ITransaccional, IAuditable {
    public IntegracionBilletera(int id, String nombre, double latenciaPromedio,
            String parametrosConfiguracion, boolean estadoConexion) {
        super(id, nombre, latenciaPromedio, parametrosConfiguracion, estadoConexion);
    }

    @Override
    public ResultadoOperacion cobrar(double monto) {
        if (!isEstadoConexion()) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "La integracion esta desconectada.", null, null);
        }
        if (!Double.isFinite(monto) || monto <= 0) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "El monto debe ser finito y mayor que cero.", null, null);
        }
        int idTransaccion = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return new ResultadoOperacion(EstadoResultado.EXITO,
                "Cobro SIMULADO por billetera: " + monto + " GTQ. No se movio dinero.",
                idTransaccion, null);
    }

    @Override
    public ResultadoOperacion reintegrar(int idTransaccion, double monto) {
        if (!isEstadoConexion()) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "La integracion esta desconectada.", null, null);
        }
        if (idTransaccion <= 0 || !Double.isFinite(monto) || monto <= 0) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "El ID del cobro y el monto deben ser positivos y el monto finito.",
                    null, null);
        }
        // El contrato se demuestra sin introducir un historial no previsto en el UML.
        return new ResultadoOperacion(EstadoResultado.EXITO,
                "Reintegro SIMULADO por billetera: " + monto
                        + " GTQ. No verifica un cobro previo ni mueve dinero.",
                idTransaccion, null);
    }

    @Override
    public ResultadoOperacion consultarEstado(String referencia) {
        if (!isEstadoConexion()) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "La integracion esta desconectada.", null, null);
        }
        if (referencia == null || referencia.trim().isEmpty()) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "La referencia es obligatoria.", null, null);
        }
        return new ResultadoOperacion(EstadoResultado.EXITO,
                "Consulta SIMULADA; no verifica una referencia real.", null,
                "Referencia: " + referencia.trim() + " | Pago de billetera procesado (demostracion)");
    }
}
