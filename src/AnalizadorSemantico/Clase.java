package AnalizadorSemantico;

import AnalizadorLexico.Token;

import java.util.HashMap;

public class Clase {

    private Token nombre;

    private HashMap<String, Atributo> atributos = new HashMap<>();

    private HashMap<String, Metodo> metodos = new HashMap<>();

    boolean tieneConstructor = false;

    private Metodo constructor;

    private Clase padre;

    private boolean estaConsolidada = false;


    public Clase(Token nombre) {
        this.nombre = nombre;
        constructor = new Metodo();
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
            throw new ExcepcionSemantica(this.nombre,"El Atributo con el nombre " + nombreAtributo + " ya existe.");
        }
        atributos.put(nombreAtributo, atributo);
    }

    public void insertarMetodo(String nombreMetodo, Metodo metodo) throws ExcepcionSemantica {
        if (metodos.containsKey(nombreMetodo)) {
            throw new ExcepcionSemantica(this.nombre,"El método con el nombre " + nombreMetodo + " ya existe.");
        }
        metodos.put(nombreMetodo, metodo);
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


}
