package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Clase;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Metodo;
import AnalizadorSemantico.Tipo;

import java.util.ArrayList;

public class NodoBloque extends NodoSentencia {

    private ArrayList<NodoSentencia> listaSentencias;

    Clase claseContenedora;

    Metodo metodoContenedor;

    public NodoBloque(Token token) {
        super(token);
        listaSentencias = new ArrayList<>();
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        for (NodoSentencia sentencia : listaSentencias) {
            sentencia.chequear();
        }
        return null;
    }

    public void setClaseContenedora(Clase claseContenedora) {
        this.claseContenedora = claseContenedora;
    }

    public Clase getClaseContenedora() {
        return claseContenedora;
    }

    public void setMetodoContenedor(Metodo metodoContenedor) {
        this.metodoContenedor = metodoContenedor;
    }

    public Metodo getMetodoContenedor() {
        return metodoContenedor;
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
