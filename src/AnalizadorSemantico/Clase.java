package AnalizadorSemantico;

import AnalizadorLexico.Token;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Clase {

    private Token nombre;

    private HashMap<String, Atributo> atributos = new HashMap<>();

    private HashMap<String, Metodo> metodos = new HashMap<>();

    boolean tieneConstructor = false;

    int tamanioCIR = 0;

    private Metodo constructor;

    private Clase padre;

    private boolean estaConsolidada = false;

    TS ts;

    private int maximoOffsetMetodos = 0;

    private int maximoOffsetAtributos = 0;

    private boolean calculeElOffsetAtributos = false;

    private boolean calculeElOffsetMetodos = false;


    public Clase(Token nombre, TS ts) {
        this.nombre = nombre;
        constructor = new Metodo();
        this.ts = ts;
    }

    public void insertarConstructor(Metodo metodo) throws ExcepcionSemantica {
        if(!tieneConstructor){
            constructor = metodo;
            tieneConstructor = true;
        }
        else{
            throw new ExcepcionSemantica(metodo.getNombre(),"La clase ya tiene un constructor.");
        }
    }

    public void insertarAtributo(String nombreAtributo, Atributo atributo) throws ExcepcionSemantica {
        if (atributos.containsKey(nombreAtributo)) {
            throw new ExcepcionSemantica(atributo.getNombre(),"El Atributo con el nombre " + nombreAtributo + " ya existe.");
        }
        atributos.put(nombreAtributo, atributo);
    }

    public void insertarMetodo(String nombreMetodo, Metodo metodo) throws ExcepcionSemantica {
        if (metodos.containsKey(nombreMetodo)) {
            throw new ExcepcionSemantica(metodo.getNombre(),"El método con el nombre " + nombreMetodo + " ya existe.");
        }
        metodos.put(nombreMetodo, metodo);
    }

    public void chequeoDeSentencias() throws ExcepcionSemantica {
        for (Metodo metodo : metodos.values()) {
            ts.setMetodoActual(metodo);
            metodo.chequear();
        }
    }

    public void setPadre(Clase padre) {
        this.padre = padre;
    }

    public Clase getPadre() {
        return padre;
    }

    public Token getNombre() {
        return nombre;
    }

    public Metodo getMetodo(String nombreMetodo) {
        return metodos.get(nombreMetodo);
    }

    public HashMap<String, Metodo> getMetodos() {
        return metodos;
    }

    public HashMap<String, Atributo> getAtributos() {
        return atributos;
    }

    public void consolidar() {
        estaConsolidada = true;
    }

    public boolean estaConsolidada() {
        return estaConsolidada;
    }

    public Metodo getConstructor() {
        return constructor;
    }

    public Atributo getAtributo(String lexema) {
        return atributos.get(lexema);
    }

    public int getTamanioCIR() {
        tamanioCIR = atributos.size() + 1;
        return tamanioCIR;
    }

    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        if(!nombre.getLexema().equals("Object") && !nombre.getLexema().equals("String") && !nombre.getLexema().equals("System")){
            gcf.setModoData();
            generarVT(gcf);
            generarAtributosStaticos(gcf);
            //generarOffsetAtributos2();
            gcf.setModoCode();
            gcf.generarEspacioEnBlanco();
            if(!tieneConstructor){
                gcf.agregarInstruccion("lbl" + nombre.getLexema() + "_constructor: LOADFP  ; Apila el valor del registro fp");
                gcf.agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
                gcf.agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
                gcf.agregarInstruccion("FMEM 0");
                gcf.agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
                gcf.agregarInstruccion("RET 1");
                gcf.generarEspacioEnBlanco();
            }
            for (Metodo metodo : metodos.values()) {
                metodo.generar(gcf);
            }
        }
    }

    private void generarAtributosStaticos(GeneradorDeCodigoFuente gcf) throws IOException {
        for (Atributo atributo : atributos.values()) {
            if (atributo.getEsStatic()) {
                gcf.agregarInstruccion("lbl_" + atributo.getNombre().getLexema() + ":  DW 0");
            }
        }
    }

    public void generarOffsetMetodos(){
        if(padre.getNombre().getLexema().equals("Object")){
            int i = 0;
            for (Metodo metodo : metodos.values()) {
                if(metodo.esConstructor()){
                    metodo.setOffset(0);
                }else {
                    if (!metodo.getEsStatic()){
                        metodo.setOffset(i);
                        i++;
                    }
                }
            }
            maximoOffsetMetodos = i;
            calculeElOffsetMetodos = true;
        }
        else{
            if (!padre.getCalculeElOffset()){
                padre.generameLosOffsets();
            }
            int i = padre.getMaximoOffset();
            for (Metodo metodo : metodos.values()) {
                if(metodo.esConstructor()){
                    metodo.setOffset(0);
                }else {
                    if (!metodo.getEsStatic()){
                        if(metodo.getClasePadre().getNombre().getLexema().equals(nombre.getLexema())){
                            metodo.setOffset(i);
                            i++;
                        }
                    }
                }
            }
            maximoOffsetMetodos = i;
            calculeElOffsetMetodos = true;
        }
    }


    private void generarOffsetAtributos2() {
        int offset = 1;
        for (Atributo atributo : atributos.values()) {
            atributo.setOffset(offset);
            offset++;
        }
    }

   public void generarOffsetAtributos() {
        if(padre.getNombre().getLexema().equals("Object")){
            int i = 1;
            for (Atributo atributo : atributos.values()) {
                if (!atributo.getEsStatic()){
                    atributo.setOffset(i);
                    i++;
                }
            }
            maximoOffsetAtributos = i;
            calculeElOffsetAtributos = true;
        }
        else{
            if (!padre.getCalculeElOffsetAtributos()){
                padre.generarOffsetAtributos();
            }
            int i = padre.getMaximoOffsetAtributos();
            for (Atributo atributo : atributos.values()) {
                if (atributo.getClasePadre().getNombre().getLexema().equals(nombre.getLexema())) {
                    if (!atributo.getEsStatic()){
                        atributo.setOffset(i);
                        i++;
                    }
                }
            }
            maximoOffsetAtributos = i;
            calculeElOffsetAtributos = true;
        }
    }

    private int getMaximoOffsetAtributos() {
        return maximoOffsetAtributos;
    }

    private boolean getCalculeElOffsetAtributos() {
        return calculeElOffsetAtributos;
    }

    public void generameLosOffsets(){
        generarOffsetMetodos();
    }

    public boolean getCalculeElOffset(){
        return calculeElOffsetMetodos;
    }

    public int getMaximoOffset(){
        return maximoOffsetMetodos;
    }

    private void generarVT(GeneradorDeCodigoFuente gcf) throws IOException {
        List<Metodo> metodosOrdenados = new ArrayList<>(metodos.values());

        // Sort the methods by their offset in ascending order
        metodosOrdenados.sort(Comparator.comparingInt(Metodo::getOffset));
        StringBuilder toReturn = new StringBuilder();
        boolean bandera = false;
        for (Metodo metodo : metodosOrdenados) {
            if (!metodo.getEsStatic() && !metodo.esConstructor()) {
                if (bandera) {
                    toReturn.append(",");
                }
                toReturn.append("lbl").append(metodo.getClasePadre().nombre.getLexema()).append("_").append(metodo.getNombre().getLexema());
                bandera = true;
            }
        }
        if (!bandera) {
            gcf.agregarInstruccion("lblVT" + nombre.getLexema() + ":  NOP");
        } else {
            gcf.agregarInstruccion("lblVT" + nombre.getLexema() + ":  DW " + toReturn);
        }
    }
}
