package AnalizadorSemantico;

import AnalizadorLexico.Token;

public interface Tipo {

    public void setNombreClase(Token nombreClase);

    public Token getNombreClase();
    public boolean esTipoPrimitivo();
}
