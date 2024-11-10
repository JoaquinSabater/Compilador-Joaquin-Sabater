package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoClase;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoString extends NodoOperandoLiteral {
    public NodoString(Token valor) {
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

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {

    }
}
