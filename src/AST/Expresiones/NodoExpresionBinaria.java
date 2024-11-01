package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoPrimitivo;

public class NodoExpresionBinaria extends NodoExpresion{
    private NodoExpresion ladoIzquierdo;
    private NodoExpresion ladoDerecho;
    private Token operador;

    TS ts;

    public NodoExpresionBinaria(NodoExpresion ladoIzquierdo, NodoExpresion ladoDerecho, Token operador, TS ts) {
        super(operador);
        this.ladoIzquierdo = ladoIzquierdo;
        this.ladoDerecho = ladoDerecho;
        this.operador = operador;
        this.ts = ts;
    }

    public NodoExpresion getLadoIzquierdo() {
        return ladoIzquierdo;
    }

    public NodoExpresion getLadoDerecho() {
        return ladoDerecho;
    }

    public Token getOperador() {
        return operador;
    }

    public boolean esAsignable(){return false;}

    @Override
    public boolean esInvocable() {
        return false;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        //Falta la comparacion comatibilidad de los tipos
        Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();
        Tipo tipoLadoDerecho = ladoDerecho.chequear();
        if (tipoLadoDerecho.esCompatibleOperador(operador) && tipoLadoIzquierdo.esCompatibleOperador(operador)) {
            if(tipoLadoIzquierdo.esCompatibleTipo(tipoLadoDerecho,ts) || tipoLadoDerecho.esCompatibleTipo(tipoLadoIzquierdo,ts)){
                Tipo toReturn = new TipoPrimitivo();
                toReturn.setNombreDelTipo(operador);
                return toReturn;
            }
            else {
                throw new ExcepcionSemantica(operador, ": Los tipos de los operandos no son compatibles con el operador " + operador.getLexema());
            }
        } else {
            throw new ExcepcionSemantica(operador, ": Los tipos de los operandos no son compatibles con el operador " + operador.getLexema());
        }
    }
}
