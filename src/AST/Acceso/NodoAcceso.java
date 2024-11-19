package AST.Acceso;

import AST.Expresiones.NodoOperando;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;
import AST.Encadenado.*;


public abstract class NodoAcceso extends NodoOperando {
    protected Encadenado encadenado;
    
    protected boolean esAsignable;

    TS ts;

    public NodoAcceso(Token token,TS ts){
        super(token);
        this.ts = ts;
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
