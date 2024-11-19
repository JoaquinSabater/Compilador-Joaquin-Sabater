package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoBrake extends NodoSentencia {

    boolean esWhileOrSwitch = false;

    NodoWhile nodoWhile = null;

    NodoSwitch nodoSwitch = null;


    public NodoBrake(Token token) {
        super(token);
    }

    public void setEsWhileOrSwitch(boolean esWhileOrSwitch) {
        this.esWhileOrSwitch = esWhileOrSwitch;
    }

    public boolean getEsWhileOrSwitch() {
        return esWhileOrSwitch;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        if (!esWhileOrSwitch) {
            throw new ExcepcionSemantica(token,": la sentencia break solo puede ser utilizada dentro de un ciclo o un switch.");
        }
    }

    public void setNodoWhile(NodoWhile nodoWhile) {
        this.nodoWhile = nodoWhile;
    }

    public void setNodoSwitch(NodoSwitch nodoSwitch) {
        this.nodoSwitch = nodoSwitch;
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        //gcf.agregarInstruccion("JUMP " + nodoWhile.getEtiquetaFin() + "; ");
    }
}
