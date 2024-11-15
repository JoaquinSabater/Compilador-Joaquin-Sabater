package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AST.Expresiones.NodoExpresionAsignacion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoAsignacionLlamada extends NodoSentencia {

    NodoExpresion expresion;

    public NodoAsignacionLlamada(Token token) {
        super(token);
    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoExpresion expresion) {
        this.expresion = expresion;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        expresion.chequear();
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        expresion.generar(gcf);
    }
}
