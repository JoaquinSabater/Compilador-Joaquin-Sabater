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

    @Override
    public boolean esCompatibleOperador(Token token) {
        if (this.getNombreClase().getLexema().equals("int")) {
            return token.getToken_id().equals("suma") || token.getToken_id().equals("resta") || token.getToken_id().equals("multiplicacion") || token.getToken_id().equals("division") || token.getToken_id().equals("asignacion") || token.getToken_id().equals("comparacion") || token.getToken_id().equals("distinto") || token.getToken_id().equals("menor") || token.getToken_id().equals("mayor") || token.getToken_id().equals("menorIgual") || token.getToken_id().equals("mayorIgual");
        }else if (this.getNombreClase().getLexema().equals("char")) {
            return token.getToken_id().equals("asignacion") || token.getToken_id().equals("comparacion") || token.getToken_id().equals("distinto");
        }else if(this.getNombreClase().getLexema().equals("boolean")){
            return token.getToken_id().equals("and") || token.getToken_id().equals("or") || token.getToken_id().equals("not") || token.getToken_id().equals("asignacion") || token.getToken_id().equals("comparacion") || token.getToken_id().equals("distinto");
        }
        return false;
    }

    @Override
    public boolean esCompatibleTipo(Tipo t) {
        return this.getNombreClase().getLexema().equals(t.getNombreClase().getLexema());
    }

}
