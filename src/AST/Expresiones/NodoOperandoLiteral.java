package AST.Expresiones;

import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoOperandoLiteral extends NodoOperando {
    private String valor;

    public NodoOperandoLiteral(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
