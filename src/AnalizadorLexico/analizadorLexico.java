package AnalizadorLexico;

import sourcemanager.SourceManager;

import java.io.IOException;

public class analizadorLexico {
    String lexema;
    char caracterActual;
    SourceManager gestorDeFuente;

    public analizadorLexico(SourceManager gestor) throws IOException {
        gestorDeFuente = gestor;
        actualizarCaracterActual();
    }

    public Token proximoToken() throws IOException {
        lexema = "";
        return e0();
    }

    private void actualizarLexema(){
        lexema = lexema+caracterActual;
    }

    private void actualizarCaracterActual() throws IOException {
        caracterActual = gestorDeFuente.getNextChar();
    }

    private Token e0() throws IOException {
        if (Character.isDigit(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e1();
        } else if (Character.isLetter(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e2();
        } else if (caracterActual == '(') {
            actualizarLexema();
            actualizarCaracterActual();
            return e3();
        } else if (caracterActual == ')') {
            actualizarLexema();
            actualizarCaracterActual();
            return e4();
        } else if (caracterActual == '{') {
            actualizarLexema();
            actualizarCaracterActual();
            return e5();
        } else if (caracterActual == '}') {
            actualizarLexema();
            actualizarCaracterActual();
            return e6();
        } else if (caracterActual == ';') {
            actualizarLexema();
            actualizarCaracterActual();
            return e7();
        } else if (caracterActual == ',') {
            actualizarLexema();
            actualizarCaracterActual();
            return e8();
        } else if (caracterActual == '.') {
            actualizarLexema();
            actualizarCaracterActual();
            return e9();
        } else if (caracterActual == '>') {
            actualizarLexema();
            actualizarCaracterActual();
            return e10();
        } else if (caracterActual == '<') {
            actualizarLexema();
            actualizarCaracterActual();
            return e12();
        } else if (caracterActual == '!') {
            actualizarLexema();
            actualizarCaracterActual();
            return e14();
        } else if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e16();
        } else if (caracterActual == '+') {
            actualizarLexema();
            actualizarCaracterActual();
            return e18();
        } else if (caracterActual == '-') {
            actualizarLexema();
            actualizarCaracterActual();
            return e20();
        } else if (caracterActual == '*') {
            actualizarLexema();
            actualizarCaracterActual();
            return e22();
        } else if (caracterActual == '/') {
            actualizarLexema();
            actualizarCaracterActual();
            return e24();
        } else if (caracterActual == '&') {
            actualizarLexema();
            actualizarCaracterActual();
            return e26();
        } else if (caracterActual == '|') {
            actualizarLexema();
            actualizarCaracterActual();
            return e28();
        } else if (caracterActual == '%') {
            actualizarLexema();
            actualizarCaracterActual();
            return e30();
        } else if (caracterActual == ':') {
            actualizarLexema();
            actualizarCaracterActual();
            return e31();
        }
        return null;
    }

    private Token e1() {
        return null;
    }

    private Token e2() {
        return null;
    }

    private Token e3() {
        return null;
    }

    private Token e4() {
        return null;
    }

    private Token e5() {
        return null;
    }

    private Token e6() {
        return null;
    }

    private Token e7() {
        return null;
    }

    private Token e8() {
        return null;
    }

    private Token e9() {
        return null;
    }

    private Token e10() {
        return null;
    }

    private Token e12() {
        return null;
    }

    private Token e14() {
        return null;
    }

    private Token e16() {
        return null;
    }

    private Token e18() {
        return null;
    }

    private Token e20() {
        return null;
    }

    private Token e22() {
        return null;
    }

    private Token e24() {
        return null;
    }

    private Token e26() {
        return null;
    }

    private Token e28() {
        return null;
    }

    private Token e30() {
        return null;
    }
    private Token e31() {
        return null;
    }

    private Token e32() {
        return null;
    }
}
