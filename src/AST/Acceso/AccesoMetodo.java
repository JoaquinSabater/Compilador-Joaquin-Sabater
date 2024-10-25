package AST.Acceso;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

import java.util.ArrayList;

public class AccesoMetodo extends NodoAcceso {

    ArrayList<NodoExpresion> listaExpresiones;

    public AccesoMetodo(Token token, ArrayList<NodoExpresion> listaExpresiones) {
        super(token);
        this.listaExpresiones = listaExpresiones;
    }

    public ArrayList<NodoExpresion> getListaExpresiones() {
        return listaExpresiones;
    }

    public void setListaExpresiones(ArrayList<NodoExpresion> listaExpresiones) {
        this.listaExpresiones = listaExpresiones;
    }

    public boolean esAsignable(){
        return this.encadenado!=null;
    }
    public boolean esInvocable(){
        return true;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}