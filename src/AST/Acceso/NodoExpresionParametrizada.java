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
    public boolean esAsignable() {
        if(this.encadenado == null){
            return false;
        } else {
            return this.encadenado.esAsignable();
        }
    }

    @Override
    public boolean esInvocable() {
        if(this.encadenado == null){
            return false;
        } else {
            return this.encadenado.esInvocable();
        }
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        Tipo tipoExpresion = this.nodoExpresion.chequear();
        if(this.encadenado != null){
            if(tipoExpresion.esTipoPrimitivo()){
                throw new ExcepcionSemantica(this.nodoExpresion.obtenerToken(), "El lado izquierdo del encadenado es un tipo primitivo");
            }
            return this.encadenado.chequear(tipoExpresion);
        }
        return tipoExpresion;
    }
}
