package AnalizadorLexico;

import sourcemanager.SourceManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class analizadorLexico {

    String lexema;

    char caracterActual;
    SourceManager gestorDeFuente;

    private Map<String, String> palabrasClave;


    public analizadorLexico(SourceManager gestor) throws ExcepcionLexica {
        gestorDeFuente = gestor;
        actualizarCaracterActual();


        palabrasClave = new HashMap<>();
        palabrasClave.put("class", "pr_class");
        palabrasClave.put("extends", "pr_extends");
        palabrasClave.put("public", "pr_public");
        palabrasClave.put("static", "pr_static");
        palabrasClave.put("void", "pr_void");
        palabrasClave.put("boolean", "pr_boolean");
        palabrasClave.put("char", "pr_char");
        palabrasClave.put("int", "pr_int");
        palabrasClave.put("if", "pr_if");
        palabrasClave.put("else", "pr_else");
        palabrasClave.put("while", "pr_while");
        palabrasClave.put("return", "pr_return");
        palabrasClave.put("var", "pr_var");
        palabrasClave.put("this", "pr_this");
        palabrasClave.put("new", "pr_new");
        palabrasClave.put("null", "pr_null");
        palabrasClave.put("true", "pr_true");
        palabrasClave.put("false", "pr_false");
        palabrasClave.put("switch", "pr_switch");
        palabrasClave.put("case", "pr_case");
        palabrasClave.put("default", "pr_default");
        palabrasClave.put("break", "pr_break");
    }

    public Token proximoToken() throws ExcepcionLexica {
        try {
            lexema = "";
            return e0();
        } catch (IOException e) {
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), "", "IO Error", gestorDeFuente.getCurrentLine());
        }
    }

    private void actualizarLexema() {
        lexema = lexema + caracterActual;
    }

    private void actualizarCaracterActual() throws ExcepcionLexica {
        try {
            caracterActual = gestorDeFuente.getNextChar();
        } catch (IOException e) {
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), "", "IO Error", gestorDeFuente.getCurrentLine());
        }
    }

    private Token e0() throws IOException, ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e1();
        } else if (Character.isLetter(caracterActual) && Character.isLowerCase(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e2_minuscula();
        }else if (Character.isLetter(caracterActual) && Character.isUpperCase(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e2_mayuscula();
        }else if (caracterActual == '(') {
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
        }else if (caracterActual == '"') {
            actualizarLexema();
            actualizarCaracterActual();
            return e34();
        }else if (caracterActual == '\'') {
            actualizarLexema();
            actualizarCaracterActual();
            return e37();
        }else if (caracterActual == gestorDeFuente.END_OF_FILE) {
            return e32();
        }else if (Character.isWhitespace(caracterActual)) {
            actualizarCaracterActual();
            return e0();
        }else {
            actualizarLexema();
            actualizarCaracterActual();
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es un caracter valido", gestorDeFuente.getCurrentLine());
        }
    }

    private Token e37() throws ExcepcionLexica {
        if(this.caracterActual == '\\'){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e40();
        }
        else
        if(this.caracterActual == '\n' || this.caracterActual == gestorDeFuente.END_OF_FILE){
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + "No es un caracter valido", gestorDeFuente.getCurrentLine());
        }
        else {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e38();
        }
    }
    private Token e38() throws ExcepcionLexica {
        if(this.caracterActual == '\''){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e39();
        }
        else{
            actualizarLexema();
            actualizarCaracterActual();
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " un char literal no puede tener mas de un elemento ", gestorDeFuente.getCurrentLine());
        }
    }
    private Token e39(){
        return new Token("charLiteral", this.lexema, gestorDeFuente.getLineNumber());
    }
    private Token e40() throws ExcepcionLexica {
        if(this.caracterActual ==  'n' || this.caracterActual == '\\' || this.caracterActual == '\'' || this.caracterActual == 't'){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e38();
        }
        else{
            actualizarLexema();
            actualizarCaracterActual();
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es un caracter valido ", gestorDeFuente.getCurrentLine());
        }
    }

    private Token e34() throws IOException, ExcepcionLexica {
        if(this.caracterActual == '"'){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e35();
        }else if(caracterActual == '\\'){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return e36();
        } else if(this.caracterActual == '\n' || this.caracterActual == gestorDeFuente.END_OF_FILE){
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " error en el salto de linea ", gestorDeFuente.getCurrentLine());
        }
        else {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return e34();
        }
    }

    private Token e36() throws IOException, ExcepcionLexica {
        if(caracterActual == '"'){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return e34();
        } else {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return e34();
        }
    }

    private Token e35() {
        return new Token("stringLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1() throws IOException, ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (caracterActual == '.'){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1f();
        }
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            if (caracterActual == '.'){
                this.actualizarLexema();
                this.actualizarCaracterActual();
                if (caracterActual == '.'){
                    throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es valido poner mas de un . ", gestorDeFuente.getCurrentLine());
                }
                return this.e1f();
            }else {
                return this.e1_segundoDigito();
            }
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_segundoDigito() throws IOException, ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            if (caracterActual == '.'){
                this.actualizarLexema();
                this.actualizarCaracterActual();
                if (caracterActual == '.'){
                    throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es valido poner mas de un . ", gestorDeFuente.getCurrentLine());
                }
                return this.e1f();
            }else {
                return this.e1_tercerDigito();
            }
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_tercerDigito() throws IOException, ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            if (caracterActual == '.'){
                this.actualizarLexema();
                this.actualizarCaracterActual();
                if (caracterActual == '.'){
                    throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es valido poner mas de un . ", gestorDeFuente.getCurrentLine());
                }
                return this.e1f();
            }else {
                return this.e1_cuartoDigito();
            }
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_cuartoDigito() throws IOException, ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            if (caracterActual == '.'){
                this.actualizarLexema();
                this.actualizarCaracterActual();
                if (caracterActual == '.'){
                    throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es valido poner mas de un . ", gestorDeFuente.getCurrentLine());
                }
                return this.e1f();
            }else {
                return this.e1_quintoDigito();
            }
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_quintoDigito() throws IOException, ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            if (caracterActual == '.'){
                this.actualizarLexema();
                this.actualizarCaracterActual();
                if (caracterActual == '.'){
                    throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es valido poner mas de un . ", gestorDeFuente.getCurrentLine());
                }
                return this.e1f();
            }else {
                return this.e1_sextoDigito();
            }
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_sextoDigito() throws IOException, ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            if (caracterActual == '.'){
                this.actualizarLexema();
                this.actualizarCaracterActual();
                if (caracterActual == '.'){
                    throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es valido poner mas de un . ", gestorDeFuente.getCurrentLine());
                }
                return this.e1f();
            }else {
                return this.e1_septimoDigito();
            }
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_septimoDigito() throws IOException, ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            if (caracterActual == '.'){
                this.actualizarLexema();
                this.actualizarCaracterActual();
                if (caracterActual == '.'){
                    throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es valido poner mas de un . ", gestorDeFuente.getCurrentLine());
                }
                return this.e1f();
            }else {
                return this.e1_octavoDigito();
            }
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_octavoDigito() throws ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            if (caracterActual == '.'){
                this.actualizarLexema();
                this.actualizarCaracterActual();
                if (caracterActual == '.'){
                    throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es valido poner mas de un . ", gestorDeFuente.getCurrentLine());

                }
                return this.e1f();
            }else {
                return this.e1_novenoDigito();
            }
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_novenoDigito() throws ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (caracterActual == '.'){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            if (caracterActual == '.'){
                throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " no es valido poner mas de un . ", gestorDeFuente.getCurrentLine());

            }
            return this.e1f();
        }else {
            if (Character.isDigit(this.caracterActual)) {
                this.actualizarLexema();
                this.actualizarCaracterActual();
                throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " tiene mas de 9 digitos ", gestorDeFuente.getCurrentLine());
            } else
                return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
        }
    }

    private Token e1f() throws ExcepcionLexica {
        if (caracterActual == 'e' || caracterActual == 'E'){
            actualizarLexema();
            actualizarCaracterActual();
            return e2f();
        }
        if (Character.isDigit(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e1f();
        } else
            return new Token("floatLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e2f() throws ExcepcionLexica {
        if (caracterActual == '+' || caracterActual == '-'){
            actualizarLexema();
            actualizarCaracterActual();
            return e3f();
        }
        if (Character.isDigit(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e4f();
        } else
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " despues de e tiene que haber digitos ", gestorDeFuente.getCurrentLine());
    }

    private Token e4f() throws ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e4f();
        } else
            return new Token("floatLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e3f() throws ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e5f();
        } else
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " despues de + o - tiene que haber digitos ", gestorDeFuente.getCurrentLine());
    }

    private Token e5f() throws ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            actualizarLexema();
            actualizarCaracterActual();
            return e5f();
        } else
            return new Token("floatLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e2_minuscula() throws ExcepcionLexica {
        if (Character.isLetter(this.caracterActual) || Character.isDigit(this.caracterActual) || this.caracterActual == '_') {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e2_minuscula();
        } else if (palabrasClave.containsKey(this.lexema))
            return new Token(palabrasClave.get(lexema), this.lexema, gestorDeFuente.getLineNumber());
        else
            return new Token("idMetVar", this.lexema, gestorDeFuente.getLineNumber());
    }

    private Token e2_mayuscula() throws ExcepcionLexica {
        if (Character.isLetter(this.caracterActual) || Character.isDigit(this.caracterActual) || this.caracterActual == '_') {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return e2_mayuscula();
        } else
            return new Token("idClase", this.lexema, gestorDeFuente.getLineNumber());
    }

    private Token e3() {
        return new Token("parentesisAbierto", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e4() {
        return new Token("parentesisCerrado", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e5() {
        return new Token("llaveAbierta", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e6() {
        return new Token("llaveCerrada", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e7() {
        return new Token("puntoComa", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e8() {
        return new Token("coma", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e9() throws ExcepcionLexica {
        if(Character.isDigit(caracterActual)){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1f();
        }else {
            return new Token("punto", lexema, gestorDeFuente.getLineNumber());
        }
    }

    private Token e10() throws ExcepcionLexica {
        if (this.caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e11();
        } else
            return new Token("mayor", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e11() {
        return new Token("mayorIgual", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e12() throws ExcepcionLexica {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e13();
        } else
            return new Token("menor", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e13() {
        return new Token("menorIgual", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e14() throws ExcepcionLexica {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e15();
        } else
            return new Token("not", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e15() {
        return new Token("distinto", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e16() throws ExcepcionLexica {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e17();
        } else
            return new Token("asignacion", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e17() {
        return new Token("comparacion", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e18() throws ExcepcionLexica {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e19();
        } else
            return new Token("suma", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e19() {
        return new Token("sumaAsignacion", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e20() throws ExcepcionLexica {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e21();
        } else
            return new Token("resta", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e21() {
        return new Token("restaAsignacion", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e22()  {
        return new Token("multiplicacion", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e24() throws IOException, ExcepcionLexica {
        if (caracterActual == '/') {
            actualizarLexema();
            actualizarCaracterActual();
            return e25();
        }else if(caracterActual == '*'){
            actualizarLexema();
            actualizarCaracterActual();
            return e33();
        }
        else{
            return new Token("division", lexema, gestorDeFuente.getLineNumber());
        }
    }

    private Token e33() throws IOException, ExcepcionLexica {
        if(caracterActual == '*'){
            actualizarCaracterActual();
            if(caracterActual == '/'){
                actualizarCaracterActual();
                lexema = "";
                return e0();
            } else{
                actualizarCaracterActual();
                return e33();
            }
        }
        if(caracterActual == gestorDeFuente.END_OF_FILE){
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " es el final del archivo ", gestorDeFuente.getCurrentLine());
        } else{
            actualizarCaracterActual();
            return e33();
        }
    }

    private Token e25() throws IOException, ExcepcionLexica {
        if(this.caracterActual != '\n' && this.caracterActual != gestorDeFuente.END_OF_FILE){
            this.actualizarCaracterActual();
            return this.e25();
        }else {
            if(this.caracterActual == gestorDeFuente.END_OF_FILE){
                lexema = "";
                return this.e32();
            }else{
                this.actualizarCaracterActual();
                lexema = "";
                return this.e0();
            }
        }
    }

    private Token e26() throws ExcepcionLexica {
        if (caracterActual == '&') {
            actualizarLexema();
            actualizarCaracterActual();
            return e27();
        } else{
            actualizarLexema();
            actualizarCaracterActual();
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " & no es un error valido ", gestorDeFuente.getCurrentLine());
        }
    }

    private Token e27() {
        return new Token("and", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e28() throws ExcepcionLexica {
        if (caracterActual == '|') {
            actualizarLexema();
            actualizarCaracterActual();
            return e29();
        } else{
            actualizarLexema();
            actualizarCaracterActual();
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), gestorDeFuente.getLineIndexNumber(), lexema, this.lexema + " | no es un error valido ", gestorDeFuente.getCurrentLine());
        }
    }

    private Token e29() {
        return new Token("or", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e30() {
        return new Token("porcentaje", lexema, gestorDeFuente.getLineNumber());
    }
    private Token e31() {
        return new Token("dosPuntos", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e32() {
        return new Token("eof", lexema, gestorDeFuente.getLineNumber());
    }

    public boolean esEOF(){
        return caracterActual == gestorDeFuente.END_OF_FILE;
    }
}

