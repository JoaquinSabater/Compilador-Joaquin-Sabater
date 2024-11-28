package AST.Acceso;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.*;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AccesoMetodo extends NodoAcceso {

    ArrayList<NodoExpresion> listaExpresiones;

    Clase claseContenedora;


    public AccesoMetodo(Token token, ArrayList<NodoExpresion> listaExpresiones, TS ts) {
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
        claseContenedora = ts.getClaseActual();
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
                //if (!tipoExpresion.esCompatibleTipo(parametro.getTipo(), ts)) {
                    //throw new ExcepcionSemantica(token, "Tipo de parámetro no coincide con el método");
                //}
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

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        claseContenedora = ts.getClaseActual();
        Metodo metodo = claseContenedora.getMetodo(token.getLexema());
        if (metodo.getEsStatic()){
            generarCodigoMetodoStatic(gcf,metodo);
        }else {
            generarCodigoMetodoNoStatic(gcf,metodo);
        }
    }

    private void generarCodigoMetodoNoStatic(GeneradorDeCodigoFuente gcf,Metodo metodo) throws IOException {
        //haces load 3 cuando sos el priemro del encadenado
        //gcf.agregarInstruccion("LOAD 3; AccesoMetodo Cargar la dirección de la instancia");
        if (!metodo.isEsVoid()){
            gcf.agregarInstruccion("RMEM 1  ; Guardo lugar para el retorno");
            gcf.agregarInstruccion("SWAP");
        }
        for (NodoExpresion expresion : listaExpresiones) {
            expresion.generar(gcf);
            gcf.agregarInstruccion("SWAP");
        }
        gcf.agregarInstruccion("DUP");
        gcf.agregarInstruccion("LOADREF 0; Apilo el offset de la VT en la CIR (Siempre es 0)");
        gcf.agregarInstruccion("LOADREF "+metodo.getOffset()+"; Apilo el offset del metdo mc en la VT");
        gcf.agregarInstruccion("CALL    ; Llamar al método " + token.getLexema());
    }

    private void generarCodigoMetodoStatic(GeneradorDeCodigoFuente gcf,Metodo metodo) throws IOException {
        if (!metodo.isEsVoid()){
            gcf.agregarInstruccion("RMEM 1  ; Guardo lugar para el retorno");
        }
        for (NodoExpresion expresion : listaExpresiones) {
            expresion.generar(gcf);
        }
        gcf.agregarInstruccion("PUSH "+token.getLexema()+"; Apilar la dirección del método " + token.getLexema());
        gcf.agregarInstruccion("CALL    ; Llamar al método " + token.getLexema());
    }
}