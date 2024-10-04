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
        metodoActual = null;
    }

    public void insertarConstructor(Token nombre){

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
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Clase " + tokenActual.getLexema() + " insertada.");
    }

    public void insertarConstructor(Token tipo,Token nombre) throws ExcepcionSemantica {
        if(claseActual == null){
            throw new ExcepcionSemantica(nombre, "No se puede agregar un constructor a una clase que no existe.");
        }
        if(claseActual.getNombre().getLexema().equals(nombre.getLexema())){
            Metodo m = new Metodo();
            m.setTipo(tipo);
            m.setNombre(nombre);
            m.setClasePadre(claseActual);
            claseActual.insertarConstructor(m);
            metodoActual = m;
            System.out.println("Constructor " + nombre.getLexema() + " insertado en la clase: " + claseActual.getNombre().getLexema());
        }else {
            throw new ExcepcionSemantica(nombre, "El nombre del constructor no coincide con el nombre de la clase.");
        }
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
        System.out.println("Metodo " + nombre.getLexema() + " insertado en la clase: " + claseActual.getNombre().getLexema());
    }

    public void setClaseActual(Clase c) {
        claseActual = c;
    }

    public void setHerencia(Token padre) throws ExcepcionSemantica {
        if (claseActual == null) {
            throw new ExcepcionSemantica(padre, "No se puede asignar herencia a una clase que no existe.");
        }
        if (claseActual.getPadre() != null) {
            throw new ExcepcionSemantica(padre, "La clase " + claseActual.getNombre() + " ya tiene una clase padre asignada.");
        }
        if (!Clases.containsKey(padre.getLexema())) {
            throw new ExcepcionSemantica(padre, "La clase padre " + padre.getLexema() + " no existe.");
        }else {
            claseActual.setPadre(Clases.get(padre.getLexema()));
        }
        System.out.println("Herencia de " + padre.getLexema() + " asignada a la clase " + claseActual.getNombre().getLexema());
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
        System.out.println("A la clase "+claseActual.getNombre().getLexema()+"  Atributo " + nombre.getLexema() + " ");
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
        System.out.println("Parametro " + nombre.getLexema() + " insertado. al medotodo "+metodoActual.getNombre().getLexema()+" ");
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
