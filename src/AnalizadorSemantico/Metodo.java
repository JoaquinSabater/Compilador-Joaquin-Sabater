// En el archivo `src/AnalizadorSemantico/Metodo.java`

package AnalizadorSemantico;

import AnalizadorLexico.Token;

import java.util.HashMap;

public class Metodo {
    private Token tipo;
    private Clase clasePadre;
    private Token nombre;
    private HashMap<String, Parametro> parametros;
    private boolean esVoid;

    // Getters and Setters
    public Token getTipo() {
        return tipo;
    }

    public void setTipo(Token tipo) {
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
