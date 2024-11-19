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
            toReturn.setNombreClase(new Token("pr_var", token.getLexema(), this.token.getNro_linea()));
        } else if (bloqueActual.esVariableDeclaradaEnEsteBloque(this.token.getLexema())) {
            toReturn = new TipoClase();
            toReturn.setNombreClase(new Token("pr_var", token.getLexema(), this.token.getNro_linea()));
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
            gcf.agregarInstruccion("LOAD 3  ; Agrego el this");
            if(encadenado != null){
                gcf.agregarInstruccion("LOADREF "+atributo.getOffset()+" ; Cargo el atributo "+atributo.getNombre());
            }else {
                gcf.agregarInstruccion("SWAP");
                gcf.agregarInstruccion("STOREREF "+atributo.getOffset()+" ; Guardo el atributo "+atributo.getNombre());
            }
        }
        if(parametro != null){
            if(encadenado != null){
                gcf.agregarInstruccion("LOAD "+parametro.getOffset()+" ; Cargo el parametro "+parametro.getNombre());
            }else {
                gcf.agregarInstruccion("STORE "+parametro.getOffset()+" ; Guardo el parametro "+parametro.getNombre());
            }
        }
        //if (parametro != null){
            //gcf.agregarInstruccion("LOAD "+parametro.getOffset()+"; Cargo el parametro "+parametro.getNombre());
        //}
        //if (atributo != null){
            //gcf.agregarInstruccion("LOAD "+atributo.getOffset()+" ; Cargo el atributo "+atributo.getNombre());
        //}
        if (this.encadenado != null) {
            this.encadenado.generar(gcf);
        }
    }
}
