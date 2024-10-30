package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;

public class NodoAsignacionLlamada extends NodoSentencia {

    NodoExpresion expresion;

    public NodoAsignacionLlamada(Token token) {
        super(token);
    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoExpresion expresion) {
        this.expresion = expresion;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        expresion.chequear();
        //Me tengo que asegurar de que si es una asigancion el lado izquierdo es asignable y si no es una asigancion tengo que controlar que
        //sea algo llamabale
    }
}
