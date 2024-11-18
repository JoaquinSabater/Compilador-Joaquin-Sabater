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

    int offset = 0;

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
        Tipo tipoAux = expresion.chequear();
        TS.AgregarVariableYTipo(this.idMetVar.getLexema(), tipoAux);
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        //TODO Hay un posible problema, si una variable no es utilizada en este caso esoy contando con us offset igual
        gcf.agregarInstruccion("RMEM 1; Reservo espacio para la variable "+idMetVar.getLexema());
        if (expresion != null) {
            expresion.generar(gcf);
        }
        gcf.agregarInstruccion("STORE "+offset+" ; Guardo el valor de la variable "+idMetVar.getLexema());
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
