package AnalizadorSemantico;

import AnalizadorLexico.Token;

import java.util.HashMap;

public class TS {

    private HashMap<String, Clase> Clases;

    private HashMap<String, Metodo> Metodos;


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

    public void insertarClase(String nombre, Clase clase) throws ExcepcionSemantica {
        if (Clases.containsKey(nombre)) {
            throw new ExcepcionSemantica(clase.nombre, "La clase con el nombre " + nombre + " ya existe.");
        }
        Clases.put(nombre, clase);
        claseActual = clase;
    }

    public void insertarMetodos(String nombre, Metodo metodo) throws ExcepcionSemantica {
        if (Metodos.containsKey(nombre)) {
            throw new ExcepcionSemantica(metodo.getNombre(), "El método con el nombre " + nombre + " ya existe.");
        }
        Metodos.put(nombre, metodo);
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
