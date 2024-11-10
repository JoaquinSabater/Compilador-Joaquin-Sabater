package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.Clase;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.Metodo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoVar extends NodoSentencia {
    private Token idMetVar;
    private NodoExpresion expresion;

    TS ts;

    public NodoVar(Token idMetVar, NodoExpresion expresion, TS ts) {
        super(idMetVar);
        this.idMetVar = idMetVar;
        this.expresion = expresion;
        this.ts = ts;
    }

    public Token getIdMetVar() {
        return idMetVar;
    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        Clase claseActual = ts.getClaseActual();
        Metodo metodoActual = ts.getMetodoActual();
        NodoBloque bloqueActual = metodoActual.getBloqueContenedor();

        if (claseActual.getAtributo(this.idMetVar.getLexema()) != null) {
            throw new ExcepcionSemantica(this.idMetVar, "El nombre de la variable ya ha sido utilizado como atributo de la clase");
        }

        if (metodoActual.getParametro(this.idMetVar.getLexema()) != null) {
            throw new ExcepcionSemantica(this.idMetVar, "El nombre de la variable ya ha sido utilizado como parámetro del método");
        }

        if(bloqueActual.esVariableDeclarada(this.idMetVar.getLexema())){
            throw new ExcepcionSemantica(this.idMetVar, "El nombre de la variable ya ha sido utilizado en el bloque actual");
        }

        bloqueActual.agregarVariable(this.idMetVar.getLexema());
        //TODO: Chequear que el nombre de la variable no haya sido utilizado en el bloque actual
        expresion.chequear();
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {

    }
}
