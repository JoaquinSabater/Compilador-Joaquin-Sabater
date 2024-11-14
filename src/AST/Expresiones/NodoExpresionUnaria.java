package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoExpresionUnaria extends NodoExpresion{

    private NodoExpresion operando;

    public NodoExpresionUnaria(Token token, NodoExpresion operando) {
        super(token);
        this.operando = operando;
    }

    public boolean esAsignable(){return false;}

    @Override
    public boolean esInvocable() {
        return false;
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        Tipo tipoOperando = operando.chequear();
        if(tipoOperando.esCompatibleOperador(token))
            return tipoOperando;
        else
            throw new ExcepcionSemantica(token,"El operador " + token.getLexema() + " no es compatible con el tipo " + tipoOperando.getNombreClase().getLexema());
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        operando.generar(gcf);
        if(token.getLexema().equals("-")){
            gcf.agregarInstruccion("NEG; Negar el valor");
        } else if(token.getLexema().equals("!")){
            gcf.agregarInstruccion("NOT; Negar el valor");
        }
    }
}
