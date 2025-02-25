package AST.Acceso;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.*;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;
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

    public Tipo chequear() throws ExcepcionSemantica {
        Tipo toReturn = null;
        if(encadenado == null){
            if (ts.getClase(token.getLexema()) == null) {
                throw new ExcepcionSemantica(token, "La clase " + token.getLexema() + " no existe");
            } else {
                Clase clase = ts.getClase(token.getLexema());
                Metodo constructor = clase.getConstructor();
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
                toReturn = new TipoClase();
                toReturn.setNombreClase(token);
                return toReturn;
            }
        }else {
            if(ts.getClase(token.getLexema()) != null) {
                Tipo toSend = new TipoClase();
                toSend.setNombreClase(token);
                return this.encadenado.chequear(toSend);
            }else {
                throw new ExcepcionSemantica(this.token, this.token.getLexema() + " no es una clase concreta declarada");
            }
        }
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        Clase clase = ts.getClase(token.getLexema());
        int CIR = clase.getTamanioCIR();
        gcf.agregarInstruccion("RMEM 1  ; Reservamos memoria para el resultado del malloc");
        gcf.agregarInstruccion("PUSH "+ CIR +"; Apilo la cantidad de var de instancia del CIR de A +1 por VT");
        gcf.agregarInstruccion("PUSH simple_malloc  ; La dirección de la rutina para alojar memoria en el heap");
        gcf.agregarInstruccion("CALL    ; Llamo a malloc");
        gcf.agregarInstruccion("DUP ; Para no perder la referencia al nuevo CIR");
        gcf.agregarInstruccion("PUSH lblVT"+clase.getNombre().getLexema()+" ; Apilamos la dirección del comienzo de la VT de la clase A");
        gcf.agregarInstruccion("STOREREF 0 ; Guardamos la Referencia a la VT en el CIR que creamos");
        gcf.agregarInstruccion("DUP");
        if(listaExpresiones != null) {
            for (NodoExpresion expresion : listaExpresiones) {
                expresion.generar(gcf);
                gcf.agregarInstruccion("SWAP    ;");
            }
        }
        gcf.agregarInstruccion("PUSH lbl"+clase.getNombre().getLexema()+"_constructor ; Apilamos la dirección de la rutina del constructor");
        gcf.agregarInstruccion("CALL    ; LLamo al constructor");
        if(encadenado != null){
            encadenado.generar(gcf);
        }
    }
}
