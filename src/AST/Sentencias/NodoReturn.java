package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Metodo;

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
        if (expresion != null) {
            expresion.chequear();
            if (!expresion.getTipo().esCompatible(metodoPadre.getTipoRetorno())) {
                throw new ExcepcionSemantica("La expresion de retorno no es compatible con el tipo de retorno del metodo", getToken());
            }
        } else {
            if (!metodoPadre.getTipoRetorno().esVoid()) {
                throw new ExcepcionSemantica("El metodo debe retornar un valor", getToken());
            }
        }
    }
}
