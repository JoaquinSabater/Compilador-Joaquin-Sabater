package AnalizadorLexico;

public class ExcepcionLexica extends Exception {
    public ExcepcionLexica(int numeroLinea, int numeroColumna, String lexema, String tipoError, String lineaContenido) {
        super(formatearMensaje(numeroLinea, numeroColumna, lexema, tipoError, lineaContenido));
    }

    private static String formatearMensaje(int numeroLinea, int numeroColumna, String lexema, String tipoError, String lineaContenido) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Error Léxico en linea ").append(numeroLinea).append(": ").append(lexema).append(" ").append(tipoError).append("\n");
        mensaje.append("Detalle: ").append(lineaContenido).append("\n");
        mensaje.append(" ".repeat("Detalle: ".length() + numeroColumna - 1)).append("^\n");
        mensaje.append("[Error:").append(lexema).append("|").append(numeroLinea).append("]");
        return mensaje.toString();
    }
}
