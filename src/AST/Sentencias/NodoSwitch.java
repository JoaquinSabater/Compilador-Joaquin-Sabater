package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AST.Expresiones.NodoOperandoLiteral;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NodoSwitch extends NodoSentencia {

    NodoExpresion expresion;

    private ArrayList<NodoSentencia> listaSentencias;

    private HashMap<NodoOperandoLiteral, NodoSentencia> casos;

    private ArrayList<NodoSentencia> casosOrdenados;

    private ArrayList<NodoOperandoLiteral> operandosOrdenados;

    NodoSentencia sentenciaDefault;

    int inicioNumeroLabel = 0;

    int etiqueta = 0;

    TS ts;

    public NodoSwitch(Token token,TS ts) {
        super(token);
        this.ts = ts;
        casos = new HashMap<NodoOperandoLiteral,NodoSentencia>();
        operandosOrdenados = new ArrayList<NodoOperandoLiteral>();
        casosOrdenados = new ArrayList<NodoSentencia>();
    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoExpresion expresion) {
        this.expresion = expresion;
    }

    public void agregarSentencia(NodoSentencia sentencia) {
        listaSentencias.add(sentencia);
    }

    public void setListaSentencias(ArrayList<NodoSentencia> listaSentencias) {
        this.listaSentencias = listaSentencias;
    }

    public ArrayList<NodoSentencia> getListaSentencias() {
        return listaSentencias;
    }

    public void setSentenciaDefault(NodoSentencia sentenciaDefault) {
        this.sentenciaDefault = sentenciaDefault;
    }

    public NodoSentencia getSentenciaDefault() {
        return sentenciaDefault;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        Tipo tipoExpresion = expresion.chequear();
        if (!tipoExpresion.getNombreClase().getToken_id().equals("pr_int") && !tipoExpresion.getNombreClase().getToken_id().equals("pr_char") && !tipoExpresion.getNombreClase().getToken_id().equals("pr_boolean")) {
            throw new ExcepcionSemantica(token, "La expresion del switch debe ser de tipo entero");
        }
        for (Map.Entry<NodoOperandoLiteral, NodoSentencia> caso : casos.entrySet()) {
            caso.getValue().chequear();
        }
        if (sentenciaDefault != null) {
            sentenciaDefault.chequear();
        }
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        String etiquetaInicioSwitch = getEtiquetaInicio() ;
        String etiquetaFinSwitch = getEtiquetaFin();
        String etiquetaDefault = getEtiquetaDefault();
        int i = 0;
        gcf.agregarInstruccion(etiquetaInicioSwitch + ": NOP ; Inicio del switch");
        expresion.generar(gcf);
        for (NodoOperandoLiteral entrada : operandosOrdenados) {
            gcf.agregarInstruccion("DUP ; Duplicar el valor de la expresion");
            gcf.agregarInstruccion("PUSH "+entrada.getValor().getLexema()+"; ");
            gcf.agregarInstruccion("EQ ; "+etiqueta);
            gcf.agregarInstruccion("BT Swich_Case_label_"+i+" ; ");
            i++;
        }
        for (NodoSentencia entrada : casosOrdenados) {
            String etiqueta = generarEtiquetaCase();
            gcf.agregarInstruccion(etiqueta+": NOP ; caso "+etiqueta+" del switch");
            entrada.generar(gcf);
        }
        if (sentenciaDefault != null) {
            gcf.agregarInstruccion(etiquetaDefault+": NOP ; Default");
            sentenciaDefault.generar(gcf);
        }
        gcf.agregarInstruccion("POP ; Eliminar el valor de la expresion");
        gcf.agregarInstruccion(etiquetaFinSwitch+": NOP ; Fin del switch");
    }

    private String generarEtiquetaCase() {
        String nombreLabel = "Swich_Case_label_"+etiqueta;
        etiqueta += 1;
        return nombreLabel;
    }

    private String getEtiquetaDefault() {
        String nombreLabel = "Swich_Default_label_"+inicioNumeroLabel;
        inicioNumeroLabel += 1;
        return nombreLabel;
    }

    private String getEtiquetaFin() {
        String nombreLabel = "Swich_Fin_label_"+inicioNumeroLabel;
        inicioNumeroLabel += 1;
        return nombreLabel;
    }

    private String getEtiquetaInicio() {
        String nombreLabel = "Swich_Inicio_label_"+inicioNumeroLabel;
        inicioNumeroLabel += 1;
        return nombreLabel;
    }

    public void agregarCaso(NodoOperandoLiteral nodoOperandoLiteral, NodoSentencia sentencia) {
        operandosOrdenados.add(nodoOperandoLiteral);
        casosOrdenados.add(sentencia);
        casos.put(nodoOperandoLiteral, sentencia);
    }
}
