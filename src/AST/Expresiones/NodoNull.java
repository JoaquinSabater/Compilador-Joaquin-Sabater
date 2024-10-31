package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoClase;

public class NodoNull extends NodoOperandoLiteral {
    public NodoNull(Token valor) {
        super(valor);
    }

    @Override
    public boolean esAsignable() {
        return false;
    }

    @Override
    public boolean esInvocable() {
        return false;
    }

    @Override
    public Tipo chequear() {
        TipoClase tipo = new TipoClase();
        tipo.setNombreClase(this.getValor());
        return tipo;
    }
}
