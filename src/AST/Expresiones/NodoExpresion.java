package AST.Expresiones;


import AnalizadorLexico.*;
import AnalizadorSemantico.*;


public abstract class NodoExpresion {
    protected Token token;
    public NodoExpresion(Token token){
        this.token = token;
    }
    public Token obtenerToken(){
        return this.token;
    }

    public abstract boolean esAsignable();

    public abstract boolean esInvocable();

    public abstract Tipo chequear() throws ExcepcionSemantica;
}
