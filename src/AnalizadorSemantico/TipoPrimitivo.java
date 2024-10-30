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
        if (this.getNombreClase().getToken_id().equals("intLiteral") || this.getNombreClase().getToken_id().equals("pr_int")) {
            return token.getToken_id().equals("suma") || token.getToken_id().equals("resta") || token.getToken_id().equals("multiplicacion") || token.getToken_id().equals("division") || token.getToken_id().equals("asignacion") || token.getToken_id().equals("comparacion") || token.getToken_id().equals("distinto") || token.getToken_id().equals("menor") || token.getToken_id().equals("mayor") || token.getToken_id().equals("menorIgual") || token.getToken_id().equals("mayorIgual");
        }else if (this.getNombreClase().getToken_id().equals("charLiteral")) {
            return token.getToken_id().equals("asignacion") || token.getToken_id().equals("comparacion") || token.getToken_id().equals("distinto");
        }else if(this.getNombreClase().getToken_id().equals("pr_true") || this.getNombreClase().getToken_id().equals("pr_false") || this.getNombreClase().getToken_id().equals("pr_boolean")){
            return token.getToken_id().equals("and") || token.getToken_id().equals("or") || token.getToken_id().equals("not") || token.getToken_id().equals("asignacion") || token.getToken_id().equals("comparacion") || token.getToken_id().equals("distinto");
        }
        return false;
    }

    public void setNombreDelTipo(Token token){
        if(token.getToken_id().equals("and") || token.getToken_id().equals("or") || token.getToken_id().equals("not") || token.getToken_id().equals("comparacion") || token.getToken_id().equals("distinto") || token.getToken_id().equals("mayor") || token.getToken_id().equals("menor") || token.getToken_id().equals("mayorIgual") || token.getToken_id().equals("menorIgual")){
            this.nombreDelTipo = new Token("pr_boolean", "pr_boolean", token.getNro_linea());
        }
        else if(token.getToken_id().equals("suma") || token.getToken_id().equals("resta") || token.getToken_id().equals("multiplicacion") || token.getToken_id().equals("division")){
            this.nombreDelTipo = new Token("intLiteral", "intLiteral", token.getNro_linea());
        }
    }

    @Override
    public boolean esCompatibleTipo(Tipo t) {
        return this.getNombreClase().getLexema().equals(t.getNombreClase().getLexema());
        // Me tengo que fijar tambine lo de los padres
    }

}
