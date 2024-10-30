package AST.Acceso;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AccesoMetodoEstatico extends NodoAcceso {

    protected ArrayList<NodoExpresion> listaExpresiones;

    Token nombre;

    Clase claseContenedora;

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

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        claseContenedora = ts.getClaseActual();
        Metodo metodo = claseContenedora.getMetodo(token.getLexema());
        if (metodo != null) {
            if (!metodo.getEsStatic()) {
                throw new ExcepcionSemantica(token, "El método " + token.getLexema() + " no es estático");
            }
            HashMap<String, Parametro> parametros = metodo.getParametros();
            if (parametros.size() != listaExpresiones.size()) {
                throw new ExcepcionSemantica(token, "Cantidad de parámetros no coincide con el método");
            }
            int i = 0;
            for (Parametro parametro : parametros.values()) {
                NodoExpresion expresion = listaExpresiones.get(i);
                Tipo tipoExpresion = expresion.chequear();
                if (!parametro.getTipo().equals(tipoExpresion)) {
                    throw new ExcepcionSemantica(token, "Tipo de parámetro no coincide con el método");
                }
                i++;
            }
        } else {
            throw new ExcepcionSemantica(token, "Método no encontrado");
        }

        if(encadenado == null){
            return metodo.getTipo();
        } else {
            if(metodo.getTipo().esTipoPrimitivo()){
                throw new ExcepcionSemantica(token, "No se puede encadenar un método con un tipo primitivo");
            } else {
                return encadenado.chequear(metodo.getTipo());
            }
        }
    }

    public boolean esAsignable(){
        if(this.encadenado == null){
            return false;
        } else {
            return this.encadenado.esAsignable();
        }
    }
    public boolean esInvocable(){
        if(this.encadenado == null){
            return true;
        } else {
            return this.encadenado.esInvocable();
        }
    }

}
