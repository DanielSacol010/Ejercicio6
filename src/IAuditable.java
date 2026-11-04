/** En este diseno, auditar significa consultar el estado de una referencia. */
public interface IAuditable {
    ResultadoOperacion consultarEstado(String referencia);
}
