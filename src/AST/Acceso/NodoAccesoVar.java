package AST.Acceso;

import AnalizadorLexico.Token;
import AnalizadorSemantico.*;

public class NodoAccesoVar extends NodoAcceso {

    private Atributo atributo;
    private Parametro parametro;
    public NodoAccesoVar(Token token, TS ts) {
        super(token,ts);
        this.esAsignable = true;
    }

    public boolean esAsignable() {
        return this.encadenado != null;
    }

    public boolean esInvocable() {
        return false;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
