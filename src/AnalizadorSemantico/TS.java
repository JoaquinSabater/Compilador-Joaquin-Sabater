package AnalizadorSemantico;

import AnalizadorLexico.Token;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TS {

    /*
    no es posible repetir los nombre entre la superclase

    Si una clase sobre-escribe un metodo,este debe tener exactamente la misma cantidad y tipo de parametros,

    Un metodo no puede tener mas de un parametro con el mismo nombre.
    Ninguna clase puede tener herencia circular (es decir, siguiendo la linea de ancestros extenderse
    a si misma).

    Si un m´etodo m1 es declarado en una clase x y tiene el mismo nombre que un m´etodo en la
    superclase, entonces el modificador de m´etodo (static o dynamic), el tipo de los argumentos y
    el tipo de retorno tambi´en deben coincidir.

    No se puede definir una variable de instancia con el mismo nombre que de una varaible de
    instancia de un ancestro

    Alguna clase deber tener un m´etodo est´atico llamado main, el cual no posee p´arametros.

    Falta heredar de object
     */

    private HashMap<String, Clase> Clases;

    Clase claseActual;

    Metodo metodoActual;

    public TS() throws ExcepcionSemantica {
        Clases = new HashMap<>();
        claseActual = null;
        metodoActual = null;

        // Crear y agregar la clase Object
        Token tokenObject = new Token("idClase", "Object", 0);
        insertarClase(tokenObject);
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "debugPrint", 0));
        agregarParametros(new Token("pr_int", "int", 0), new Token("idMetVar", "i", 0));

        // Crear y agregar la clase String
        Token tokenString = new Token("idClase", "String", 0);
        insertarClase(tokenString);

        // Crear y agregar la clase System
        Token tokenSystem = new Token("idClase", "System", 0);
        insertarClase(tokenSystem);
        insertarMetodos(new Token("pr_int", "int", 0), new Token("idMetVar", "read", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printB", 0));
        agregarParametros(new Token("pr_boolean", "boolean", 0), new Token("idMetVar", "b", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printC", 0));
        agregarParametros(new Token("pr_char", "char", 0), new Token("idMetVar", "c", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printI", 0));
        agregarParametros(new Token("pr_int", "int", 0), new Token("idMetVar", "i", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printS", 0));
        agregarParametros(new Token("idClase", "String", 0), new Token("idMetVar", "s", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "println", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printBln", 0));
        agregarParametros(new Token("pr_boolean", "boolean", 0), new Token("idMetVar", "b", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printCln", 0));
        agregarParametros(new Token("pr_char", "char", 0), new Token("idMetVar", "c", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printIln", 0));
        agregarParametros(new Token("pr_int", "int", 0), new Token("idMetVar", "i", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printSln", 0));
        agregarParametros(new Token("idClase", "String", 0), new Token("idMetVar", "s", 0));
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
        //System.out.println("-------------------------------------------------------------------");
        //System.out.println("Clase " + tokenActual.getLexema() + " insertada.");
    }

    public void insertarConstructor(Token tipo,Token nombre) throws ExcepcionSemantica {
        if(claseActual == null){
            throw new ExcepcionSemantica(nombre, "No se puede agregar un constructor a una clase que no existe.");
        }
        if(claseActual.getNombre().getLexema().equals(nombre.getLexema())){
            Metodo m = new Metodo();
            Tipo t;
            t = new TipoClase();
            t.setNombreClase(tipo);
            m.setTipo(t);
            m.setNombre(nombre);
            m.setClasePadre(claseActual);
            claseActual.insertarConstructor(m);
            metodoActual = m;
            //System.out.println("Constructor " + nombre.getLexema() + " insertado en la clase: " + claseActual.getNombre().getLexema());
        }else {
            throw new ExcepcionSemantica(nombre, "El nombre del constructor no coincide con el nombre de la clase.");
        }
    }

    public void insertarMetodos(Token tipo,Token nombre) throws ExcepcionSemantica {
        //Tipo no es un token tiene que ser un tipo
        Tipo t;
        if (tipo.getToken_id().equals("pr_boolean") || tipo.getToken_id().equals("pr_char") || tipo.getToken_id().equals("pr_int")) {
            t = new TipoPrimitivo();
            t.setNombreClase(tipo);
        }else {
            t = new TipoClase();
            t.setNombreClase(tipo);
        }
        Metodo m = new Metodo();
        m.setTipo(t);
        m.setNombre(nombre);
        m.setClasePadre(claseActual);
        if (claseActual == null) {
            throw new ExcepcionSemantica(nombre, "No se puede agregar un metodo a una clase que no existe.");
        }
        claseActual.insertarMetodo(nombre.getLexema(), m);
        metodoActual = m;
        //System.out.println("Metodo " + nombre.getLexema() + " insertado en la clase: " + claseActual.getNombre().getLexema());
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
        }else {
            Clase c = new Clase(padre);
            claseActual.setPadre(c);
            System.out.println(claseActual.getPadre().getNombre().getLexema());
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
        //System.out.println("A la clase "+claseActual.getNombre().getLexema()+"  Atributo " + nombre.getLexema() + " ");
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
        //System.out.println("Parametro " + nombre.getLexema() + " insertado. al medotodo "+metodoActual.getNombre().getLexema()+" ");
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

    // En el archivo `src/AnalizadorSemantico/TS.java`

    public void chequeoDeDeclaraciones() throws ExcepcionSemantica {
        for (Clase c : Clases.values()) {
            // Verificar herencia circular
            verificarHerenciaCircular(c, new HashSet<>());

            // Verificar que la clase padre esté definida
            if (c.getPadre() != null) {
                String nombrePadre = c.getPadre().getNombre().getLexema();
                if (!Clases.containsKey(nombrePadre)) {
                    throw new ExcepcionSemantica(c.getPadre().getNombre(), "La clase padre " + nombrePadre + " no está definida.");
                } else {
                    c.setPadre(Clases.get(nombrePadre));
                }
            }

            // Verificar tipos de atributos
            for (Atributo atributo : c.getAtributos().values()) {
                if (!esTipoValido(atributo.getTipo())) {
                    throw new ExcepcionSemantica(c.getNombre(), "El tipo del atributo " + atributo.getNombre().getLexema() + " no está definido.");
                }
            }

            // Verificar tipos de parámetros y métodos
            for (Metodo metodo : c.getMetodos().values()) {
                if (!esTipoValido(metodo.getTipo())) {
                    throw new ExcepcionSemantica(c.getNombre(), "El tipo del método " + metodo.getNombre().getLexema() + " no está definido.");
                }
                for (Parametro parametro : metodo.getParametros().values()) {
                    if (!esTipoValido(parametro.getTipo())) {
                        throw new ExcepcionSemantica(c.getNombre(), "El tipo del parámetro " + parametro.getNombre().getLexema() + " no está definido.");
                    }
                }
            }
        }
    }

    private void verificarHerenciaCircular(Clase c, Set<String> visitadas) throws ExcepcionSemantica {
        if (visitadas.contains(c.getNombre().getLexema())) {
            throw new ExcepcionSemantica(c.getNombre(), "Herencia circular detectada en la clase " + c.getNombre().getLexema());
        }
        visitadas.add(c.getNombre().getLexema());
        if (c.getPadre() != null) {
            verificarHerenciaCircular(c.getPadre(), visitadas);
        }
    }

    private boolean esTipoValido(Tipo tipo) {
        if(Objects.equals(tipo.getNombreClase().getLexema(), "void")){
            return true;
        }
        if (tipo instanceof TipoPrimitivo) {
            return true;
        } else if (tipo instanceof TipoClase) {
            return Clases.containsKey(tipo.getNombreClase().getLexema());
        }
        return false;
    }

    public void consolidar() throws ExcepcionSemantica {
        for (Clase c : Clases.values()) {
            if (c.getPadre() != null) {
                Clase padre = c.getPadre();

                // Copiar atributos de la clase padre a la clase hija
                for (Atributo atributoPadre : padre.getAtributos().values()) {
                    if (!c.getAtributos().containsKey(atributoPadre.getNombre().getLexema())) {
                        c.insertarAtributo(atributoPadre.getNombre().getLexema(), atributoPadre);
                    }
                }

                // Copiar métodos de la clase padre a la clase hija
                for (Metodo metodoPadre : padre.getMetodos().values()) {
                    if (c.getMetodos().containsKey(metodoPadre.getNombre().getLexema())) {
                        Metodo metodoHijo = c.getMetodos().get(metodoPadre.getNombre().getLexema());
                        if (!metodoHijo.getTipo().getNombreClase().getLexema().equals(metodoPadre.getTipo().getNombreClase().getLexema()) || !metodoHijo.compararParametros(metodoPadre.getParametros())) {
                            throw new ExcepcionSemantica(metodoHijo.getNombre(), "El método " + metodoPadre.getNombre().getLexema() + " en la clase " + c.getNombre().getLexema() + " no coincide con el método en la clase padre.");
                        }
                    }else {
                        c.insertarMetodo(metodoPadre.getNombre().getLexema(), metodoPadre);
                    }
                }
            }
        }
    }

}
