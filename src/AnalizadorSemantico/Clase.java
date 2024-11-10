package AnalizadorSemantico;

import AnalizadorLexico.Token;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;
import java.util.HashMap;

public class Clase {

    private Token nombre;

    private HashMap<String, Atributo> atributos = new HashMap<>();

    private HashMap<String, Metodo> metodos = new HashMap<>();

    boolean tieneConstructor = false;

    private Metodo constructor;

    private Clase padre;

    private boolean estaConsolidada = false;

    TS ts;


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

    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        gcf.setModoData();
        gcf.agregarInstruccion("lblVTInit:  NOP");
        gcf.setModoCode();
        for (Metodo metodo : metodos.values()) {
            metodo.generar(gcf);
        }
        constructor.generar(gcf);
    }
}
