package AST.Acceso;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;
import AST.Expresiones.NodoExpresion;

import java.beans.Expression;

public class NodoExpresionParametrizada extends NodoAcceso{

    NodoExpresion nodoExpresion;

    public NodoExpresionParametrizada(Token token, NodoExpresion nodoExpresion, TS ts) {
        super(token,ts);
        this.nodoExpresion = nodoExpresion;
    }

    public NodoExpresion getNodoExpresion(){
        return nodoExpresion;
    }

    public void setNodoExpresion(NodoExpresion nodoExpresion){
        this.nodoExpresion = nodoExpresion;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }


}
