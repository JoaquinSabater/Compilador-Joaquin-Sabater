package AnalizadorSemantico;

import AnalizadorLexico.Token;

public class TipoClase implements Tipo {

    private Token nombreDeClase;

    @Override
    public void setNombreClase(Token nombreClase) {
        this.nombreDeClase = nombreClase;
    }

    @Override
    public Token getNombreClase() {
        return nombreDeClase;
    }

    @Override
    public boolean esTipoPrimitivo() {
        return false;
    }
}
