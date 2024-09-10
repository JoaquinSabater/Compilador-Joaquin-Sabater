package AnalizadorSintactico;

import AnalizadorLexico.*;

public class AnalizadorSintactico {
    analizadorLexico lexico;

    Token tokenActual;

    public AnalizadorSintactico(analizadorLexico lexico) throws ExcepcionLexica, ExcepcionSintactica {
        this.lexico = lexico;
        this.tokenActual = lexico.proximoToken();
        Inicial();
    }

    void match(String tokenId) throws ExcepcionSintactica, ExcepcionLexica {
        if (this.tokenActual.getToken_id().equals(tokenId)) {
            this.tokenActual = this.lexico.proximoToken();
            System.out.println("Match: " + tokenActual.getToken_id());
        } else {
            throw new ExcepcionSintactica(this.tokenActual, tokenId);
        }
    }

    //<Inicial> ::= <ListaClases>
    public void Inicial() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Inicial");
        ListaClases();
    }

    //<ListaClases> ::= <Clase> <ListaClases> | e
    private void ListaClases() throws ExcepcionLexica, ExcepcionSintactica {
        System.out.println("Lista Clases");
        if (this.tokenActual.getToken_id().equals("pr_class") || this.tokenActual.getToken_id().equals("pr_interface")) {
            System.out.println("hola");
            Clase();
            ListaClases();
        }else {
            //e
        }
    }
    //<Clase> ::= class idClase <HerenciaOpcional> { <ListaMiembros> }
    private void Clase() throws ExcepcionLexica, ExcepcionSintactica {
        System.out.println("Clase");
        if(tokenActual.getToken_id().equals("pr_class")) {
            ClaseConcreta();
        }else if(tokenActual.getToken_id().equals("pr_interface")){
            Interface();
        }else
            throw new ExcepcionSintactica(tokenActual, "class o interface");
    }

    private void Interface() throws ExcepcionLexica, ExcepcionSintactica {
        System.out.println("Interface");
        match("pr_interface");
        match("idClase");
        match("llave_izq");
        ListaMiembros();
        match("llave_der");
    }

    private void ListaMiembros() {
        System.out.println("Lista Miembros");
    }

    private void ClaseConcreta() throws ExcepcionLexica, ExcepcionSintactica {
        System.out.println("Clase Concreta");
        match("pr_class");
        match("idClase");
        //HerenciaOpcional
        match("llave_izq");
        ListaMiembros();
        match("llave_der");
    }
}
