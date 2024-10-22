package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;

public class NodoExpresionAsignacion extends NodoExpresion {
    public NodoExpresionAsignacion(Token token) {
        super(token);
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        return null;
    }
}
