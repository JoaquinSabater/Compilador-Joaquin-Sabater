package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

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
       Tipo tipoLadoIzquierdo = ladoIzquierdo.chequear();
       Tipo tipoLadoDerecho = ladoDerecho.chequear();
       if(tipoLadoDerecho.esCompatibleOperador(operador) && tipoLadoIzquierdo.esCompatibleOperador(operador) && tipoLadoDerecho.esCompatibleTipo(tipoLadoIzquierdo)){
           return tipoLadoIzquierdo;
       }else {
           throw new ExcepcionSemantica(operador,"Error en la linea " + operador.getNro_linea() + ": Los tipos de los operandos no son compatibles con el operador " + operador.getLexema());
       }
    }
}
