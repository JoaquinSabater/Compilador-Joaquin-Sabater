package AST.Acceso;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;

public class NodoAccesoThis extends NodoAcceso{

    Token token;

    public NodoAccesoThis(Token token, TS ts) {
        super(token,ts);
    }

    public boolean esAsignable(){
        return this.encadenado != null;
    }
    public boolean esInvocable(){
        return false;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
