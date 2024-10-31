package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoBrake extends NodoSentencia {

    boolean esWhileOrSwitch = false;

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
}
