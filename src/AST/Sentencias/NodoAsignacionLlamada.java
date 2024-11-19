package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AST.Expresiones.NodoExpresionAsignacion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoAsignacionLlamada extends NodoSentencia {

    NodoExpresion expresion;

    Tipo tipo;

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
        tipo = expresion.chequear();
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        //TODO aca puede ser que haya que hacer algo mas
        expresion.generar(gcf);
        if (!tipo.getNombreClase().getLexema().equals("void")){
            gcf.agregarInstruccion("POP");
        }
        //Tengo que hacer pop si la expresion no es lado derecho, si el ultimo metodo
    }
}
