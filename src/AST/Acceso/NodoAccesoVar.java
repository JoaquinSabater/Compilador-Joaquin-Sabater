package AST.Acceso;

import AST.Sentencias.NodoBloque;
import AnalizadorLexico.Token;
import AnalizadorSemantico.*;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoAccesoVar extends NodoAcceso {

    private Atributo atributo;
    private Parametro parametro;
    public NodoAccesoVar(Token token, TS ts) {
        super(token,ts);
        this.esAsignable = true;
    }

    public boolean esAsignable() {
        if(this.encadenado == null){
            return true;
        } else {
            return this.encadenado.esAsignable();
        }
    }

    public boolean esInvocable() {
        if(this.encadenado == null){
            return false;
        } else {
            return this.encadenado.esAsignable();
        }
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
        } else if (bloqueActual.esVariableDeclaradaEnBloqueOEnPadre(this.token.getLexema())) {
            toReturn = new TipoClase();
            toReturn.setNombreClase(new Token("pr_var", "pr_var", this.token.getNro_linea()));
        } else{
            throw new ExcepcionSemantica(this.token, "No se encontro el atributo o parametro " + this.token.getLexema());
        }

        if (this.encadenado != null) {
            return encadenado.chequear(toReturn);
        } else {
            return toReturn;
        }
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        if(atributo != null){
            //gcf.agregarInstruccion("LOAD 3  ; ");
            //gcf.agregarInstruccion("SWAP");
            //gcf.agregarInstruccion("STOREREF 1");
            //System.out.println("Entre aca");
        }
        //TODO falta ver que pasa si es de tipo static o no
    }
}
