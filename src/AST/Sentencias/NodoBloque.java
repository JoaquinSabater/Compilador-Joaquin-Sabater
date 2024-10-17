package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NodoBloque extends NodoSentencia {

    private ArrayList<NodoSentencia> listaSentencias;
    public NodoBloque(Token token) {
        super(token);
        listaSentencias = new ArrayList<>();
    }

    @Override
    public void chequear() throws ExcepcionSemantica {

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
}
