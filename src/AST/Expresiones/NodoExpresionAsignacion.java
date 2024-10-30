package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoExpresionAsignacion extends NodoExpresion {

    protected NodoExpresion ladoIzquierdo;
    protected NodoExpresion ladoDerecho;
    public NodoExpresionAsignacion(NodoExpresion ladoIzquierdo,Token operadorExpresion ,NodoExpresion ladoDerecho ){
        super(operadorExpresion);
        this.ladoIzquierdo = ladoIzquierdo;
        this.ladoDerecho = ladoDerecho;
    }
    public void setLadoIzquierdo(NodoExpresion ladoIzquierdo){
        this.ladoIzquierdo = ladoIzquierdo;
    }
    public void setLadoDerecho(NodoExpresion ladoDerecho){
        this.ladoDerecho = ladoDerecho;
    }

    public void setToken(Token token){
        this.token = token;
    }

    public NodoExpresion getLadoIzquierdo(){
        return ladoIzquierdo;
    }
    public NodoExpresion getLadoDerecho(){
        return ladoDerecho;
    }
    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();
        Tipo tipoLadoDerecho = ladoDerecho.chequear();

        System.out.println("Lado izquierdo: " + tipoLadoIzquierdo.getNombreClase().getLexema());
        System.out.println("Lado derecho: " + tipoLadoDerecho.getNombreClase().getLexema());

        if (tipoLadoDerecho.esCompatibleTipo(tipoLadoIzquierdo)) {
            throw new ExcepcionSemantica(token, "Tipos incompatibles en la asignacion: " + tipoLadoDerecho.getNombreClase().getLexema() + " no conforma con " + tipoLadoIzquierdo.getNombreClase().getLexema());
        }
        Tipo toReturn = null;
        if (ladoIzquierdo != null) {
            toReturn = ladoIzquierdo.chequear();
            if (ladoDerecho == null) {
                return toReturn;
            } else {
                toReturn = ladoDerecho.chequear();
            }
        }
        System.out.println("Lado izquierdo: " + ladoIzquierdo.chequear().getNombreClase().getLexema());
        System.out.println("Lado derecho: " + ladoDerecho.chequear().getNombreClase().getLexema());
        //Fijarme que el lado izquierdo sea asiganble
        return toReturn;
    }
}
