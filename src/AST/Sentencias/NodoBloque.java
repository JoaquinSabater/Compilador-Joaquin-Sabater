package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.*;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NodoBloque extends NodoSentencia {

    private ArrayList<NodoSentencia> listaSentencias;

    private ArrayList<NodoSentencia> listaSentenciasAux;

    private Set<String> variablesDeclaradas;

    Clase claseContenedora;

    Metodo metodoContenedor;

    NodoBloque padre;

    int cantidadDeVariablesLocales;

    public NodoBloque(Token token, NodoBloque padre) {
        super(token);
        listaSentencias = new ArrayList<>();
        listaSentenciasAux = new ArrayList<>();
        this.padre = padre;
        this.variablesDeclaradas = new HashSet<>();
    }

    public NodoBloque getPadre() {
        return padre;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        while (!listaSentencias.isEmpty()) {
            NodoSentencia sentencia = listaSentencias.remove(0);
            listaSentenciasAux.add(sentencia);
            sentencia.chequear();
        }
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        for (NodoSentencia sentencia : listaSentenciasAux) {
            sentencia.generar(gcf);
        }
        //Libero la memoria de las variables locales
        gcf.agregarInstruccion("FMEM 0");
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

    public boolean esVariableDeclaradaEnBloqueOEnPadre(String nombreVariable) {
        if (padre != null) {
            return padre.esVariableDeclarada(nombreVariable);
        }
        return false;
    }

    public boolean esVariableDeclaradaEnEsteBloque(String nombreVariable) {
        return variablesDeclaradas.contains(nombreVariable);
    }

    public void agregarVariable(String nombreVariable) throws ExcepcionSemantica {
        variablesDeclaradas.add(nombreVariable);
    }

    public boolean esVariableDeclarada(String lexema) {
        if (variablesDeclaradas.contains(lexema)) {
            return true;
        }else{
            return esVariableDeclaradaEnBloqueOEnPadre(lexema);
        }
    }
}
