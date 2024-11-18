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

    NodoBloque bloqueActual;

    int offset = 0;

    TS ts;

    public NodoVar(Token idMetVar, NodoExpresion expresion, TS ts,NodoBloque bloqueActual) {
        super(idMetVar);
        this.idMetVar = idMetVar;
        this.expresion = expresion;
        this.ts = ts;
        this.bloqueActual = bloqueActual;
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
        NodoBloque bloqueActualM = metodoActual.getBloqueContenedor();

        if (claseActual.getAtributo(this.idMetVar.getLexema()) != null) {
            throw new ExcepcionSemantica(this.idMetVar, "El nombre de la variable ya ha sido utilizado como atributo de la clase");
        }

        if (metodoActual.getParametro(this.idMetVar.getLexema()) != null) {
            throw new ExcepcionSemantica(this.idMetVar, "El nombre de la variable ya ha sido utilizado como parámetro del método");
        }

        if(bloqueActualM.esVariableDeclarada(this.idMetVar.getLexema())){
            throw new ExcepcionSemantica(this.idMetVar, "El nombre de la variable ya ha sido utilizado en el bloque actual");
        }

        bloqueActualM.agregarVariable(this.idMetVar.getLexema());
        offset = bloqueActual.obtenerSiguienteOffsetDisponible();
        Tipo tipoAux = expresion.chequear();
        TS.AgregarVariableYTipo(this.idMetVar.getLexema(), tipoAux);
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
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
