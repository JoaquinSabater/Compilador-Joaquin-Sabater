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


    public boolean esCompatibleTipo(Tipo t, TS ts) {
        if (this.nombreDeClase.getLexema().equals(t.getNombreClase().getLexema())) {
            return true;
        }

        if (this.nombreDeClase.getToken_id().equals("stringLiteral") && t.getNombreClase().getLexema().equals("String")) {
            return true;
        }

        if(this.nombreDeClase.getToken_id().equals("pr_null")){
            return true;
        }

        Clase claseActual = ts.getClase(this.nombreDeClase.getLexema());
        while (claseActual != null) {
            if (claseActual.getNombre().getLexema().equals(t.getNombreClase().getLexema())) {
                return true;
            }
            claseActual = claseActual.getPadre();
        }

        return false;
    }
}
