package AST.Encadenado;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.Metodo;

import java.util.ArrayList;

public class LlamadaEncadenada extends Encadenado{
    private ArrayList<NodoExpresion> listaExpresiones;
    private Metodo metodo;

    public LlamadaEncadenada(Token token, ArrayList<NodoExpresion> listaExpresiones){
        super(token);
        this.listaExpresiones = listaExpresiones;
    }

}
