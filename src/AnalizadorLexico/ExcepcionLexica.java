package AnalizadorLexico;

public class ExcepcionLexica extends Exception {

    private int numeroLinea;
    private int numeroColumna;
    private String lexema;
    private String tipoError;
    private String lineaContenido;

    public ExcepcionLexica(int numeroLinea, int numeroColumna, String lexema, String tipoError, String lineaContenido) {
        this.numeroLinea = numeroLinea;
        this.numeroColumna = numeroColumna;
        this.lexema = lexema;
        this.tipoError = tipoError;
        this.lineaContenido = lineaContenido;
    }

    public String getMessage(){
        return '\n' + this.formatearMensaje(numeroLinea,numeroColumna,lexema,tipoError,lineaContenido);
    }

    private static String formatearMensaje(int numeroLinea, int numeroColumna, String lexema, String tipoError, String lineaContenido) {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Error Léxico en linea ").append(numeroLinea).append(", columna ").append(numeroColumna).append(": ").append(tipoError).append("\n");
        mensaje.append("Detalle: ").append(lineaContenido).append("\n");
        mensaje.append(" ".repeat("Detalle: ".length() + numeroColumna - 1)).append("^\n");
        mensaje.append("[Error:").append(lexema).append("|").append(numeroLinea).append("]");
        return mensaje.toString();
    }
}
