package GeneradorDeCodigoFuente;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GeneradorDeCodigoFuente {

    private Set<String> etiquetas = new HashSet<>();
    private File archivoSalida;
    private BufferedWriter buffer;

    private String modoActual;

    public GeneradorDeCodigoFuente(String aux) throws IOException {
        inicializador(aux);
    }

    public void inicializador(String aux) throws IOException {
        archivoSalida = new File(aux);
        FileWriter fileWriter = new FileWriter(archivoSalida);
        buffer = new BufferedWriter(fileWriter);
        modoActual = ".";
        setModoCode();
    }

    public void setModoCode() throws IOException {
        if(!modoActual.equals(".CODE")){
            buffer.write(".CODE");
            buffer.newLine();
            modoActual = ".CODE";
        }
    }
    public void setModoData() throws IOException {
        if(!modoActual.equals(".DATA")){
            buffer.write(".DATA");
            buffer.newLine();
            modoActual = ".DATA";
        }
    }

    public void agregarInstruccion(String linea) throws IOException {
        buffer.write(linea);
        buffer.newLine();
        if (linea.contains(":")) {
            String etiqueta = linea.split(":")[0].trim();
            etiquetas.add(etiqueta);
        }
    }

    public boolean existeEtiqueta(String etiqueta) {
        return etiquetas.contains(etiqueta);
    }

    public void generamosLlamadaMain() throws IOException {
        setModoCode();
        agregarInstruccion("PUSH lblInit_main");
        agregarInstruccion("CALL");
        agregarInstruccion("HALT");
        generarEspacioEnBlanco();
    }

    public void primitivasMalloc() throws IOException {
        agregarInstruccion("simple_heap_init:   RET 0   ; Retorna inmediatamente");
        agregarInstruccion("simple_malloc:      LOADFP  ; inicializacion del RA");
        agregarInstruccion("LOADSP");
        agregarInstruccion("STOREFP ; Finaliza inicializacion del RA");
        agregarInstruccion("LOADHL  ; hl");
        agregarInstruccion("DUP  ; hl");
        agregarInstruccion("PUSH 1  ; 1");
        agregarInstruccion("ADD ; hl+1");
        agregarInstruccion("STORE 4 ; Guarda el resultado (el puntero a la primer celda de la region de memoria)");
        agregarInstruccion("LOAD 3  ; Carga la cantidad de celdas a alojar (parametro debe ser positvo)");
        agregarInstruccion("ADD");
        agregarInstruccion("STOREHL ; Mueve el heap limit (h1). Expande el heap");
        agregarInstruccion("STOREFP");
        agregarInstruccion("RET 1   ; Retorna elimiando el parametro");
        generarEspacioEnBlanco();
    }

    public void cerrar() throws IOException {
        buffer.close();
    }

    public void generarEspacioEnBlanco() throws IOException {
        buffer.newLine();
    }

    public void generarCodigoPredefinido() throws IOException {
        setModoData();
        agregarInstruccion("lblVTObject: NOP");
        setModoCode();
        generarEspacioEnBlanco();
        agregarInstruccion("lblConstructor@Object: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("FMEM 0");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
        agregarInstruccion("debugPrint: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("LOAD 3  ; Apila el parámetro");
        agregarInstruccion("IPRINT  ; Imprime el entero en el tope de la pila");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();

        setModoData();
        agregarInstruccion("lblVTString: NOP");
        setModoCode();
        generarEspacioEnBlanco();
        agregarInstruccion("lblConstructor@String: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("FMEM 0");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();

        setModoData();
        agregarInstruccion("lblVTSystem: NOP");
        setModoCode();
        generarEspacioEnBlanco();
        agregarInstruccion("lblConstructor@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("FMEM 0");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetprintC@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("LOAD 3  ; Apila el parámetro");
        agregarInstruccion("CPRINT  ; Imprime el char en el tope de la pila");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetprintS@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("LOAD 3  ; Apila el parámetro");
        agregarInstruccion("SPRINT  ; Imprime el string en el tope de la pila");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetprintln@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("PRNLN  ; Imprime el caracter de nueva línea");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 0");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetprintCln@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("LOAD 3  ; Apila el parámetro");
        agregarInstruccion("CPRINT  ; Imprime el char en el tope de la pila");
        agregarInstruccion("PRNLN  ; Imprime el caracter de nueva línea");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetprintSln@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("LOAD 3  ; Apila el parámetro");
        agregarInstruccion("SPRINT  ; Imprime el booleano en el tope de la pila");
        agregarInstruccion("PRNLN  ; Imprime el caracter de nueva línea");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetread@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("READ  ; Lee un valor entero");
        agregarInstruccion("PUSH 48  ; Por ASCII");
        agregarInstruccion("SUB");
        agregarInstruccion("STORE 3  ; Devuelve el valor entero leído, poniendo el tope de la pila en la locación reservada");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 0");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetprintB@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("LOAD 3  ; Apila el parámetro");
        agregarInstruccion("BPRINT  ; Imprime el booleano en el tope de la pila");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetprintIln@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("LOAD 3  ; Apila el parámetro");
        agregarInstruccion("IPRINT  ; Imprime el entero en el tope de la pila");
        agregarInstruccion("PRNLN  ; Imprime el caracter de nueva línea");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetprintI@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("LOAD 3  ; Apila el parámetro");
        agregarInstruccion("IPRINT  ; Imprime el entero en el tope de la pila");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
        agregarInstruccion("lblMetprintBln@System: LOADFP  ; Apila el valor del registro fp");
        agregarInstruccion("LOADSP  ; Apila el valor del registro sp");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("LOAD 3  ; Apila el parámetro");
        agregarInstruccion("BPRINT  ; Imprime el booleano en el tope de la pila");
        agregarInstruccion("PRNLN  ; Imprime el caracter de nueva línea");
        agregarInstruccion("STOREFP  ; Almacena el tope de la pila en el registro fp");
        agregarInstruccion("RET 1");
        generarEspacioEnBlanco();
    }
}
