package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoExpresionVacia extends NodoExpresion{
    public NodoExpresionVacia(Token token) {
        super(token);
    }

    public boolean esAsignable(){return false;}

    @Override
    public boolean esInvocable() {
        return false;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {

    }
}
