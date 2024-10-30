package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoPrimitivo;

public class NodoBoolean extends NodoOperandoLiteral {
    public NodoBoolean(Token valor) {
        super(valor);
    }

    @Override
    public Tipo chequear() {
        TipoPrimitivo tipo = new TipoPrimitivo(); //TODO: Aca hay que cambiarlo
        tipo.setNombreClase(new Token("pr_boolean", "pr_boolean", getValor().getNro_linea()));
        return tipo;
    }
}
