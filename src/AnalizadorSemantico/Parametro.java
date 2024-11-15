package AnalizadorSemantico;

import AnalizadorLexico.Token;

public class Parametro {
    private Tipo tipo;
    private Token nombre;

    int offset;

    public Parametro(Tipo tipo, Token nombre) {
        this.tipo = tipo;
        this.nombre = nombre;
        offset = 0;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setOffset(int offset){
        this.offset = offset;
    }

    public int getOffset(){
        return offset;
    }
}
