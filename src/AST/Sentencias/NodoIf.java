package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AST.*;

public class NodoIf extends NodoSentencia {

    private NodoExpresion condicion;
    private NodoSentencia sentencia;
    private NodoSentencia sentenciaElse;

    public NodoIf(Token token) {
        super(token);
    }

    @Override
    public void chequear() throws ExcepcionSemantica {

    }

    public void setCondicion(NodoExpresion condicion) {
        this.condicion = condicion;
    }

    public void setSentencia(NodoSentencia sentencia) {
        this.sentencia = sentencia;
    }

    public void setSentenciaElse(NodoSentencia sentenciaElse) {
        this.sentenciaElse = sentenciaElse;
    }

    public NodoExpresion getCondicion() {
        return condicion;
    }

    public NodoSentencia getSentencia() {
        return sentencia;
    }

    public NodoSentencia getSentenciaElse() {
        return sentenciaElse;
    }
}
