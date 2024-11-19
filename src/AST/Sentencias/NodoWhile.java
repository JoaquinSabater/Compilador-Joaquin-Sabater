package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AST.Expresiones.*;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoWhile extends NodoSentencia {

    private NodoExpresion condicion;
    private NodoSentencia sentencia;

    private int finNumeroLabel = 0;
    private int inicioNumeroLabel = 0;

    private String etiquetaFin;

    public NodoWhile(Token token) {
        super(token);
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        Tipo tipoCondicion = condicion.chequear();
        if (!tipoCondicion.getNombreClase().getToken_id().equals("pr_boolean")) {
            throw new ExcepcionSemantica(token, "La condición del while debe ser de tipo booleano");
        }
        sentencia.chequear();
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        String etiquetaInicio = generarEtiquetaInicio();
        etiquetaFin = generarEtiquetaFin();
        gcf.agregarInstruccion(etiquetaInicio + ": NOP ; Inicio del while");
        condicion.generar(gcf);
        gcf.agregarInstruccion("BF " + etiquetaFin + "; ");
        sentencia.generar(gcf);
        gcf.agregarInstruccion("JUMP " + etiquetaInicio + "; ");
        gcf.agregarInstruccion(etiquetaFin + ": NOP ; Fin del while");
    }

    private String generarEtiquetaFin() {
        String nombreLabel = "while_end_label_"+finNumeroLabel;
        finNumeroLabel += 1;
        return nombreLabel;
    }

    private String generarEtiquetaInicio() {
        String nombreLabel = "while_begin_label_"+inicioNumeroLabel;
        inicioNumeroLabel += 1;
        return nombreLabel;
    }

    public void setCondicion(NodoExpresion condicion) {
        this.condicion = condicion;
    }

    public void setSentencia(NodoSentencia sentencia) {
        this.sentencia = sentencia;
    }

    public NodoExpresion getCondicion() {
        return condicion;
    }

    public NodoSentencia getSentencia() {
        return sentencia;
    }

    public String getEtiquetaFin() {
        return etiquetaFin;
    }
}
