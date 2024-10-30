package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AST.Expresiones.NodoExpresionAsignacion;
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
        if (!(expresion instanceof NodoExpresionAsignacion)){
            if (!expresion.esInvocable()){
                throw new ExcepcionSemantica(expresion.obtenerToken(), "La expresion no es invocable");
            }
        }
    }
}
