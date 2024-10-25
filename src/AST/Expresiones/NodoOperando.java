package AST.Expresiones;

import AnalizadorLexico.Token;

public abstract class NodoOperando extends NodoExpresion {
    public NodoOperando(Token token){
        super(token);
    }
}
