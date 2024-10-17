package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;

public class NodoAsignacion extends NodoSentencia {
    public NodoAsignacion(Token token) {
        super(token);
    }

    @Override
    public void chequear() throws ExcepcionSemantica {

    }
}
