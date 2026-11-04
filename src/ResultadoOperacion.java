/** Resultado inmutable que el modelo entrega a la vista mediante el controlador. */
public class ResultadoOperacion {
    private final EstadoResultado estado;
    private final String mensaje;
    private final Integer idTransaccion;
    private final String contenido;

    public ResultadoOperacion(EstadoResultado estado, String mensaje,
            Integer idTransaccion, String contenido) {
        if (estado == null || mensaje == null) {
            throw new IllegalArgumentException("El estado y el mensaje son obligatorios.");
        }
        this.estado = estado;
        this.mensaje = mensaje;
        this.idTransaccion = idTransaccion;
        this.contenido = contenido;
    }

    public EstadoResultado getEstado() { return estado; }
    public String getMensaje() { return mensaje; }
    public Integer getIdTransaccion() { return idTransaccion; }
    public String getContenido() { return contenido; }
}
