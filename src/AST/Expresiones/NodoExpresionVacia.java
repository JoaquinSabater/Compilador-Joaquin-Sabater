package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoExpresionVacia extends NodoExpresion{
    public NodoExpresionVacia(Token token) {
        super(token);
    }

    public boolean esAsignable(){return false;}

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
