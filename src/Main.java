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
            AL = new analizadorLexico(gestorDeArchivos);

            gestorDeArchivos.open(args[0]);

            while (!Objects.equals(AL.proximoToken().getToken_id(), "eof")){
                AL.proximoToken();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExcepcionLexica e) {
            throw new RuntimeException(e);
        }
    }
}