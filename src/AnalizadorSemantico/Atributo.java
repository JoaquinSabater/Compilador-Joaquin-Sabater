package AnalizadorSemantico;

import AnalizadorLexico.Token;

public class Atributo {

    private Tipo tipo;

    private Token nombre;

    public Atributo(Tipo tipo, Token nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
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

}
