/** Contrato financiero compartido por distintos proveedores. */
public interface ITransaccional {
    ResultadoOperacion cobrar(double monto);
    ResultadoOperacion reintegrar(int idTransaccion, double monto);
}
