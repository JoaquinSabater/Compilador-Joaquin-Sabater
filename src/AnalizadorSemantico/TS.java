package AnalizadorSemantico;

import AST.Sentencias.NodoBloque;
import AnalizadorLexico.Token;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TS {

    //TODO cambiar la etiqueta de los metodos staticos

    private HashMap<String, Clase> Clases;

    private static HashMap<String,Tipo> variablesYTipos = new HashMap<>();

    Clase claseActual;

    Metodo metodoActual;

    NodoBloque bloqueActual;

    int labelIf = 0;

    int labelIfElse = 0;

    private int finWhileNumeroLabel = 0;
    private int inicioWhileNumeroLabel = 0;


    public TS() throws ExcepcionSemantica {
        Clases = new HashMap<>();
        variablesYTipos = new HashMap<>();
        claseActual = null;
        metodoActual = null;
        bloqueActual = null;

        // Crear y agregar la clase Object
        Token tokenObject = new Token("idClase", "Object", 0);
        insertarClase(tokenObject);
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "debugPrint", 0),true);
        agregarParametros(new Token("pr_int", "int", 0), new Token("idMetVar", "i", 0));

        // Crear y agregar la clase String
        Token tokenString = new Token("idClase", "String", 0);
        insertarClase(tokenString);
        setHerencia(new Token("idClase", "Object", 0));


        // Crear y agregar la clase System
        Token tokenSystem = new Token("idClase", "System", 0);
        insertarClase(tokenSystem);
        setHerencia(new Token("idClase", "Object", 0));


        insertarMetodos(new Token("pr_int", "int", 0), new Token("idMetVar", "read", 0),true);
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printB", 0),true);
        agregarParametros(new Token("pr_boolean", "boolean", 0), new Token("idMetVar", "b", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printC", 0),true);
        agregarParametros(new Token("pr_char", "char", 0), new Token("idMetVar", "c", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printI", 0),true);
        agregarParametros(new Token("pr_int", "int", 0), new Token("idMetVar", "i", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printS", 0),true);
        agregarParametros(new Token("idClase", "String", 0), new Token("idMetVar", "s", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "println", 0),true);
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printBln", 0),true);
        agregarParametros(new Token("pr_boolean", "boolean", 0), new Token("idMetVar", "b", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printCln", 0),true);
        agregarParametros(new Token("pr_char", "char", 0), new Token("idMetVar", "c", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printIln", 0),true);
        agregarParametros(new Token("pr_int", "int", 0), new Token("idMetVar", "i", 0));
        insertarMetodos(new Token("pr_void", "void", 0), new Token("idMetVar", "printSln", 0),true);
        agregarParametros(new Token("idClase", "String", 0), new Token("idMetVar", "s", 0));

    }

    public static Tipo getTipo(String lexema) {
        return variablesYTipos.get(lexema);
    }

    public static void AgregarVariableYTipo(String lexema, Tipo tipoAux) {
        variablesYTipos.put(lexema,tipoAux);
    }

    public static Tipo getTipoVariable(String lexema) {
        return variablesYTipos.get(lexema);
    }
    public NodoBloque getBloqueActual() {
        return bloqueActual;
    }

    public void setBloqueActual(NodoBloque bloqueActual) {
        this.bloqueActual = bloqueActual;
    }

    public void limpiarClases() {
        Clases.clear();
        variablesYTipos.clear();
    }

    public Clase getClase(String nombre) {
        return Clases.get(nombre);
    }

    public void insertarClase(Token tokenActual) throws ExcepcionSemantica {
        Clase c = new Clase(tokenActual, this);
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
            m.setEsConstructor(true);
            claseActual.insertarConstructor(m);
            claseActual.insertarMetodo(nombre.getLexema(), m);
            metodoActual = m;
            //System.out.println("Constructor " + nombre.getLexema() + " insertado en la clase: " + claseActual.getNombre().getLexema());
        }else {
            throw new ExcepcionSemantica(nombre, "El nombre del constructor no coincide con el nombre de la clase.");
        }
    }

    public void insertarMetodos(Token tipo,Token nombre,boolean esStatic) throws ExcepcionSemantica {
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
        m.setEsStatic(esStatic);
        if (tipo.getToken_id().equals("pr_void")) {
            m.setEsVoid(true);
        }
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
            Clase c = new Clase(padre,this);
            claseActual.setPadre(c);
        }
        //System.out.println("Herencia de " + padre.getLexema() + " asignada a la clase " + claseActual.getNombre().getLexema());
    }

    public void agregarAtributos(Token tipo, Token nombre,boolean esStatic) throws ExcepcionSemantica {
        Tipo t;
        if (tipo.getToken_id().equals("pr_boolean") || tipo.getToken_id().equals("pr_char") || tipo.getToken_id().equals("pr_int")) {
            t = new TipoPrimitivo();
            t.setNombreClase(tipo);
        }else {
            t = new TipoClase();
            t.setNombreClase(tipo);
        }
        Atributo a = new Atributo(t, nombre);
        a.setEsStatic(esStatic);
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
            //System.out.println(t.getNombreClase().getLexema());
        }
        Parametro p = new Parametro(t, nombre);
        metodoActual.insertarParametro(p);
        //System.out.println("Parametro " + nombre.getLexema() + " insertado. al medotodo "+metodoActual.getNombre().getLexema()+" ");
    }

    public void setHerenciaObject() throws ExcepcionSemantica {
        if (claseActual == null) {
            throw new ExcepcionSemantica(new Token("idClase", "Object", 0), "No se puede asignar herencia a una clase que no existe.");
        }
        if (claseActual.getPadre() != null) {
            throw new ExcepcionSemantica(new Token("idClase", "Object", 0), "La clase " + claseActual.getNombre() + " ya tiene una clase padre asignada.");
        }else {
            Clase c = Clases.get("Object");
            claseActual.setPadre(c);
        }
        //System.out.println("Herencia de Object asignada a la clase " + claseActual.getNombre().getLexema());
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

    public void chequeoDeDeclaraciones() throws ExcepcionSemantica {
        boolean mainEncontrado = false;

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
                    throw new ExcepcionSemantica(atributo.getNombre(), "El tipo del atributo " + atributo.getNombre().getLexema() + " no está definido.");
                }
            }

            // Verificar tipos de parámetros y métodos
            for (Metodo metodo : c.getMetodos().values()) {
                if (!esTipoValido(metodo.getTipo())) {
                    throw new ExcepcionSemantica(metodo.getNombre(), "El tipo del método " + metodo.getNombre().getLexema() + " no está definido.");
                }
                for (Parametro parametro : metodo.getParametros().values()) {
                    if (!esTipoValido(parametro.getTipo())) {
                        throw new ExcepcionSemantica(metodo.getNombre(), "El tipo del parámetro " + parametro.getNombre().getLexema() + " no está definido.");
                    }
                }

                // Verificar método main
                if (metodo.getNombre().getLexema().equals("main") && metodo.getEsStatic() && metodo.getParametros().isEmpty() && metodo.getTipo().getNombreClase().getLexema().equals("void")) {
                    mainEncontrado = true;
                }
            }
        }

        if (!mainEncontrado) {
            throw new ExcepcionSemantica(new Token("idMetVar", "main", 0), "No se encontró un método estático llamado main sin parámetros en ninguna clase.");
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

        }else if(tipo.getNombreClase().getToken_id().equals("pr_public")){
            return true;
        }else if(tipo instanceof TipoClase) {
            return Clases.containsKey(tipo.getNombreClase().getLexema());
        }
        return false;
    }

    public void consolidar() throws ExcepcionSemantica {
        for (Clase c : Clases.values()) {
            if(!c.estaConsolidada()){
                consolidarClase(c);
            }
        }
    }

    private void consolidarClase(Clase c) throws ExcepcionSemantica {
        if (c.getPadre() != null && !c.getPadre().estaConsolidada()) {
            consolidarClase(c.getPadre());
        }
        agregarMetodosYAtributos(c);
        c.consolidar();
    }

    private void agregarMetodosYAtributos(Clase c) throws ExcepcionSemantica {
        if (c.getPadre() != null){
            Clase padre = c.getPadre();

            // Copiar atributos de la clase padre a la clase hija
            for (Atributo atributoPadre : padre.getAtributos().values()) {
                if (c.getAtributos().containsKey(atributoPadre.getNombre().getLexema())) {
                    throw new ExcepcionSemantica(c.getAtributos().get(atributoPadre.getNombre().getLexema()).getNombre(), "El nombre del atributo " + atributoPadre.getNombre().getLexema() + " ya está definido en una clase padre");
                }
                c.insertarAtributo(atributoPadre.getNombre().getLexema(), atributoPadre);
            }

            // Copiar métodos de la clase padre a la clase hija
            for (Metodo metodoPadre : padre.getMetodos().values()) {
                if (c.getMetodos().containsKey(metodoPadre.getNombre().getLexema())) {
                    Metodo metodoHijo = c.getMetodos().get(metodoPadre.getNombre().getLexema());
                    if (!metodoHijo.getTipo().getNombreClase().getLexema().equals(metodoPadre.getTipo().getNombreClase().getLexema()) || !metodoHijo.compararParametros(metodoPadre.getParametros()) || metodoHijo.getEsStatic() != metodoPadre.getEsStatic()) {
                        throw new ExcepcionSemantica(metodoHijo.getNombre(), "El método " + metodoPadre.getNombre().getLexema() + " en la clase " + c.getNombre().getLexema() + " no coincide con el método en la clase padre.");
                    }
                }else {
                    c.insertarMetodo(metodoPadre.getNombre().getLexema(), metodoPadre);
                }
            }
        }
    }

    public void chequeoDeSentencias() throws ExcepcionSemantica {
        for (Clase c : Clases.values()) {
            claseActual = c;
            c.chequeoDeSentencias();
        }
    }

    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        gcf.generamosLlamadaMain();
        gcf.primitivasMalloc();
        for (Clase c : Clases.values()) {
            claseActual = c;
            c.generar(gcf);
        }
        gcf.generarCodigoPredefinido();
    }

    public String generarEtiquetaIf() {
        String nombreLabel = "if_end_label_"+labelIf;
        labelIf+=1;
        return nombreLabel;
    }

    public String generarEtiquetaElse() {
        String nombreLabel = "if_else_label_"+labelIfElse;
        labelIfElse+=1;
        return nombreLabel;
    }

    public String generarEtiquetaFinWhile() {
        String nombreLabel = "while_end_label_"+finWhileNumeroLabel;
        finWhileNumeroLabel += 1;
        return nombreLabel;
    }

    public String generarEtiquetaInicioWhile() {
        String nombreLabel = "while_begin_label_"+inicioWhileNumeroLabel;
        inicioWhileNumeroLabel += 1;
        return nombreLabel;
    }

    public void mostrarInformacionClases() {
        for (Clase c : Clases.values()) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Clase: " + c.getNombre().getLexema());
            if (c.getPadre() != null) {
                System.out.println("  Padre: " + c.getPadre().getNombre().getLexema());
            } else {
                System.out.println("  Padre: Ninguno");
            }

            System.out.println(" ------------------------- Atributos:-----------------------------");
            for (Atributo atributo : c.getAtributos().values()) {
                System.out.println("    Nombre: " + atributo.getNombre().getLexema() + ", Tipo: " + atributo.getTipo().getNombreClase().getLexema() + ", Es estático: " + atributo.getEsStatic());
            }

            System.out.println("  --------------------------Métodos:-----------------------------");
            for (Metodo metodo : c.getMetodos().values()) {
                System.out.println("    Nombre: " + metodo.getNombre().getLexema() + ", Tipo: " + metodo.getTipo().getNombreClase().getLexema() + ", Es estático: " + metodo.getEsStatic());
                System.out.println("    Parámetros:");
                for (Parametro parametro : metodo.getParametros().values()) {
                    System.out.println("      Nombre: " + parametro.getNombre().getLexema() + ", Tipo: " + parametro.getTipo().getNombreClase().getLexema());
                }
            }
        }
    }

}
