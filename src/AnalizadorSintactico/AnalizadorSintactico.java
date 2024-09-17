package AnalizadorSintactico;

import AnalizadorLexico.*;

import java.util.HashMap;
import java.util.Map;

public class AnalizadorSintactico {
    analizadorLexico lexico;

    Token tokenActual;

    private Map<String, String> palabrasClave;

    public AnalizadorSintactico(analizadorLexico lexico) throws ExcepcionLexica, ExcepcionSintactica {
        this.lexico = lexico;
        this.tokenActual = lexico.proximoToken();
        Inicial();

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

    void match(String tokenId) throws ExcepcionSintactica, ExcepcionLexica {
        if (this.tokenActual.getToken_id().equals(tokenId)) {
            this.tokenActual = this.lexico.proximoToken();
        } else {
            throw new ExcepcionSintactica(this.tokenActual, tokenId);
        }
    }

    void Inicial() throws ExcepcionSintactica, ExcepcionLexica {
        ListaClases();
    }

    void ListaClases() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_class")) {
            Clase();
            ListaClases();
        }
    }

    void Clase() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_class");
        match("idClase");
        HerenciaOpcional();
        match("llaveAbierta");
        ListaMiembros();
        match("llaveCerrada");
    }

    void HerenciaOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_extends")) {
            match("pr_extends");
            match("idClase");
        }
    }

    void ListaMiembros() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_public") || tokenActual.getToken_id().equals("pr_static") || tokenActual.getToken_id().equals("idClase") || tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int") || tokenActual.getToken_id().equals("pr_void")) {
            Miembro();
            ListaMiembros();
        }
    }

    void Miembro() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_public")) {
            Constructor();
        } else {
            EstaticoOpcional();
            TipoMiembro();
            match("idMetVar");
            RestoAtributoMetodo();
        }
    }

    void RestoAtributoMetodo() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("puntoComa")) {
            match("puntoComa");
        } else {
            ArgsFormales();
            Bloque();
        }
    }

    void Constructor() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_public");
        match("idClase");
        ArgsFormales();
        Bloque();
    }

    void TipoMiembro() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_void")) {
            match("pr_void");
        } else {
            Tipo();
        }
    }

    void Tipo() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int")) {
            TipoPrimitivo();
        } else {
            match("idClase");
        }
    }

    void TipoPrimitivo() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_boolean")) {
            match("pr_boolean");
        } else if (tokenActual.getToken_id().equals("pr_char")) {
            match("pr_char");
        } else if (tokenActual.getToken_id().equals("pr_int")) {
            match("pr_int");
        }
    }

    void EstaticoOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_static")) {
            match("pr_static");
        }
    }

    void ArgsFormales() throws ExcepcionSintactica, ExcepcionLexica {
        match("parentesisAbierto");
        ListaArgsFormalesOpcional();
        match("parentesisCerrado");
    }

    void ListaArgsFormalesOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int") || tokenActual.getToken_id().equals("idClase")) {
            ListaArgsFormales();
        }
    }

    void ListaArgsFormales() throws ExcepcionSintactica, ExcepcionLexica {
        ArgFormal();
        RestoListaArgFormal();
    }

    void RestoListaArgFormal() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("coma")) {
            match("coma");
            ListaArgsFormales();
        }
    }

    void ArgFormal() throws ExcepcionSintactica, ExcepcionLexica {
        Tipo();
        match("idMetVar");
    }

    void Bloque() throws ExcepcionSintactica, ExcepcionLexica {
        match("llaveAbierta");
        ListaSentencias();
        match("llaveCerrada");
    }

    void ListaSentencias() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("puntoComa") || tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("pr_var") || tokenActual.getToken_id().equals("pr_return") || tokenActual.getToken_id().equals("pr_break") || tokenActual.getToken_id().equals("pr_if") || tokenActual.getToken_id().equals("pr_while") || tokenActual.getToken_id().equals("pr_switch") || tokenActual.getToken_id().equals("llaveAbierta")) {
            Sentencia();
            ListaSentencias();
        }
    }

    void Sentencia() throws ExcepcionSintactica, ExcepcionLexica {
        switch (tokenActual.getToken_id()) {
            case "puntoComa":
                match("puntoComa");
                break;
            case "idMetVar":
                Asignacion();
                match("puntoComa");
                break;
            case "pr_var":
                VarLocal();
                match("puntoComa");
                break;
            case "pr_return":
                Return();
                match("puntoComa");
                break;
            case "pr_break":
                Break();
                match("puntoComa");
                break;
            case "pr_if":
                If();
                break;
            case "pr_while":
                While();
                break;
            case "pr_switch":
                Switch();
                break;
            case "llaveAbierta":
                Bloque();
                break;
            default:
                throw new ExcepcionSintactica(tokenActual, "sentencia válida");
        }
    }

    void Asignacion() throws ExcepcionSintactica, ExcepcionLexica {
        Expresion();
    }

    void VarLocal() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_var");
        match("idMetVar");
        match("igual");
        ExpresionCompuesta();
    }

    void Return() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_return");
        ExpresionOpcional();
    }

    void Break() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_break");
    }

    void ExpresionOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("intLiteral") || tokenActual.getToken_id().equals("charLiteral") || tokenActual.getToken_id().equals("pr_true") || tokenActual.getToken_id().equals("pr_false") || tokenActual.getToken_id().equals("pr_null") || tokenActual.getToken_id().equals("stringLiteral") || tokenActual.getToken_id().equals("parentesisAbierto") || tokenActual.getToken_id().equals("pr_this") || tokenActual.getToken_id().equals("pr_new") || tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("not")) {
            Expresion();
        }
    }

    void If() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_if");
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
        Sentencia();
        IfPrima();
    }

    void IfPrima() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_else")) {
            match("pr_else");
            Sentencia();
        }
    }

    void While() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_while");
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
        Sentencia();
    }

    void Switch() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_switch");
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
        match("llaveAbierta");
        ListaSentenciasSwitch();
        match("llaveCerrada");
    }

    void ListaSentenciasSwitch() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_case") || tokenActual.getToken_id().equals("pr_default")) {
            SentenciaSwitch();
            ListaSentenciasSwitch();
        }
    }

    void SentenciaSwitch() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_case")) {
            match("pr_case");
            LiteralPrimitivo();
            match("dosPuntos");
            SentenciaOpcional();
        } else if (tokenActual.getToken_id().equals("pr_default")) {
            match("pr_default");
            match("dosPuntos");
            Sentencia();
        }
    }

    void SentenciaOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("puntoComa") || tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("pr_var") || tokenActual.getToken_id().equals("pr_return") || tokenActual.getToken_id().equals("pr_break") || tokenActual.getToken_id().equals("pr_if") || tokenActual.getToken_id().equals("pr_while") || tokenActual.getToken_id().equals("pr_switch") || tokenActual.getToken_id().equals("llaveAbierta")) {
            Sentencia();
        }
    }

    void Expresion() throws ExcepcionSintactica, ExcepcionLexica {
        ExpresionCompuesta();
        ExpresionPrima();
    }

    void ExpresionPrima() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("igual") || tokenActual.getToken_id().equals("sumaAsignacion") || tokenActual.getToken_id().equals("restaAsignacion")) {
            OperadorAsignacion();
            ExpresionCompuesta();
        }
    }

    void OperadorAsignacion() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("igual")) {
            match("igual");
        } else if (tokenActual.getToken_id().equals("sumaAsignacion")) {
            match("sumaAsignacion");
        } else if (tokenActual.getToken_id().equals("restaAsignacion")) {
            match("restaAsignacion");
        }
    }

    void ExpresionCompuesta() throws ExcepcionSintactica, ExcepcionLexica {
        ExpresionBasica();
        ExpresionCompuestaPrima();
    }

    void ExpresionCompuestaPrima() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("or") || tokenActual.getToken_id().equals("and") || tokenActual.getToken_id().equals("comparacion") || tokenActual.getToken_id().equals("distinto") || tokenActual.getToken_id().equals("menor") || tokenActual.getToken_id().equals("mayor") || tokenActual.getToken_id().equals("menorIgual") || tokenActual.getToken_id().equals("mayorIgual") || tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("multiplicacion") || tokenActual.getToken_id().equals("division") || tokenActual.getToken_id().equals("porcentaje")) {
            OperadorBinario();
            ExpresionBasica();
            ExpresionCompuestaPrima();
        }
    }

    void OperadorBinario() throws ExcepcionSintactica, ExcepcionLexica {
        switch (tokenActual.getToken_id()) {
            case "or":
                match("or");
                break;
            case "and":
                match("and");
                break;
            case "comparacion":
                match("comparacion");
                break;
            case "distinto":
                match("distinto");
                break;
            case "menor":
                match("menor");
                break;
            case "mayor":
                match("mayor");
                break;
            case "menorIgual":
                match("menorIgual");
                break;
            case "mayorIgual":
                match("mayorIgual");
                break;
            case "suma":
                match("suma");
                break;
            case "resta":
                match("resta");
                break;
            case "multiplicacion":
                match("multiplicacion");
                break;
            case "division":
                match("division");
                break;
            case "porcentaje":
                match("porcentaje");
                break;
            default:
                throw new ExcepcionSintactica(tokenActual, "operador binario válido");
        }
    }

    void ExpresionBasica() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("not")) {
            OperadorUnario();
            Operando();
        } else {
            Operando();
        }
    }

    void OperadorUnario() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("suma")) {
            match("suma");
        } else if (tokenActual.getToken_id().equals("resta")) {
            match("resta");
        } else if (tokenActual.getToken_id().equals("not")) {
            match("not");
        }
    }

    void Operando() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("intLiteral") || tokenActual.getToken_id().equals("charLiteral") || tokenActual.getToken_id().equals("pr_true") || tokenActual.getToken_id().equals("pr_false") || tokenActual.getToken_id().equals("pr_null") || tokenActual.getToken_id().equals("stringLiteral")) {
            Literal();
        } else {
            Acceso();
        }
    }

    // <Literal> ::= <LiteralPrimitivo> | <LiteralObjeto>
    void Literal() throws ExcepcionSintactica, ExcepcionLexica {
        if (esLiteralPrimitivo(tokenActual)) {
            LiteralPrimitivo();
        } else if (esLiteralObjeto(tokenActual)) {
            LiteralObjeto();
        } else {
            throw new ExcepcionSintactica(tokenActual, "literal válido");
        }
    }

    // <LiteralPrimitivo> ::= true | false | intLiteral | charLiteral
    void LiteralPrimitivo() throws ExcepcionSintactica, ExcepcionLexica {
        switch (tokenActual.getToken_id()) {
            case "pr_true":
                match("pr_true");
                break;
            case "pr_false":
                match("pr_false");
                break;
            case "intLiteral":
                match("intLiteral");
                break;
            case "charLiteral":
                match("charLiteral");
                break;
            default:
                throw new ExcepcionSintactica(tokenActual, "literal primitivo válido");
        }
    }

    // <LiteralObjeto> ::= null | stringLiteral
    void LiteralObjeto() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_null")) {
            match("pr_null");
        } else if (tokenActual.getToken_id().equals("stringLiteral")) {
            match("stringLiteral");
        } else {
            throw new ExcepcionSintactica(tokenActual, "literal objeto válido");
        }
    }

    // Método auxiliar para verificar si el token actual es un literal primitivo
    private boolean esLiteralPrimitivo(Token token) {
        return token.getToken_id().equals("pr_true") || token.getToken_id().equals("pr_false") ||
                token.getToken_id().equals("intLiteral") || token.getToken_id().equals("charLiteral");
    }

    // Método auxiliar para verificar si el token actual es un literal objeto
    private boolean esLiteralObjeto(Token token) {
        return token.getToken_id().equals("pr_null") || token.getToken_id().equals("stringLiteral");
    }

    void Acceso() throws ExcepcionSintactica, ExcepcionLexica {
        Primario();
        EncadenadoOpcional();
    }

    void Primario() throws ExcepcionSintactica, ExcepcionLexica {
        switch (tokenActual.getToken_id()) {
            case "pr_this":
                AccesoThis();
                break;
            case "idMetVar":
                AccesoVar();
                break;
            case "pr_new":
                AccesoConstructor();
                break;
            case "parentesisAbierto":
                ExpresionParentizada();
                break;
            default:
                throw new ExcepcionSintactica(tokenActual, "primario válido");
        }
    }

    void AccesoThis() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_this");
    }

    void AccesoVar() throws ExcepcionSintactica, ExcepcionLexica {
        match("idMetVar");
    }

    void AccesoConstructor() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_new");
        match("idClase");
        ArgsActuales();
    }

    void ExpresionParentizada() throws ExcepcionSintactica, ExcepcionLexica {
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
    }

    void AccesoMetodo() throws ExcepcionSintactica, ExcepcionLexica {
        match("idMetVar");
        ArgsActuales();
    }

    void AccesoMetodoEstatico() throws ExcepcionSintactica, ExcepcionLexica {
        match("idClase");
        match("punto");
        match("idMetVar");
        ArgsActuales();
    }

    void ArgsActuales() throws ExcepcionSintactica, ExcepcionLexica {
        match("parentesisAbierto");
        ListaExpsOpcional();
        match("parentesisCerrado");
    }

    // <ListaExpsOpcional> ::= <ListaExps> | e
    private void ListaExpsOpcional() throws ExcepcionLexica, ExcepcionSintactica {
        if (esExpresion(tokenActual)) {
            ListaExps();
        } else {
            // e
        }
    }

    // <ListaExps> ::= <Expresion> <ListaExpsPrima>
    private void ListaExps() throws ExcepcionLexica, ExcepcionSintactica {
        Expresion();
        ListaExpsPrima();
    }

    // <ListaExpsPrima> ::= , <ListaExps> | ε
    private void ListaExpsPrima() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getToken_id().equals("coma")) {
            match("coma");
            ListaExps();
        } else {
            // ε
        }
    }

    // <EncadenadoOpcional>::= idMetVar<EncadenadoOpcionalPrima> | ε
    private void EncadenadoOpcional() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getToken_id().equals("idMetVar")) {
            match("idMetVar");
            EncadenadoOpcionalPrima();
        } else {
            // ε
        }
    }

    // <EncadenadoOpcionalPrima>::=  <EncadenadoOpcional> | <ArgsActuales> <EncadenadoOpcional>
    private void EncadenadoOpcionalPrima() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getToken_id().equals("parentesisAbierto")) {
            ArgsActuales();
            EncadenadoOpcional();
        } else if (tokenActual.getToken_id().equals("idMetVar")) {
            EncadenadoOpcional();
        } else {
            // ε
        }
    }

    // Método auxiliar para verificar si el token actual puede ser el inicio de una expresión
    private boolean esExpresion(Token token) {
        return token.getToken_id().equals("idMetVar") || token.getToken_id().equals("intLiteral") ||
                token.getToken_id().equals("charLiteral") || token.getToken_id().equals("stringLiteral") ||
                token.getToken_id().equals("pr_true") || token.getToken_id().equals("pr_false") ||
                token.getToken_id().equals("pr_null") || token.getToken_id().equals("pr_this") ||
                token.getToken_id().equals("pr_new") || token.getToken_id().equals("parentesisAbierto") ||
                token.getToken_id().equals("suma") || token.getToken_id().equals("resta") ||
                token.getToken_id().equals("not");
    }

}
