package AnalizadorSemantico;

import AnalizadorLexico.Token;

public class Parametro {
    private Tipo tipo;
    private Token nombre;

    public Parametro(Tipo tipo, Token nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Token getNombre() {
        return nombre;
    }
}
