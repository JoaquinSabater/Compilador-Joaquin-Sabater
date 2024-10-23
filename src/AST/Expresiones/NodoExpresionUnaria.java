package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoExpresionUnaria extends NodoExpresion{

    private NodoExpresion operando;

    public NodoExpresionUnaria(Token token, NodoExpresion operando) {
        super(token);
        this.operando = operando;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
