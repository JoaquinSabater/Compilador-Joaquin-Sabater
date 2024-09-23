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

            Token t = null;

            boolean bandera = true;

            try {
                AS = new AnalizadorSintactico(AL);
            } catch (ExcepcionSintactica e) {
                bandera = false;
                System.out.println(e.getMessage());
            }

            if(bandera){
                System.out.println("[SinErrores]");
            }

            gestorDeArchivos.close();
        } catch (IOException | ExcepcionLexica e) {
            throw new RuntimeException("\n" + e);
        }
    }
}