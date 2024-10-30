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

    @Override
    public boolean esCompatibleOperador(Token token) {
        return token.getToken_id().equals("asignacion") || token.getToken_id().equals("comparacion") || token.getToken_id().equals("distinto");
    }

    @Override
    public void setNombreDelTipo(Token token) {

    }

    @Override
    public boolean esCompatibleTipo(Tipo t) {
        return this.getNombreClase().getLexema().equals(t.getNombreClase().getLexema());
    }
}
