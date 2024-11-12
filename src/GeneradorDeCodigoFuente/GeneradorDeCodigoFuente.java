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

    public void cerrar() throws IOException {
        buffer.close();
    }
}
