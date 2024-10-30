package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoPrimitivo;

public class NodoInt extends NodoOperandoLiteral {
    public NodoInt(Token valor) {
        super(valor);
    }

    @Override
    public Tipo chequear() {
        TipoPrimitivo tipo = new TipoPrimitivo();
        tipo.setNombreClase(new Token("pr_int", "int", token.getNro_linea()));
        return tipo;
    }
}
