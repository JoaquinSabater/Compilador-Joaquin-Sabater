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

            Token t = AL.proximoToken();;

            while (!Objects.equals(t.getToken_id(), "eof")){
                t = AL.proximoToken();
            }

            gestorDeArchivos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExcepcionLexica e) {
            throw new RuntimeException(e);
        }
    }
}