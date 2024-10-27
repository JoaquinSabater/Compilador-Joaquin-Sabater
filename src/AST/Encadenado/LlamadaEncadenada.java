package AST.Encadenado;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LlamadaEncadenada extends Encadenado{
    private ArrayList<NodoExpresion> listaExpresiones;
    private Metodo metodo;

    public LlamadaEncadenada(Token token,TS ts ,ArrayList<NodoExpresion> listaExpresiones){
        super(token,ts);
        this.listaExpresiones = listaExpresiones;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        Clase claseActual = ts.getClaseActual();

        Metodo metodo = claseActual.getMetodo(token.getLexema());
        if (metodo == null) {
            throw new ExcepcionSemantica(token, "Método " + token.getLexema() + " no encontrado en la clase " + claseActual.getNombre());
        }

        HashMap<String, Parametro> parametros = metodo.getParametros();
        if (parametros.size() != listaExpresiones.size()) {
            throw new ExcepcionSemantica(token, "Cantidad de parámetros no coincide con el método " + token.getLexema());
        }

        int i = 0;
        for (Parametro parametro : parametros.values()) {
            NodoExpresion expresion = listaExpresiones.get(i);
            Tipo tipoExpresion = expresion.chequear();
            if (!parametro.getTipo().equals(tipoExpresion)) {
                throw new ExcepcionSemantica(token, "Tipo de parámetro no coincide con el método " + token.getLexema());
            }
            i++;
        }

        return metodo.getTipo();
    }

    public boolean esAsignable(){
        return false;
    }
    public boolean esInvocable(){
        return true;
    }
}
