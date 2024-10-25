package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoIf extends NodoSentencia {

    private NodoExpresion condicion;
    private NodoSentencia sentencia;
    private NodoSentencia sentenciaElse;

    public NodoIf(Token token) {
        super(token);
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        Tipo tipoCondicion = condicion.chequear();
        if (!tipoCondicion.getNombreClase().getLexema().equals("boolean")) {
            throw new ExcepcionSemantica(token, "La condicion del if debe ser de tipo boolean");
        }
        sentencia.chequear();
        if (sentenciaElse != null) {
            sentenciaElse.chequear();
        }
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
