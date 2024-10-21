package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Metodo;

public class NodoReturn extends NodoSentencia {

    Metodo metodoPadre;

    NodoExpresion expresion;

    public NodoReturn(Token token) {
        super(token);
    }

    public void setExpresion(NodoExpresion expresion) {
        this.expresion = expresion;
    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    public void setMetodoPadre(Metodo metodoPadre) {
        this.metodoPadre = metodoPadre;
    }

    public Metodo getMetodoPadre() {
        return metodoPadre;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        //Voy a tener que fijarme si el metodo padre tiene un tipo de retorno
    }
}
