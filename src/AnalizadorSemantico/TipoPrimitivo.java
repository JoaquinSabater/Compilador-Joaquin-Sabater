package AnalizadorSemantico;

import AnalizadorLexico.Token;

public class TipoPrimitivo implements Tipo {

    private Token nombreDelTipo;

    @Override
    public void setNombreClase(Token nombreClase) {
        this.nombreDelTipo = nombreClase;
    }

    @Override
    public Token getNombreClase() {
        return nombreDelTipo;
    }

    @Override
    public boolean esTipoPrimitivo() {
        return true;
    }
}
