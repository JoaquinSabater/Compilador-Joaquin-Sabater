package AST.Acceso;

import AST.Sentencias.NodoBloque;
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
        Tipo toReturn = null;
        Clase claseActual = ts.getClaseActual();
        Metodo metodoActual = ts.getMetodoActual();
        NodoBloque bloqueActual = metodoActual.getBloqueContenedor();

        if(claseActual.getAtributo(this.token.getLexema()) != null){
            this.atributo = claseActual.getAtributo(this.token.getLexema());
            toReturn = this.atributo.getTipo();
        }
        else if(metodoActual.getParametro(this.token.getLexema()) != null){
            this.parametro = metodoActual.getParametro(this.token.getLexema());
            toReturn = this.parametro.getTipo();
        } else{
            throw new ExcepcionSemantica(this.token, "No se encontro el atributo o parametro " + this.token.getLexema());
        }

        if (this.encadenado != null) {
            return encadenado.chequear(toReturn);
        } else {
            return toReturn;
        }
    }
}
