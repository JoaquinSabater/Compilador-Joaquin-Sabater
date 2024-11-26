package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoIf extends NodoSentencia {

    private NodoExpresion condicion;
    private NodoSentencia sentencia;
    private NodoSentencia sentenciaElse;

    TS ts;

    public NodoIf(Token token,TS ts) {
        super(token);
        this.ts = ts;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        Tipo tipoCondicion = condicion.chequear();
        if (!tipoCondicion.getNombreClase().getToken_id().equals("pr_boolean")) {
            throw new ExcepcionSemantica(token, "La condicion del if debe ser de tipo boolean");
        }
        sentencia.chequear();
        if (sentenciaElse != null) {
            sentenciaElse.chequear();
        }
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        if(sentenciaElse == null){
            generacionIf(gcf);
        }
        else {
            generacionIfElse(gcf);
        }
    }

    private void generacionIf(GeneradorDeCodigoFuente gcf) throws IOException {
        String etiquetaIf = ts.generarEtiquetaIf();
        condicion.generar(gcf);
        gcf.agregarInstruccion("BF "+etiquetaIf+"; ");
        sentencia.generar(gcf);
        gcf.agregarInstruccion(etiquetaIf+": NOP ; Final del then ");
    }

    private void generacionIfElse(GeneradorDeCodigoFuente gcf) throws IOException {
        String outetiquetaIf = ts.generarEtiquetaIf();
        String etiquetaElse = ts.generarEtiquetaElse();
        condicion.generar(gcf);
        gcf.agregarInstruccion("BF "+etiquetaElse+"; ");
        sentencia.generar(gcf);
        gcf.agregarInstruccion("JUMP "+outetiquetaIf+"; ");
        gcf.agregarInstruccion(etiquetaElse+": NOP ; Inicio del else ");
        sentenciaElse.generar(gcf);
        gcf.agregarInstruccion(outetiquetaIf+": NOP ; Final del if ");
    }

    public void setCondicion(NodoExpresion condicion) {
        this.condicion = condicion;
    }

    public void setSentencia(NodoSentencia sentencia) {
        this.sentencia = sentencia;
    }

    public void setSentenciaElse(NodoSentencia sentenciaElse) {
        this.sentenciaElse = sentenciaElse;
    }

    public NodoExpresion getCondicion() {
        return condicion;
    }

    public NodoSentencia getSentencia() {
        return sentencia;
    }

    public NodoSentencia getSentenciaElse() {
        return sentenciaElse;
    }
}
