package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AST.Expresiones.NodoOperandoLiteral;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
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

    NodoSentencia sentenciaDefault;

    int inicioNumeroLabel = 0;

    public NodoSwitch(Token token) {
        super(token);
        casos = new HashMap<NodoOperandoLiteral,NodoSentencia>();
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
        gcf.agregarInstruccion(etiquetaInicioSwitch + ": NOP ; Inicio del switch");
        expresion.generar(gcf);
        for (Map.Entry<NodoOperandoLiteral, NodoSentencia> entrada : casos.entrySet()) {
            gcf.agregarInstruccion("DUP ; Duplicar el valor de la expresion");
            if (expresion.obtenerToken().getLexema().equals(entrada.getKey().obtenerToken().getLexema())) {
                entrada.getValue().generar(gcf);
            }
            gcf.agregarInstruccion("EQ ; "+etiquetaFinSwitch);
            gcf.agregarInstruccion("BF "+etiquetaFinSwitch+" ; ");
        }
        if (sentenciaDefault != null) {
            gcf.agregarInstruccion(etiquetaDefault+": NOP ; Default");
            sentenciaDefault.generar(gcf);
        }
        gcf.agregarInstruccion(etiquetaFinSwitch+": NOP ; Fin del switch");
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
        casos.put(nodoOperandoLiteral, sentencia);
    }
}
