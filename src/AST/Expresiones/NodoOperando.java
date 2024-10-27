package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public abstract class NodoOperando extends NodoExpresion {
    public NodoOperando(Token token){
        super(token);
    }

    public abstract Tipo chequear() throws ExcepcionSemantica;
}
