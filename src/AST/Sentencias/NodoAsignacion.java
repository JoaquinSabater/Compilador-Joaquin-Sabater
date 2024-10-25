package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoAsignacion extends NodoSentencia {
    public NodoAsignacion(Token token) {
        super(token);
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
