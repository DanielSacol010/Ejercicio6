/** Integracion de FEL. Simula el proveedor*/
public class IntegracionFEL extends Integracion implements IDocumentable, IAuditable {
    public IntegracionFEL(int id, String nombre, double latenciaPromedio,
            String parametrosConfiguracion, boolean estadoConexion) {
        super(id, nombre, latenciaPromedio, parametrosConfiguracion, estadoConexion);
    }

    @Override
    public ResultadoOperacion generarDocumento(String datos) {
        if (!isEstadoConexion()) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "La integracion esta desconectada.", null, null);
        }
        if (datos == null || datos.trim().isEmpty()) {
            return new ResultadoOperacion(EstadoResultado.FALLO,
                    "Debes proporcionar los datos del documento.", null, null);
        }
        String datosXml = datos.trim().replace("&", "&amp;")
                .replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&apos;");
        String contenido = "<FacturaSimulada><Integracion>" + getId()
                + "</Integracion><Datos>" + datosXml + "</Datos></FacturaSimulada>";
        return new ResultadoOperacion(EstadoResultado.EXITO,
                "Documento SIMULADO de FEL, sin validez legal ni fiscal.",
                null, contenido);
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
                "Referencia: " + referencia.trim() + " | Documento recibido para revision (demostracion)");
    }
}
