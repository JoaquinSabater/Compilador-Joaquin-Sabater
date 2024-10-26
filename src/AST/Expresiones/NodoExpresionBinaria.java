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
        return null;
        //Implementar por cada operador binario y comparar
    }
}
