package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoPrimitivo;

public class NodoChar extends NodoOperandoLiteral {
    public NodoChar(Token valor) {
        super(valor);
    }

    @Override
    public Tipo chequear() {
        TipoPrimitivo tipo = new TipoPrimitivo();
        tipo.setNombreClase(new Token("charLiteral", "charLiteral", token.getNro_linea()));
        return tipo;
    }
}
