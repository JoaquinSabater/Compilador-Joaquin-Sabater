package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoOperandoLiteral extends NodoOperando {
    private Token valor;

    public NodoOperandoLiteral(Token valor) {
        super(valor);
        this.valor = valor;
    }

    public Token getValor() {
        return valor;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
