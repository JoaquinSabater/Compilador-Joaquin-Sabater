package GeneradorDeCodigoFuente;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeneradorDeCodigoFuente {

    private File archivoSalida;
    private BufferedWriter buffer;

    private String modoActual;

    public GeneradorDeCodigoFuente() throws IOException {
        inicializador();
    }

    public void inicializador() throws IOException {
        archivoSalida = new File("codigoFuente.java");
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
    }

    public void generamosLlamadaMain() throws IOException {
        setModoCode();
        agregarInstruccion("PUSH lblmain");
        agregarInstruccion("CALL");
        agregarInstruccion("HALT");
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
    }
}
