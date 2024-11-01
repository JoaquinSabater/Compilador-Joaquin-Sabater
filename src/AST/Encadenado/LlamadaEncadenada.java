package AST.Encadenado;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LlamadaEncadenada extends Encadenado{
    private ArrayList<NodoExpresion> listaExpresiones;

    TS ts;

    public LlamadaEncadenada(Token token,TS ts ,ArrayList<NodoExpresion> listaExpresiones){
        super(token,ts);
        this.listaExpresiones = listaExpresiones;
        this.ts = ts;
    }


    @Override
    public Tipo chequear(Tipo tipoLadoIzquierdo) throws ExcepcionSemantica {
        Clase claseActual = ts.getClase(tipoLadoIzquierdo.getNombreClase().getLexema());

        Tipo toReturn;

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

        toReturn = metodo.getTipo();

        if(this.encadenado != null){
            if(toReturn.esTipoPrimitivo()) //Porque el encadenado sigue
                throw new ExcepcionSemantica(token, "el tipo de retorno del metodo "+this.token.getLexema()+" no debe ser tipo primitivo");
            return this.encadenado.chequear(toReturn);
        }

        return toReturn;
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
