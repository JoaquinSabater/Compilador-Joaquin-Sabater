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
        TipoPrimitivo tipo = new TipoPrimitivo();
        tipo.setNombreClase(this.getValor());
        return tipo;
    }
}
