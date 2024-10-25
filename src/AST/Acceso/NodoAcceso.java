package AST.Acceso;

import AST.Expresiones.NodoOperando;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;
import AST.Encadenado.*;


public abstract class NodoAcceso extends NodoOperando {
    protected Encadenado encadenado;
    protected boolean esAsignable;
    private boolean esLadoIzquierdo;

    public NodoAcceso(Token token){
        super(token);
        this.esAsignable = true;
        this.esLadoIzquierdo = false;
    }
    public Encadenado getEncadenado(){
        return encadenado;
    }
    public void setEncadenado(Encadenado encadenado){
        this.encadenado = encadenado;
    }
    public Encadenado obtenerEncadenado(){
        return encadenado;
    }
    public void setNoEsAsignable(){
        esAsignable = false;
    }

}
