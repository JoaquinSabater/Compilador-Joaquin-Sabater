package AST.Sentencias;

import AnalizadorLexico.*;
import AnalizadorSemantico.*;
import AST.*;
import AnalizadorSintactico.*;

public abstract class NodoSentencia {
    protected Token token;

    public NodoSentencia(Token token){
        this.token = token;
    }

    public abstract void chequear() throws ExcepcionSemantica;

    public Token getToken(){
        return token;
    }
}
