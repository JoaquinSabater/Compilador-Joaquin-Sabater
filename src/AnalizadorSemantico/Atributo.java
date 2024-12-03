package AnalizadorSemantico;

import AnalizadorLexico.Token;

public class Atributo {

    private Tipo tipo;

    private Token nombre;

    boolean esStatic = false;

    private int offset;

    private Clase clasePadre;

    public Atributo(Tipo tipo, Token nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public boolean getEsStatic() {
        return esStatic;
    }

    public void setEsStatic(boolean esStatic) {
        this.esStatic = esStatic;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setNombre(Token nombre) {
        this.nombre = nombre;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setClasePadre(Clase clasePadre) {
        this.clasePadre = clasePadre;
    }

    public Clase getClasePadre() {
        return clasePadre;
    }
}
