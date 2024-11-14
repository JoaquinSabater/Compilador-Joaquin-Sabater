package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoIf extends NodoSentencia {

    private NodoExpresion condicion;
    private NodoSentencia sentencia;
    private NodoSentencia sentenciaElse;

    int labelIf = 0;

    int labelIfElse = 0;

    public NodoIf(Token token) {
        super(token);
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
        String etiquetaIf = generarEtiquetaIf();
        condicion.generar(gcf);
        gcf.agregarInstruccion("BF "+etiquetaIf+"; ");
        sentencia.generar(gcf);
        gcf.agregarInstruccion(etiquetaIf+": NOP ; Final del then ");
    }

    private String generarEtiquetaIf() {
        String nombreLabel = "if_end_label_"+labelIf;
        labelIf+=1;
        return nombreLabel;
    }

    private void generacionIfElse(GeneradorDeCodigoFuente gcf) throws IOException {
        String outetiquetaIf = generarEtiquetaIf();
        String etiquetaElse = generarEtiquetaElse();
        condicion.generar(gcf);
        gcf.agregarInstruccion("BF "+etiquetaElse+"; ");
        sentencia.generar(gcf);
        gcf.agregarInstruccion("JUMP "+outetiquetaIf+"; ");
        gcf.agregarInstruccion(etiquetaElse+": NOP ; Inicio del else ");
        sentenciaElse.generar(gcf);
        gcf.agregarInstruccion(outetiquetaIf+": NOP ; Final del if ");
    }

    private String generarEtiquetaElse() {
        String nombreLabel = "if_else_label_"+labelIfElse;
        labelIfElse+=1;
        return nombreLabel;
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
