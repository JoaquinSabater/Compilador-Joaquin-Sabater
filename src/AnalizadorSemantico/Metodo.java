// En el archivo `src/AnalizadorSemantico/Metodo.java`

package AnalizadorSemantico;

import AnalizadorLexico.Token;

import java.util.HashMap;

public class Metodo {
    private Tipo tipo;
    private Clase clasePadre;
    private Token nombre;
    private HashMap<String, Parametro> parametros = new HashMap<>();
    private boolean esVoid;

    public void insertarParametro(Parametro p) throws ExcepcionSemantica {
        if (parametros.containsKey(p.getNombre().getLexema())) {
            throw new ExcepcionSemantica(p.getNombre(),"El parametro con el nombre " + p.getNombre().getLexema() + " ya existe.");
        }
        parametros.put(p.getNombre().getLexema(), p);
    }

    // Getters and Setters
    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Clase getClasePadre() {
        return clasePadre;
    }

    public void setClasePadre(Clase clasePadre) {
        this.clasePadre = clasePadre;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setNombre(Token nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(HashMap<String, Parametro> parametros) {
        this.parametros = parametros;
    }

    public boolean isEsVoid() {
        return esVoid;
    }

    public void setEsVoid(boolean esVoid) {
        this.esVoid = esVoid;
    }
}
