package AST.Acceso;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Atributo;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Parametro;
import AnalizadorSemantico.Tipo;

public class NodoAccesoVar extends NodoAcceso {

    private Atributo atributo;
    private Parametro parametro;
    public NodoAccesoVar(Token token) {
        super(token);
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
