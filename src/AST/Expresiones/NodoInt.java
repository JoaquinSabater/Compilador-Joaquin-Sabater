package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoPrimitivo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoInt extends NodoOperandoLiteral {
    public NodoInt(Token valor) {
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
        TipoPrimitivo tipo = new TipoPrimitivo();
        tipo.setNombreClase(new Token("pr_int", "int", token.getNro_linea()));
        return tipo;
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        gcf.agregarInstruccion("PUSH " + token.getLexema() +"; Apilar el entero " + token.getLexema());
    }
}
