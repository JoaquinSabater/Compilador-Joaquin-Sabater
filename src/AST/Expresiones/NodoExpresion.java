package AST.Expresiones;


import AnalizadorLexico.*;
import AnalizadorSemantico.*;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;


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

    public abstract void generar(GeneradorDeCodigoFuente gcf) throws IOException;
}
