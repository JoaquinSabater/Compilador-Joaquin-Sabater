package AnalizadorSemantico;

import AnalizadorLexico.Token;

import java.util.HashMap;

public class Clase {

    Token nombre;

    private HashMap<String, Atributo> atributos = new HashMap<>();

    private HashMap<String, Metodo> metodos = new HashMap<>();

    private Clase padre;

    boolean estaConsolidada = false;


    public Clase(Token nombre) {
        this.nombre = nombre;
    }

    public void insertarAtributo(String nombreAtributo, Atributo atributo) throws ExcepcionSemantica {
        if (atributos.containsKey(nombreAtributo)) {
            throw new ExcepcionSemantica(this.nombre,"El método con el nombre " + nombreAtributo + " ya existe.");
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

    public void consolidar() {
        estaConsolidada = true;
    }

    public boolean estaConsolidada() {
        return estaConsolidada;
    }

}
