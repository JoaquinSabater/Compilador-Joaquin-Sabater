package AST.Acceso;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AccesoMetodo extends NodoAcceso {

    ArrayList<NodoExpresion> listaExpresiones;

    Clase claseContenedora;

    public AccesoMetodo(Token token, ArrayList<NodoExpresion> listaExpresiones, TS ts, Clase claseContenedora) {
        super(token,ts);
        this.listaExpresiones = listaExpresiones;
        this.claseContenedora = claseContenedora;
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

    public Tipo chequear() throws ExcepcionSemantica {
        if (claseContenedora == null) {
            throw new ExcepcionSemantica(token, "Clase contenedora no definida");
        } else {
            Metodo metodo = claseContenedora.getMetodo(token.getLexema());
            if (metodo != null) {
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
            return metodo.getTipo();
        }
    }
}