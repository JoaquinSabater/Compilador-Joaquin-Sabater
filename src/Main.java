import AnalizadorLexico.*;
import sourcemanager.*;
import AnalizadorSintactico.*;
import AnalizadorSintactico.ExcepcionSintactica;

import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        try {
            analizadorLexico AL = null;
            AnalizadorSintactico AS = null;
            SourceManager gestorDeArchivos = null;

            gestorDeArchivos = new SourceManagerImpl();
            gestorDeArchivos.open(args[0]);
            AL = new analizadorLexico(gestorDeArchivos);

            AS = new AnalizadorSintactico(AL);

            Token t = null;

            boolean bandera = false;

            if(!bandera){
                System.out.println("[SinErrores]");
            }

            gestorDeArchivos.close();
        } catch (IOException | ExcepcionLexica | ExcepcionSintactica e) {
            throw new RuntimeException("\n" + e);
        }
    }
}