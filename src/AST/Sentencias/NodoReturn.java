package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Metodo;
import AnalizadorSemantico.Tipo;

public class NodoReturn extends NodoSentencia {

    Metodo metodoPadre;

    NodoExpresion expresion;

    Token token;

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
        Tipo tipoMetodo = metodoPadre.getTipo();
        if (expresion != null) {
            Tipo tipoExpresion = expresion.chequear();
            if (!tipoMetodo.esCompatibleTipo(tipoExpresion)) {
                throw new ExcepcionSemantica(token, "El tipo de la expresion de retorno no es compatible con el tipo de retorno del metodo");
            }
        }
        //Donde controlo si el tipo si un metodo con un tipo no tiene un return ?
    }

}
