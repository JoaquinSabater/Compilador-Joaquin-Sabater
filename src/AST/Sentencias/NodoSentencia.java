package AST.Sentencias;

import AnalizadorLexico.*;
import AnalizadorSemantico.*;


public abstract class NodoSentencia {
    protected Token token;

    public NodoSentencia(Token token){
        this.token = token;
    }

    public abstract Tipo chequear() throws ExcepcionSemantica;

    public Token getToken(){
        return token;
    }
}
