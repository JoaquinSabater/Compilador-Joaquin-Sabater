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

    public boolean esAsignable(){return false;}

    @Override
    public boolean esInvocable() {
        return false;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        Tipo tipoOperando = operando.chequear();
        if(tipoOperando.esCompatibleOperador(token))
            return tipoOperando;
        else
            throw new ExcepcionSemantica(token,"El operador " + token.getLexema() + " no es compatible con el tipo " + tipoOperando.getNombreClase().getLexema());
    }
}
