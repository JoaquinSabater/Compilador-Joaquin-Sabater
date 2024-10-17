package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;

public class NodoSwitch extends NodoSentencia {
    public NodoSwitch(Token token) {
        super(token);
    }

    @Override
    public void chequear() throws ExcepcionSemantica {

    }
}
