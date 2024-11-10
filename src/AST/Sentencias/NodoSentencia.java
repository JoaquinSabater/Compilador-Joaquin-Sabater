package AST.Sentencias;

import AnalizadorLexico.*;
import AnalizadorSemantico.*;

import java.io.IOException;

import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;


public abstract class NodoSentencia {
    protected Token token;

    public NodoSentencia(Token token){
        this.token = token;
    }

    public abstract void chequear() throws ExcepcionSemantica;

    public Token getToken(){
        return token;
    }

    public abstract void generar(GeneradorDeCodigoFuente gcf) throws IOException;
}
