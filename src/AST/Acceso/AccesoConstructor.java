package AST.Acceso;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AccesoConstructor extends NodoAcceso {
    ArrayList<NodoExpresion> listaExpresiones;
    public AccesoConstructor(Token token, ArrayList<NodoExpresion> listaExpresiones, TS ts) {
        super(token,ts);
        this.listaExpresiones = listaExpresiones;
    }

    public ArrayList<NodoExpresion> getListaExpresiones() {
        return listaExpresiones;
    }

    public void setListaExpresiones(ArrayList<NodoExpresion> listaExpresiones) {
        this.listaExpresiones = listaExpresiones;
    }

    public boolean esAsignable(){
        return false;
    }
    public boolean esInvocable(){
        return true;
    }

    public Tipo chequear() throws ExcepcionSemantica {
        if (ts.getClase(token.getLexema()) == null) {
            throw new ExcepcionSemantica(token, "La clase " + token.getLexema() + " no existe");
        } else {
            Clase clase = ts.getClase(token.getLexema());
            Metodo constructor = clase.getMetodo(token.getLexema());
            if (constructor != null) {
                HashMap<String, Parametro> parametros = constructor.getParametros();
                if (parametros.size() != listaExpresiones.size()) {
                    throw new ExcepcionSemantica(token, "Cantidad de parámetros no coincide con el constructor");
                }
                int i = 0;
                for (Parametro parametro : parametros.values()) {
                    NodoExpresion expresion = listaExpresiones.get(i);
                    Tipo tipoExpresion = expresion.chequear();
                    if (!parametro.getTipo().equals(tipoExpresion)) {
                        throw new ExcepcionSemantica(token, "Tipo de parámetro no coincide con el constructor");
                    }
                    i++;
                }
            } else {
                throw new ExcepcionSemantica(token, "Constructor no encontrado");
            }
            Tipo toReturn = new TipoClase();
            toReturn.setNombreClase(token);
            return toReturn;
        }
    }
}
