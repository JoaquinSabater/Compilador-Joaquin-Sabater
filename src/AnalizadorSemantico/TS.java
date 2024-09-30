package AnalizadorSemantico;

import AnalizadorLexico.Token;

import java.util.HashMap;

public class TS {

    private HashMap<String, Clase> Clases;

    Clase claseActual;

    Metodo metodoActual;

    public TS() {
        Clases = new HashMap<>();
        claseActual = null;
    }

    public void limpiarClases() {
        Clases.clear();
    }

    public Clase getClase(String nombre) {
        return Clases.get(nombre);
    }

    public void insertarClase(Token tokenActual) throws ExcepcionSemantica {
        Clase c = new Clase(tokenActual);
        if (Clases.containsKey(tokenActual.getLexema())) {
            throw new ExcepcionSemantica(tokenActual, "La clase con el nombre " + tokenActual.getLexema() + " ya existe.");
        }
        Clases.put(tokenActual.getLexema(), c);
        claseActual = c;
    }

    public void insertarMetodos(Token tipo,Token nombre) throws ExcepcionSemantica {
        Metodo m = new Metodo();
        m.setTipo(tipo);
        m.setNombre(nombre);
        m.setClasePadre(claseActual);
        if (claseActual == null) {
            throw new ExcepcionSemantica(nombre, "No se puede agregar un metodo a una clase que no existe.");
        }
        claseActual.insertarMetodo(nombre.getLexema(), m);
        metodoActual = m;
    }

    public void setClaseActual(Clase c) {
        claseActual = c;
    }

    public void setHerencia(Token padre) throws ExcepcionSemantica {
        if (claseActual == null) {
            throw new ExcepcionSemantica(padre, "No se puede asignar herencia a una clase que no existe.");
        }
        if (claseActual.getPadre() != null) {
            throw new ExcepcionSemantica(padre, "La clase " + claseActual.nombre + " ya tiene una clase padre asignada.");
        }
        if (!Clases.containsKey(padre.getLexema())) {
            throw new ExcepcionSemantica(padre, "La clase padre " + padre.getLexema() + " no existe.");
        }else {
            claseActual.setPadre(Clases.get(padre.getLexema()));
        }
    }

    public void agregarAtributos(Token tipo, Token nombre) throws ExcepcionSemantica {
        Tipo t;
        if (tipo.getToken_id().equals("pr_boolean") || tipo.getToken_id().equals("pr_char") || tipo.getToken_id().equals("pr_int")) {
            t = new TipoPrimitivo();
            t.setNombreClase(tipo);
        }else {
            t = new TipoClase();
            t.setNombreClase(tipo);
        }
        Atributo a = new Atributo(t, nombre);
        claseActual.insertarAtributo(nombre.getLexema(), a);
    }

    public void agregarParametros(Token tipo, Token nombre) throws ExcepcionSemantica {
        Tipo t;
        if (tipo.getToken_id().equals("pr_boolean") || tipo.getToken_id().equals("pr_char") || tipo.getToken_id().equals("pr_int")) {
            t = new TipoPrimitivo();
            t.setNombreClase(tipo);
        }else {
            t = new TipoClase();
            t.setNombreClase(tipo);
        }
        Parametro p = new Parametro(t, nombre);
        metodoActual.insertarParametro(p);
    }

    public Clase getClaseActual() {
        return claseActual;
    }

    public void setMetodoActual(Metodo m) {
        metodoActual = m;
    }

    public Metodo getMetodoActual() {
        return metodoActual;
    }

}
