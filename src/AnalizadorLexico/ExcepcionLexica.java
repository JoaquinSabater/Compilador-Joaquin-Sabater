package AnalizadorLexico;

public class ExcepcionLexica extends Exception {
    public ExcepcionLexica(int numeroLinea, int numeroColumna, String lexema, String tipoError){
        super("Error en la linea " + numeroLinea + " columna " + numeroColumna + " se encontro " + lexema + " " + tipoError);
    }
}
