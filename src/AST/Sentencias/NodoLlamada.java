package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoLlamada extends NodoSentencia {
    public NodoLlamada(Token token) {
        super(token);
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
