package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;

public class NodoVar extends NodoSentencia {
    public NodoVar(Token token) {
        super(token);
    }

    @Override
    public void chequear() throws ExcepcionSemantica {

    }
}
