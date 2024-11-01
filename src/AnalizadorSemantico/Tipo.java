package AnalizadorSemantico;

import AnalizadorLexico.Token;

public interface Tipo {

    public void setNombreClase(Token nombreClase);

    public Token getNombreClase();
    public boolean esTipoPrimitivo();

    public abstract boolean esCompatibleOperador(Token token);

    public void setNombreDelTipo(Token token);

    public abstract boolean esCompatibleTipo(Tipo t, TS ts);
}
