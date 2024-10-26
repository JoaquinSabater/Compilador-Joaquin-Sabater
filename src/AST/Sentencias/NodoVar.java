package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoVar extends NodoSentencia {
    private Token idMetVar;
    private NodoExpresion expresion;

    public NodoVar(Token idMetVar, NodoExpresion expresion) {
        super(idMetVar);
        this.idMetVar = idMetVar;
        this.expresion = expresion;
    }

    public Token getIdMetVar() {
        return idMetVar;
    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        //Aca en la parte de var que tengo que chequear ?
    }
}
