package AnalizadorSintactico;

import AnalizadorLexico.*;

import java.util.Objects;

public class AnalizadorSintactico {
    analizadorLexico lexico;

    Token tokenActual;

    public AnalizadorSintactico(analizadorLexico lexico) throws ExcepcionLexica, ExcepcionSintactica {
        this.lexico = lexico;
        this.tokenActual = lexico.proximoToken();
        Inicial();
    }

    private void match(String tokenId) throws ExcepcionSintactica, ExcepcionLexica {
        if (this.tokenActual.getToken_id().equals(tokenId)) {
            this.tokenActual = this.lexico.proximoToken();
            System.out.println("Token ID: " + this.tokenActual.getToken_id() + ", Lexema: " + this.tokenActual.getLexema() + ", Linea: " + this.tokenActual.getNro_linea());
        } else {
            throw new ExcepcionSintactica(this.tokenActual, tokenId);
        }
    }

    // <Inicial> ::= <ListaClases>
    private void Inicial() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Inicial");
        System.out.println("Token ID: " + this.tokenActual.getToken_id() + ", Lexema: " + this.tokenActual.getLexema() + ", Linea: " + this.tokenActual.getNro_linea());
        ListaClases();
    }

    // <ListaClases> ::= <Clase> <ListaClases> | ε
    private void ListaClases() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ListaClases");
        if (tokenActual.getToken_id().equals("pr_class")) {
            Clase();
            ListaClases();
        }
    }

    // <Clase> ::= class idClase <HerenciaOpcional> { <ListaMiembros> }
    private void Clase() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Clase");
        match("pr_class");
        match("idClase");
        HerenciaOpcional();
        match("llaveAbierta");
        ListaMiembros();
        match("llaveCerrada");
    }

    // <HerenciaOpcional> ::= extends idClase | ε
    private void HerenciaOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("HerenciaOpcional");
        if (tokenActual.getToken_id().equals("pr_extends")) {
            match("pr_extends");
            match("idClase");
        }
    }

    // <ListaMiembros> ::= <Miembro> <ListaMiembros> | ε
    private void ListaMiembros() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ListaMiembros");
        if (tokenActual.getToken_id().equals("pr_public") || tokenActual.getToken_id().equals("pr_static") || tokenActual.getToken_id().equals("idClase") || tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int") || tokenActual.getToken_id().equals("pr_void")) {
            Miembro();
            ListaMiembros();
        }
    }

    // <Miembro> ::= public <Constructor> | <EstaticoOpcional> <TipoMiembro> idMetVar <RestoAtributoMetodo>
    private void Miembro() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Miembro");
        if (tokenActual.getToken_id().equals("pr_public")) {
            Constructor();
        } else {
            EstaticoOpcional();
            TipoMiembro();
            match("idMetVar");
            RestoAtributoMetodo();
        }
    }

    // <RestoAtributoMetodo> ::= ; | <ArgsFormales> <Bloque>
    private void RestoAtributoMetodo() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("RestoAtributoMetodo");
        if (tokenActual.getToken_id().equals("puntoComa")) {
            match("puntoComa");
        } else {
            ArgsFormales();
            Bloque();
        }
    }

    // <Constructor> ::= public idClase <ArgsFormales> <Bloque>
    private void Constructor() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Constructor");
        match("pr_public");
        match("idClase");
        ArgsFormales();
        Bloque();
    }

    // <TipoMiembro> ::= void | <Tipo>
    private void TipoMiembro() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("TipoMiembro");
        if (tokenActual.getToken_id().equals("pr_void")) {
            match("pr_void");
        } else {
            Tipo();
        }
    }

    //<Tipo> ::= <TipoPrimitivo> | idClase
    private void Tipo() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Tipo");
        if (tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int")) {
            TipoPrimitivo();
        } else {
            match("idClase");
        }
    }

    // <TipoPrimitivo> ::= boolean | char | int
    private void TipoPrimitivo() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("TipoPrimitivo");
        if (tokenActual.getToken_id().equals("pr_boolean")) {
            match("pr_boolean");
        } else if (tokenActual.getToken_id().equals("pr_char")) {
            match("pr_char");
        } else if (tokenActual.getToken_id().equals("pr_int")) {
            match("pr_int");
        }
    }

    //<EstaticoOpcional> ::= static | e
    private void EstaticoOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("EstaticoOpcional");
        if (tokenActual.getToken_id().equals("pr_static")) {
            match("pr_static");
        }
    }

    // <ArgsFormales> ::= ( <ListaArgsFormalesOpcional> )
    private void ArgsFormales() throws ExcepcionSintactica, ExcepcionLexica {
        match("parentesisAbierto");
        ListaArgsFormalesOpcional();
        match("parentesisCerrado");
    }

    // <ListaArgsFormalesOpcional> ::= <ListaArgsFormales> | ε
    private void ListaArgsFormalesOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ListaArgsFormalesOpcional");
        if (tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int") || tokenActual.getToken_id().equals("idClase")) {
            ListaArgsFormales();
        }
    }

    // <ListaArgsFormales> ::= <ArgFormal> <RestoListaArgFormal>
    private void ListaArgsFormales() throws ExcepcionSintactica, ExcepcionLexica {
        ArgFormal();
        RestoListaArgFormal();
    }

    // <RestoListaArgFormal> ::= , <ListaArgsFormales> | ε
    private void RestoListaArgFormal() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("RestoListaArgFormal");
        if (tokenActual.getToken_id().equals("coma")) {
            match("coma");
            ListaArgsFormales();
        }
    }

    // <ArgFormal> ::= <Tipo> idMetVar
    private void ArgFormal() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ArgFormal");
        Tipo();
        match("idMetVar"); //Llego
    }

    // <Bloque> ::= { <ListaSentencias> }
    private void Bloque() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Bloque");
        match("llaveAbierta");
        ListaSentencias();
        match("llaveCerrada");
    }

    // <ListaSentencias> ::= <Sentencia> <ListaSentencias> | ε
    private void ListaSentencias() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ListaSentencias");
        if (tokenActual.getToken_id().equals("puntoComa") || tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("pr_var") || tokenActual.getToken_id().equals("pr_return") || tokenActual.getToken_id().equals("pr_break") || tokenActual.getToken_id().equals("pr_if") || tokenActual.getToken_id().equals("pr_while") || tokenActual.getToken_id().equals("pr_switch") || tokenActual.getToken_id().equals("llaveAbierta") || esExpresion(tokenActual)) {
            Sentencia(); //Aca tenia el primer debug
            ListaSentencias();
        }
    }

    /*
       <Sentencia> ::= ;
       <Sentencia> ::= <Expresion> ;
       <Sentencia> ::= <VarLocal> ;
       <Sentencia> ::= <Return> ;
       <Sentencia> ::= <Break> ;
       <Sentencia> ::= <If>
       <Sentencia> ::= <While>
       <Sentencia> ::= <Switch>
       <Sentencia> ::= <Bloque>

    */
    private void Sentencia() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Sentencia");
        if(esExpresion(tokenActual) && !Objects.equals(tokenActual.getToken_id(), "idMetVar")){
            Expresion();
            match("puntoComa");
        }else {
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
                    throw new ExcepcionSintactica(tokenActual, "sentencia invalida");
            }
        }
    }

    // <Asignacion> ::= <Expresion>
    private void Asignacion() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Asignacion");
        Expresion();
    }

    // <VarLocal> ::= var idMetVar = <ExpresionCompuesta>
    private void VarLocal() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("VarLocal");
        match("pr_var");
        match("idMetVar");
        match("asignacion");
        ExpresionCompuesta();
    }

    //<Return> ::= return <ExpresionOpcional>
    private void Return() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Return");
        match("pr_return");
        ExpresionOpcional();
    }

    // <Break> ::= break
    private void Break() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Break");
        match("pr_break");
    }

    // <ExpresionOpcional> ::= <Expresion> | ε
    private void ExpresionOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ExpresionOpcional");
        if (tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("intLiteral") || tokenActual.getToken_id().equals("charLiteral") || tokenActual.getToken_id().equals("pr_true") || tokenActual.getToken_id().equals("pr_false") || tokenActual.getToken_id().equals("pr_null") || tokenActual.getToken_id().equals("stringLiteral") || tokenActual.getToken_id().equals("parentesisAbierto") || tokenActual.getToken_id().equals("pr_this") || tokenActual.getToken_id().equals("pr_new") || tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("not")) {
            Expresion();
        }
    }

    // <If> ::= if ( <Expresion> ) <Sentencia> <IfPrima>
    private void If() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("If");
        match("pr_if");
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
        Sentencia();
        IfPrima();
    }

    // <IfPrima> ::= else <Sentencia> | ε
    private void IfPrima() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("IfPrima");
        if (tokenActual.getToken_id().equals("pr_else")) {
            match("pr_else");
            Sentencia();
        }
    }

    // <While> ::= while ( <Expresion> ) <Sentencia>
    private void While() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("While");
        match("pr_while");
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
        Sentencia();
    }

    // <Switch> ::= switch ( <Expresion> ) { <ListaSentenciasSwitch> }
    private void Switch() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Switch");
        match("pr_switch");
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
        match("llaveAbierta");
        ListaSentenciasSwitch();
        match("llaveCerrada");
    }

    // <ListaSentenciasSwitch> ::= <SentenciaSwitch> <ListaSentenciasSwitch> | ε
    private void ListaSentenciasSwitch() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ListaSentenciasSwitch");
        if (tokenActual.getToken_id().equals("pr_case") || tokenActual.getToken_id().equals("pr_default")) {
            SentenciaSwitch();
            ListaSentenciasSwitch();
        }
    }

    // <SentenciaSwitch> ::= case <LiteralPrimitivo> : <SentenciaOpcional> | default : <Sentencia>
    private void SentenciaSwitch() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("SentenciaSwitch");
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

    // <SentenciaOpcional> ::= <Sentencia> | ε
    private void SentenciaOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("SentenciaOpcional");
        if (tokenActual.getToken_id().equals("puntoComa") || tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("pr_var") || tokenActual.getToken_id().equals("pr_return") || tokenActual.getToken_id().equals("pr_break") || tokenActual.getToken_id().equals("pr_if") || tokenActual.getToken_id().equals("pr_while") || tokenActual.getToken_id().equals("pr_switch") || tokenActual.getToken_id().equals("llaveAbierta")) {
            Sentencia();
        }
    }

    // <Expresion>::= <ExpresionCompuesta> <ExpresionPrima>
    private void Expresion() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Expresion");
        ExpresionCompuesta();
        ExpresionPrima();
    }

    // <ExpresionPrima> ::= = <ExpresionCompuesta> | += <ExpresionCompuesta> | -= <ExpresionCompuesta> | ε
    private void ExpresionPrima() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ExpresionPrima");
        if (tokenActual.getToken_id().equals("asignacion") || tokenActual.getToken_id().equals("sumaAsignacion") || tokenActual.getToken_id().equals("restaAsignacion")) {
            OperadorAsignacion();
            ExpresionCompuesta();
        }
    }

    // <OperadorAsignacion> ::= = | += | -=
    private void OperadorAsignacion() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("OperadorAsignacion");
        if (tokenActual.getToken_id().equals("asignacion")) {
            match("asignacion");
        } else if (tokenActual.getToken_id().equals("sumaAsignacion")) {
            match("sumaAsignacion");
        } else if (tokenActual.getToken_id().equals("restaAsignacion")) {
            match("restaAsignacion");
        }
    }

    // <ExpresionCompuesta> ::= <ExpresionBasica> <ExpresionCompuestaPrima>
    private void ExpresionCompuesta() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ExpresionCompuesta");
        ExpresionBasica();
        ExpresionCompuestaPrima();
    }

    // <ExpresionCompuestaPrima> ::= <OperadorBinario> <ExpresionBasica> <ExpresionCompuestaPrima> | ε
    private void ExpresionCompuestaPrima() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ExpresionCompuestaPrima");
        if (tokenActual.getToken_id().equals("or") || tokenActual.getToken_id().equals("and") || tokenActual.getToken_id().equals("comparacion") || tokenActual.getToken_id().equals("distinto") || tokenActual.getToken_id().equals("menor") || tokenActual.getToken_id().equals("mayor") || tokenActual.getToken_id().equals("menorIgual") || tokenActual.getToken_id().equals("mayorIgual") || tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("multiplicacion") || tokenActual.getToken_id().equals("division") || tokenActual.getToken_id().equals("porcentaje")) {
            OperadorBinario();
            ExpresionBasica();
            ExpresionCompuestaPrima();
        }
    }

    // <OperadorBinario> ::= or | and | == | != | < | > | <= | >= | + | - | * | / | %
    private void OperadorBinario() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("OperadorBinario");
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

    // <ExpresionBasica> ::= <OperadorUnario> <Operando> | <Operando>
    private void ExpresionBasica() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ExpresionBasica");
        if (tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("not")) {
            OperadorUnario();
            Operando();
        } else {
            Operando();
        }
    }

    // <OperadorUnario> ::= + | - | !
    private void OperadorUnario() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("OperadorUnario");
        if (tokenActual.getToken_id().equals("suma")) {
            match("suma");
        } else if (tokenActual.getToken_id().equals("resta")) {
            match("resta");
        } else if (tokenActual.getToken_id().equals("not")) {
            match("not");
        }
    }

    // <Operando> ::= <Literal> | <Acceso>
    private void Operando() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Operando");
        if (tokenActual.getToken_id().equals("intLiteral") || tokenActual.getToken_id().equals("charLiteral") || tokenActual.getToken_id().equals("pr_true") || tokenActual.getToken_id().equals("pr_false") || tokenActual.getToken_id().equals("pr_null") || tokenActual.getToken_id().equals("stringLiteral")) {
            Literal();//Aca no esta entrando
        } else {
            Acceso();
        }
    }

    // <Literal> ::= <LiteralPrimitivo> | <LiteralObjeto>
    private void Literal() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Literal");
        if (esLiteralPrimitivo(tokenActual)) {
            LiteralPrimitivo();
        } else if (esLiteralObjeto(tokenActual)) {
            LiteralObjeto();
        } else {
            throw new ExcepcionSintactica(tokenActual, "literal válido");
        }
    }

    // <LiteralPrimitivo> ::= true | false | intLiteral | charLiteral
    private void LiteralPrimitivo() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("LiteralPrimitivo");
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
    private void LiteralObjeto() throws ExcepcionSintactica, ExcepcionLexica {
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

    // <Acceso> ::= <Primario> <EncadenadoOpcional>
    private void Acceso() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("Acceso");
        Primario();
        EncadenadoOpcional();
    }

    /*
    <Primario> ::= <AccesoThis>
    <Primario> ::= <AccesoVar>
    <Primario> ::= <AccesoConstructor>
    <Primario> ::= <AccesoMetodo>
    <Primario> ::= <AccesoMetodoEstatico>
    <Primario> ::= <ExpresionParentizada>
     */
    private void Primario() throws ExcepcionSintactica, ExcepcionLexica {
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
            case "pr_static":
                AccesoMetodoEstatico();
                break;
            case "idClase":
                AccesoMetodo();
                break;
            default:
                throw new ExcepcionSintactica(tokenActual, "primario válido");
        }
    }

    //<AccesoThis> ::= this
    private void AccesoThis() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("AccesoThis");
        match("pr_this");
    }

    //<AccesoVar> ::= idMetVar
    private void AccesoVar() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("AccesoVar");
        match("idMetVar");
    }

    //<AccesoConstructor> ::= new idClase <ArgsActuales>
    private void AccesoConstructor() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("AccesoConstructor");
        match("pr_new");
        match("idClase");
        ArgsActuales();
    }

    //<ExpresionParentizada> ::= ( <Expresion> )
    private void ExpresionParentizada() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ExpresionParentizada");
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
    }

    //<AccesoMetodo> ::= idMetVar <ArgsActuales>
    private void AccesoMetodo() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("AccesoMetodo");
        match("idMetVar");
        ArgsActuales();
    }

    //<AccesoMetodoEstatico> ::= idClase . idMetVar <ArgsActuales>
    private void AccesoMetodoEstatico() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("AccesoMetodoEstatico");
        match("idClase");
        match("punto");
        match("idMetVar");
        ArgsActuales();
    }

    //<ArgsActuales> ::= ( <ListaExpsOpcional> )
    private void ArgsActuales() throws ExcepcionSintactica, ExcepcionLexica {
        System.out.println("ArgsActuales");
        match("parentesisAbierto");
        ListaExpsOpcional();
        match("parentesisCerrado");
    }

    // <ListaExpsOpcional> ::= <ListaExps> | e
    private void ListaExpsOpcional() throws ExcepcionLexica, ExcepcionSintactica {
        if (esExpresion(tokenActual)) {
            ListaExps();
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
        }
    }

    // <EncadenadoOpcional>::= idMetVar<EncadenadoOpcionalPrima> | ε
    private void EncadenadoOpcional() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getToken_id().equals("punto")) {
            match("punto");
            match("idMetVar");
            EncadenadoOpcionalPrima();
        }
    }

    // <EncadenadoOpcionalPrima>::=  <EncadenadoOpcional> | <ArgsActuales> <EncadenadoOpcional>
    private void EncadenadoOpcionalPrima() throws ExcepcionLexica, ExcepcionSintactica { //Aca no entra con asigancion
        if (tokenActual.getToken_id().equals("parentesisAbierto")) {
            ArgsActuales();
            EncadenadoOpcional();
        } else if (tokenActual.getToken_id().equals("idMetVar")) {
            EncadenadoOpcional();
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
