package AnalizadorLexico;

import sourcemanager.SourceManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class analizadorLexico {

    //Arreglar la parte de " para hacer los strings

    String lexema;
    char caracterActual;
    SourceManager gestorDeFuente;

    private Map<String, String> palabrasClave;

    public analizadorLexico(SourceManager gestor) throws IOException {
        gestorDeFuente = gestor;
        actualizarCaracterActual();

        palabrasClave = new HashMap<>();
        palabrasClave.put("class", "pr_class");
        palabrasClave.put("interface", "pr_interface");
        palabrasClave.put("extends", "pr_extends");
        palabrasClave.put("implements", "pr_implements");
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
    }

    public Token proximoToken() throws IOException, ExcepcionLexica {
        lexema = "";
        return e0();
    }

    private void actualizarLexema() {
        lexema = lexema + caracterActual;
    }

    private void actualizarCaracterActual() throws IOException {
        caracterActual = gestorDeFuente.getNextChar();
    }

    private Token e0() throws IOException, ExcepcionLexica {
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
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), 1, lexema, this.lexema + " no es un caracter valido");
        }
    }

    private Token e37() throws IOException, ExcepcionLexica {
        if(this.caracterActual == '\\'){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e40();
        }
        else
        if(this.caracterActual == '\n' || this.caracterActual == '\'' || this.caracterActual == -1){
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), 1, lexema, this.lexema + "error en salto de linea");
        }
        else {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e38();
        }
    }
    private Token e38() throws IOException, ExcepcionLexica {
        if(this.caracterActual == '\''){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e39();
        }
        else
        if(this.caracterActual != gestorDeFuente.END_OF_FILE && this.caracterActual != '\n')
            this.actualizarLexema();
        throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), 1, lexema, this.lexema + "error en salto de linea");
    }
    private Token e39(){
        return new Token("charLiteral", this.lexema, gestorDeFuente.getLineNumber());
    }
    private Token e40() throws IOException, ExcepcionLexica {
        if(this.caracterActual !=  gestorDeFuente.END_OF_FILE && this.caracterActual != '\n'){
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e5();
        }
        else
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), 1, lexema, this.lexema + "error en salto de linea");
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
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), 1, lexema, this.lexema + "error en salto de linea");
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
        }
        else {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return e34();
        }
        //Enter es error
    }

    private Token e35() {
        return new Token("stringLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1() throws IOException, ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1_segundoDigito();
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_segundoDigito() throws IOException, ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1_tercerDigito();
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_tercerDigito() throws IOException, ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1_cuartoDigito();
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_cuartoDigito() throws IOException, ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1_quintoDigito();
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_quintoDigito() throws IOException, ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1_sextoDigito();
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_sextoDigito() throws IOException, ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1_septimoDigito();
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_septimoDigito() throws IOException, ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1_octavoDigito();
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_octavoDigito() throws IOException, ExcepcionLexica {
        if (Character.isDigit(caracterActual)) {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.e1_novenoDigito();
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e1_novenoDigito() throws ExcepcionLexica {
        if (Character.isDigit(this.caracterActual)) {
            this.actualizarLexema();
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), 1, lexema, this.lexema + " tiene mas de 9 digitos");
        } else
            return new Token("intLiteral", lexema, gestorDeFuente.getLineNumber());
    }


    private Token e2() throws IOException {
        if (Character.isUpperCase(caracterActual)) {
            return esMayuscula();
        } else {
            return esMinuscula();
        }
    }

    private Token esMinuscula() throws IOException {
        if (Character.isLetter(this.caracterActual) || Character.isDigit(this.caracterActual) || this.caracterActual == '_') {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return this.esMinuscula();
        } else if (palabrasClave.containsKey(this.lexema))
            return new Token(palabrasClave.get(lexema), this.lexema, gestorDeFuente.getLineNumber());
        else
            return new Token("IdMetVar", this.lexema, gestorDeFuente.getLineNumber());
    }

    private Token esMayuscula() throws IOException {
        if (Character.isLetter(this.caracterActual) || Character.isDigit(this.caracterActual) || this.caracterActual == '_') {
            this.actualizarLexema();
            this.actualizarCaracterActual();
            return esMayuscula();
        } else
            return new Token("idClase", this.lexema, gestorDeFuente.getLineNumber());
    }

    private Token e3() {
        return new Token("parentesisAbierto", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e4() {
        return new Token("parentesiscerrado", lexema, gestorDeFuente.getLineNumber());
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

    private Token e9() {
        return new Token("punto", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e10() throws IOException {
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

    private Token e12() throws IOException {
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

    private Token e14() throws IOException {
        if (caracterActual == '=') {
            actualizarLexema();
            actualizarCaracterActual();
            return e15();
        } else
            return new Token("negado", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e15() {
        return new Token("distinto", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e16() throws IOException {
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

    private Token e18() throws IOException {
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

    private Token e20() throws IOException {
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

    private Token e22() throws IOException {
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
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), 1, lexema, this.lexema + "Comentario sin cerrar");
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

    private Token e26() throws IOException, ExcepcionLexica {
        if (caracterActual == '&') {
            actualizarLexema();
            actualizarCaracterActual();
            return e27();
        } else
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), 1, lexema, this.lexema + "& no es un caracter valido");
    }

    private Token e27() {
        return new Token("and", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e28() throws IOException, ExcepcionLexica {
        if (caracterActual == '|') {
            actualizarLexema();
            actualizarCaracterActual();
            return e29();
        } else
            throw new ExcepcionLexica(gestorDeFuente.getLineNumber(), 1, lexema, this.lexema + "| no es un caracter valido");
    }

    private Token e29() {
        return new Token("or", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e30() {
        return new Token("dosPuntos", lexema, gestorDeFuente.getLineNumber());
    }
    private Token e31() {
        return new Token("porcentaje", lexema, gestorDeFuente.getLineNumber());
    }

    private Token e32() {
        return new Token("eof", lexema, gestorDeFuente.getLineNumber());
    }
}
