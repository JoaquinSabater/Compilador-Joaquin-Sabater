package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AST.Expresiones.NodoOperandoLiteral;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;

import java.util.ArrayList;
import java.util.HashMap;

public class NodoSwitch extends NodoSentencia {

    NodoExpresion expresion;

    private ArrayList<NodoSentencia> listaSentencias;

    private HashMap<NodoOperandoLiteral, NodoSentencia> casos;

    NodoSentencia sentenciaDefault;

    public NodoSwitch(Token token) {
        super(token);
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

    }

    public void agregarCaso(NodoOperandoLiteral nodoOperandoLiteral, NodoSentencia sentencia) {
        casos.put(nodoOperandoLiteral, sentencia);
    }
}
