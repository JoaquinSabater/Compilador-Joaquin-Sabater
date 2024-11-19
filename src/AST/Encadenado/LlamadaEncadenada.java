package AST.Encadenado;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.*;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LlamadaEncadenada extends Encadenado{
    private ArrayList<NodoExpresion> listaExpresiones;

    Metodo metodo;


    public LlamadaEncadenada(Token token,TS ts ,ArrayList<NodoExpresion> listaExpresiones){
        super(token,ts);
        this.listaExpresiones = listaExpresiones;
    }


    @Override
    public Tipo chequear(Tipo tipoLadoIzquierdo) throws ExcepcionSemantica {
        if(tipoLadoIzquierdo.getNombreClase().getToken_id().equals("pr_var")){
            Tipo TipoVar = TS.getTipo(tipoLadoIzquierdo.getNombreClase().getLexema());
            tipoLadoIzquierdo = TipoVar;
        }
        Clase claseActual = ts.getClase(tipoLadoIzquierdo.getNombreClase().getLexema());

        Tipo toReturn;

        metodo = claseActual.getMetodo(token.getLexema());
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
            if (!tipoExpresion.esCompatibleTipo(parametro.getTipo(), ts)) {
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

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        if(metodo.getEsStatic()){
            generarCodigoStatic(gcf);
        }else {
            generarCodigoNoStatic(gcf);
        }
    }

    private void generarCodigoStatic(GeneradorDeCodigoFuente gcf) throws IOException {
        if (!metodo.isEsVoid()){
            gcf.agregarInstruccion("RMEM 1  ; Guardo lugar para el retorno");
        }
        for (NodoExpresion expresion : listaExpresiones) {
            expresion.generar(gcf);
        }
        gcf.agregarInstruccion("PUSH " + token.getLexema() + "; Apilar la dirección del método " + token.getLexema());
        gcf.agregarInstruccion("CALL    ; Llamar al método " + token.getLexema());
    }

    private void generarCodigoNoStatic(GeneradorDeCodigoFuente gcf) throws IOException {
        //gcf.agregarInstruccion("LOAD 3; LlamdaEncadenada Cargar la dirección de la instancia"); //TODO esto es temporal el offset se tiene que calcular
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
        gcf.agregarInstruccion("LOADREF "+metodo.getOffset()+"; Apilo el offset del metdo mc en la VT"); //TODO esto tambien es temporal
        gcf.agregarInstruccion("CALL    ; Llamar al método " + token.getLexema());
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
