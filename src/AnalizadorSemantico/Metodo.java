package AnalizadorSemantico;

import AST.Sentencias.NodoBloque;
import AnalizadorLexico.Token;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;
import java.util.HashMap;

public class Metodo {
    private Tipo tipo;
    private Clase clasePadre;
    private Token nombre;
    private HashMap<String, Parametro> parametros = new HashMap<>();
    private boolean esVoid;

    private boolean esConstructor = false;

    private NodoBloque bloqueContenedor;

    private boolean esStatic = false;

    public void insertarParametro(Parametro p) throws ExcepcionSemantica {
        if (parametros.containsKey(p.getNombre().getLexema())) {
            throw new ExcepcionSemantica(p.getNombre(),"El parametro con el nombre " + p.getNombre().getLexema() + " ya existe.");
        }
        parametros.put(p.getNombre().getLexema(), p);
    }

    // Getters and Setters

    public boolean esConstructor(){
        return esConstructor;
    }

    public void setEsConstructor(boolean esConstructor){
        this.esConstructor = esConstructor;
    }

    public NodoBloque getBloqueContenedor() {
        return bloqueContenedor;
    }

    public void setBloqueContenedor(NodoBloque bloqueContenedor) {
        this.bloqueContenedor = bloqueContenedor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Clase getClasePadre() {
        return clasePadre;
    }

    public void setClasePadre(Clase clasePadre) {
        this.clasePadre = clasePadre;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setNombre(Token nombre) {
        this.nombre = nombre;
    }

    public HashMap<String, Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(HashMap<String, Parametro> parametros) {
        this.parametros = parametros;
    }

    public boolean isEsVoid() {
        return esVoid;
    }

    public void setEsVoid(boolean esVoid) {
        this.esVoid = esVoid;
    }

    public boolean compararParametros(HashMap<String, Parametro> otrosParametros) {
        if (this.parametros.size() != otrosParametros.size()) {
            return false;
        }

        for (String key : this.parametros.keySet()) {
            if (!otrosParametros.containsKey(key)) {
                return false;
            }

            Parametro parametro1 = this.parametros.get(key);
            Parametro parametro2 = otrosParametros.get(key);

            if (!parametro1.getTipo().getNombreClase().getLexema().equals(parametro2.getTipo().getNombreClase().getLexema()) ||
                    !parametro1.getNombre().getLexema().equals(parametro2.getNombre().getLexema())) {
                return false;
            }
        }

        return true;
    }

    public boolean getEsStatic() {
        return esStatic;
    }

    public void setEsStatic(boolean esStatic) {
        this.esStatic = esStatic;
    }

    public Parametro getParametro(String lexema) {
        return parametros.get(lexema);
    }

    public void chequear() throws ExcepcionSemantica {
        if (bloqueContenedor != null) {
            bloqueContenedor.chequear();
        }
    }

    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        if(!(clasePadre.getNombre().getLexema().equals("Object") || clasePadre.getNombre().getLexema().equals("String") || clasePadre.getNombre().getLexema().equals("System"))){
            if (esConstructor) {
                gcf.agregarInstruccion("lbl" + clasePadre.getNombre().getLexema() + "_constructor: LOADFP; Apilar el valor del registro");
                gcf.agregarInstruccion("LOADSP  ; Apilar el valor del SP");
                gcf.agregarInstruccion("STOREFP ; Guardar el valor del SP en el FP");
                if (!parametros.isEmpty()) {
                    setOffsetsParametro();
                }
                if (bloqueContenedor != null) {
                    bloqueContenedor.generar(gcf);
                }
                gcf.agregarInstruccion("STOREFP ; Almacena el tope de la pila en el registro");
                gcf.agregarInstruccion("RET 1");
            } else {
                String etiqueta = "lbl" + clasePadre.getNombre().getLexema() + "_" + nombre.getLexema();
                if (!gcf.existeEtiqueta(etiqueta)) {
                    gcf.agregarInstruccion(etiqueta + ": LOADFP; Apilar el valor del registro");
                    gcf.agregarInstruccion("LOADSP  ; Apilar el valor del SP");
                    gcf.agregarInstruccion("STOREFP ; Guardar el valor del SP en el FP");
                    if (!parametros.isEmpty()) {
                        setOffsetsParametro();
                    }
                    if (bloqueContenedor != null) {
                        bloqueContenedor.generar(gcf);
                    }
                    gcf.agregarInstruccion("STOREFP ; Almacena el tope de la pila en el registro");
                    gcf.agregarInstruccion("RET "+parametros.size() + 1);
                }
            }
        }
        gcf.generarEspacioEnBlanco();
    }

    public void setOffsetsParametro(){
        int i = parametros.size();
        for (Parametro p : parametros.values()) {
            p.setOffset(i);
            i--;
        }
    }

    public int getOffset() {
        return parametros.size();
    }
}
