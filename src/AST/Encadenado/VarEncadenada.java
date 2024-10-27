package AST.Encadenado;

import AnalizadorLexico.Token;
import AnalizadorSemantico.*;

public class VarEncadenada extends Encadenado {
    public VarEncadenada(Token token, TS ts) {
        super(token, ts);
        this.esAsignable = true;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        Clase claseActual = ts.getClaseActual();

        Atributo atributo = claseActual.getAtributo(token.getLexema());
        if (atributo == null) {
            throw new ExcepcionSemantica(token, "Atributo " + token.getLexema() + " no encontrado en la clase " + claseActual.getNombre());
        }

        return atributo.getTipo();
    }

    public boolean esAsignable(){
        return true;
    }
    public boolean esInvocable(){
        return false;
    }

}
