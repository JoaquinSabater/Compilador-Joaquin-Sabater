package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;

public class NodoBrake extends NodoSentencia {
    public NodoBrake(Token token) {
        super(token);
    }

    @Override
    public void chequear() throws ExcepcionSemantica {

    }
}
