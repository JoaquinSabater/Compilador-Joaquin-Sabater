package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoPrimitivo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoChar extends NodoOperandoLiteral {
    public NodoChar(Token valor) {
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
        tipo.setNombreClase(new Token("charLiteral", "charLiteral", token.getNro_linea()));
        return tipo;
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        gcf.agregarInstruccion("PUSH " + token.getLexema() +"; Apilar el caracter " + token.getLexema());
    }
}
