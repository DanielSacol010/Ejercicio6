/** Registra diez objetos de cinco clases, sin mantener otra lista. */
public class CargadorInicial {
    public CargadorInicial() { }

    public void cargarEn(GestorIntegraciones gestor) {
        gestor.registrar(new IntegracionPagoTarjeta(1, "Tarjetas Norte", 120.5,
                "modo=simulado;moneda=GTQ;region=norte", true));
        gestor.registrar(new IntegracionPagoTarjeta(2, "Tarjetas Sur", 340.0,
                "modo=simulado;moneda=GTQ;region=sur", false));
        gestor.registrar(new IntegracionTransferencia(3, "Transferencias Central", 250.0,
                "modo=simulado;moneda=GTQ;canal=ACH", true));
        gestor.registrar(new IntegracionTransferencia(4, "Transferencias Regional", 180.0,
                "modo=simulado;moneda=GTQ;canal=interbancario", true));
        gestor.registrar(new IntegracionBilletera(5, "Billetera Uno", 75.0,
                "modo=simulado;moneda=GTQ;canal=movil", true));
        gestor.registrar(new IntegracionBilletera(6, "Billetera Dos", 95.5,
                "modo=simulado;moneda=GTQ;canal=web", true));
        gestor.registrar(new IntegracionLogistica(7, "Envios Urbanos", 420.0,
                "modo=simulado;zona=urbana;formato=XML", true));
        gestor.registrar(new IntegracionLogistica(8, "Envios Regionales", 310.0,
                "modo=simulado;zona=regional;formato=XML", true));
        gestor.registrar(new IntegracionFEL(9, "Certificador A", 600.0,
                "modo=simulado;formato=XML;ambiente=pruebas", false));
        gestor.registrar(new IntegracionFEL(10, "Certificador B", 210.0,
                "modo=simulado;formato=XML;ambiente=pruebas", true));
    }
}
