import AnalizadorLexico.*;
import sourcemanager.*;

import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        try {
            analizadorLexico AL = null;
            SourceManager gestorDeArchivos = null;

            gestorDeArchivos = new SourceManagerImpl();
            gestorDeArchivos.open(args[0]);
            AL = new analizadorLexico(gestorDeArchivos);

            Token t = null;

            do {
                try {
                    t = AL.proximoToken();
                    System.out.println("Token ID: " + t.getToken_id() + ", Lexema: " + t.getLexema() + ", Linea: " + t.getNro_linea());
                } catch (ExcepcionLexica e) {
                    System.err.println(e.getMessage());
                }
            } while (!Objects.equals(t.getToken_id(), "eof"));

            System.out.println("[SinErrores]");

            gestorDeArchivos.close();
        } catch (IOException | ExcepcionLexica e) {
            throw new RuntimeException(e);
        }
    }
}