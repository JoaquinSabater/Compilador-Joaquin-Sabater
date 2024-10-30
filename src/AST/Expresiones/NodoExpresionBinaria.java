package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoPrimitivo;

public class NodoExpresionBinaria extends NodoExpresion{
    private NodoExpresion ladoIzquierdo;
    private NodoExpresion ladoDerecho;
    private Token operador;

    public NodoExpresionBinaria(NodoExpresion ladoIzquierdo, NodoExpresion ladoDerecho, Token operador) {
        super(operador);
        this.ladoIzquierdo = ladoIzquierdo;
        this.ladoDerecho = ladoDerecho;
        this.operador = operador;
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

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        //Falta la comparacion comatibilidad de los tipos
        Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();
        Tipo tipoLadoDerecho = ladoDerecho.chequear();
        if (tipoLadoDerecho.esCompatibleOperador(operador) && tipoLadoIzquierdo.esCompatibleOperador(operador)) {
            Tipo toReturn = new TipoPrimitivo();
            toReturn.setNombreDelTipo(operador);
            return toReturn;
        } else {
            throw new ExcepcionSemantica(operador, ": Los tipos de los operandos no son compatibles con el operador " + operador.getLexema());
        }
    }
}
