package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;

public class NodoLlamada extends NodoSentencia {
    public NodoLlamada(Token token) {
        super(token);
    }

    @Override
    public void chequear() throws ExcepcionSemantica {

    }
}
