import java.util.Locale;

public abstract class Integracion implements Comparable<Integracion> {
    private final int id;
    private String nombre;
    private double latenciaPromedio;
    private String parametrosConfiguracion;
    private boolean estadoConexion;

    protected Integracion(int id, String nombre, double latenciaPromedio,
            String parametrosConfiguracion, boolean estadoConexion) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (!Double.isFinite(latenciaPromedio) || latenciaPromedio < 0) {
            throw new IllegalArgumentException("La latencia debe ser finita y no negativa.");
        }
        if (parametrosConfiguracion == null) {
            throw new IllegalArgumentException("La configuracion no puede ser null.");
        }
        this.id = id;
        this.nombre = nombre.trim();
        this.latenciaPromedio = latenciaPromedio;
        this.parametrosConfiguracion = parametrosConfiguracion;
        this.estadoConexion = estadoConexion;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        this.nombre = nombre.trim();
    }

    public double getLatenciaPromedio() { return latenciaPromedio; }

    public void setLatenciaPromedio(double latenciaPromedio) {
        if (!Double.isFinite(latenciaPromedio) || latenciaPromedio < 0) {
            throw new IllegalArgumentException("La latencia debe ser finita y no negativa.");
        }
        this.latenciaPromedio = latenciaPromedio;
    }

    public String getParametrosConfiguracion() { return parametrosConfiguracion; }

    public void setParametrosConfiguracion(String parametrosConfiguracion) {
        if (parametrosConfiguracion == null) {
            throw new IllegalArgumentException("La configuracion no puede ser null.");
        }
        this.parametrosConfiguracion = parametrosConfiguracion;
    }

    public boolean isEstadoConexion() { return estadoConexion; }
    public void setEstadoConexion(boolean estadoConexion) {
        this.estadoConexion = estadoConexion;
    }

    /** Orden descendente: primero los posibles cuellos de botella. */
    @Override
    public int compareTo(Integracion otra) {
        return Double.compare(otra.latenciaPromedio, this.latenciaPromedio);
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%d | %s | %.2f ms | %s", id, nombre,
                latenciaPromedio, estadoConexion ? "Conectada" : "Desconectada");
    }
}
