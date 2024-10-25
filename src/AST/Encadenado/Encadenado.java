package AST.Encadenado;

import AnalizadorLexico.Token;

public abstract class Encadenado {
    protected Token token;
    protected Encadenado encadenado;
    protected boolean esAsignable;
    protected boolean esLadoIzquierdo;
    public Encadenado(Token token){
        this.token = token;
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
    public boolean esAsignable(){
        return esAsignable;
    }
    public void setComoLadoIzquierdo(){
        if(this.encadenado != null)
            encadenado.setComoLadoIzquierdo();
        else
            esLadoIzquierdo = true;
    }
}
