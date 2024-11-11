package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoPrimitivo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoBoolean extends NodoOperandoLiteral {
    public NodoBoolean(Token valor) {
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
        tipo.setNombreClase(new Token("pr_boolean", "pr_boolean", getValor().getNro_linea()));
        return tipo;
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        if(token.getLexema().equals("true")){
            gcf.agregarInstruccion("PUSH 1; Apilar el booleano true");
        } else {
            gcf.agregarInstruccion("PUSH 0; Apilar el booleano false");
        }
    }
}
