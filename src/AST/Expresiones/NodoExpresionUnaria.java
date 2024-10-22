package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoExpresionUnaria extends NodoExpresion{
    public NodoExpresionUnaria(Token token) {
        super(token);
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
