package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;

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
        //verificar que el break este dentro de un while o un switch
    }
}
