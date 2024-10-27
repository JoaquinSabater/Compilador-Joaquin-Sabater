package AST.Acceso;

import AnalizadorLexico.Token;
import AnalizadorSemantico.*;

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
        Clase claseActual = ts.getClaseActual();
        if (claseActual == null) {
            throw new ExcepcionSemantica(token, "Clase actual no definida");
        }
        TipoClase tipoClaseActual = new TipoClase();
        tipoClaseActual.setNombreClase(claseActual.getNombre());

        if (this.encadenado != null) {
            return encadenado.chequear(tipoClaseActual);
        } else {
            return tipoClaseActual;
        }
    }
}
