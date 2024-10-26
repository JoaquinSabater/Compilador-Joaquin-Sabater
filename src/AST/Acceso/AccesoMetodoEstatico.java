package AST.Acceso;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Metodo;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;

import java.util.ArrayList;

public class AccesoMetodoEstatico extends NodoAcceso {

    protected ArrayList<NodoExpresion> listaExpresiones;

    Token nombre;

    Token metodo;
    public AccesoMetodoEstatico(Token nombre, Token metodo, ArrayList<NodoExpresion> listaExpresiones, TS ts) {
        super(nombre,ts);
        this.listaExpresiones = listaExpresiones;
        this.nombre = nombre;
        this.metodo = metodo;
    }

    public ArrayList<NodoExpresion> getListaExpresiones() {
        return listaExpresiones;
    }

    public void setListaExpresiones(ArrayList<NodoExpresion> listaExpresiones) {
        this.listaExpresiones = listaExpresiones;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }

    public boolean esAsignable(){
        return false;
    }
    public boolean esInvocable(){
        return true;
    }

}
