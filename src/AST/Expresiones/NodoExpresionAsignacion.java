package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoExpresionAsignacion extends NodoExpresion {

    protected NodoExpresion ladoIzquierdo;
    protected NodoExpresion ladoDerecho;

    TS ts;
    public NodoExpresionAsignacion(NodoExpresion ladoIzquierdo, Token operadorExpresion , NodoExpresion ladoDerecho, TS ts){
        super(operadorExpresion);
        this.ladoIzquierdo = ladoIzquierdo;
        this.ladoDerecho = ladoDerecho;
        this.ts = ts;
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

    public boolean esAsignable(){return false;}

    @Override
    public boolean esInvocable() {
        return false;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();
        //Sete
        Tipo tipoLadoDerecho = ladoDerecho.chequear();

        if (!tipoLadoDerecho.esCompatibleTipo(tipoLadoIzquierdo, ts)) {
            throw new ExcepcionSemantica(token, "Tipos incompatibles en la asignacion: " + tipoLadoDerecho.getNombreClase().getLexema() + " no conforma con " + tipoLadoIzquierdo.getNombreClase().getLexema());
        }

        Tipo toReturn = null;
        if (ladoIzquierdo != null) {
            toReturn = tipoLadoIzquierdo;
            if (ladoDerecho != null) {
                toReturn = tipoLadoDerecho;
            }
        }

        if(ladoIzquierdo.esAsignable()){
            return toReturn;
        }
        else {
            throw new ExcepcionSemantica(token, "El lado izquierdo de la asignacion no es asignable");
        }
        //Fijarme que el lado izquierdo sea asiganble
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        if(ladoDerecho != null){
            ladoDerecho.generar(gcf);
        }
        ladoIzquierdo.setComoLadoIzquierdo();
        if(ladoIzquierdo != null){
            ladoIzquierdo.generar(gcf);
        }
    }
}
