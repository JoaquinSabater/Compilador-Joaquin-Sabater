package AST.Encadenado;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public abstract class Encadenado {
    protected Token token;
    protected Encadenado encadenado;
    protected boolean esAsignable;
    protected boolean esLadoIzquierdo;

    TS ts;

    public Encadenado(Token token, TS ts){
        this.token = token;
        this.ts = ts;
        esLadoIzquierdo = false;
    }
    public Encadenado getEncadenado(){
        return encadenado;
    }
    public void setEncadenado(Encadenado encadenado){
        this.encadenado = encadenado;
    }
    public boolean esLadoIzquierdo(){
        return esLadoIzquierdo;
    }
    public abstract boolean esAsignable();

    public abstract boolean esInvocable();

    public void setComoLadoIzquierdo(){
        if(this.encadenado != null)
            encadenado.setComoLadoIzquierdo();
        else
            esLadoIzquierdo = true;
    }

    public abstract Tipo chequear(Tipo tipoLadoIzquierdo) throws ExcepcionSemantica;

    public abstract void generar(GeneradorDeCodigoFuente gcf) throws IOException;
}
