package AnalizadorSintactico;

import AST.Expresiones.*;
import AST.Sentencias.*;
import AnalizadorLexico.*;
import AnalizadorSemantico.*;

import java.util.Objects;

public class AnalizadorSintactico {
    analizadorLexico lexico;

    Token tokenActual;

    TS ts;

    boolean noEsMetodo = true;

    boolean esStatic = false;

    boolean esWhileOrSwich = false;

    Token tipoAuxiliar;

    Token nombreAuxiliar;

    NodoBloque bloqueActual;

    NodoSwitch nodoSwitchActual;

    public AnalizadorSintactico(analizadorLexico lexico,TS ts) throws ExcepcionLexica, ExcepcionSintactica, ExcepcionSemantica {
        this.lexico = lexico;
        this.tokenActual = lexico.proximoToken();
        this.ts = ts;
        Inicial();
    }

    private void match(String tokenId) throws ExcepcionSintactica, ExcepcionLexica {
        if (this.tokenActual.getToken_id().equals(tokenId)) {
            this.tokenActual = this.lexico.proximoToken();
        } else {
            throw new ExcepcionSintactica(this.tokenActual, tokenId);
        }
    }

    // <Inicial> ::= <ListaClases>
    private void Inicial() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        ListaClases();
    }

    // <ListaClases> ::= <Clase> <ListaClases> | ε
    private void ListaClases() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (tokenActual.getToken_id().equals("pr_class")) {
            Clase();
            ListaClases();
        }
    }

    // <Clase> ::= class idClase <HerenciaOpcional> { <ListaMiembros> }
    private void Clase() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        match("pr_class");
        ts.insertarClase(tokenActual);
        match("idClase");
        HerenciaOpcional();
        match("llaveAbierta");
        ListaMiembros();
        match("llaveCerrada");
    }

    // <HerenciaOpcional> ::= extends idClase | ε
    private void HerenciaOpcional() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (tokenActual.getToken_id().equals("pr_extends")) {
            match("pr_extends");
            ts.setHerencia(tokenActual);
            match("idClase");
        }else {
            ts.setHerenciaObject();
        }
    }

    // <ListaMiembros> ::= <Miembro> <ListaMiembros> | ε
    private void ListaMiembros() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (tokenActual.getToken_id().equals("pr_public") || tokenActual.getToken_id().equals("pr_static") || tokenActual.getToken_id().equals("idClase") || tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int") || tokenActual.getToken_id().equals("pr_void")) {
            Miembro();
            ListaMiembros();
        }
    }

    // <Miembro> ::= public <Constructor> | <EstaticoOpcional> <TipoMiembro> idMetVar <RestoAtributoMetodo>
    private void Miembro() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (tokenActual.getToken_id().equals("pr_public")) {
            Constructor();
        } else {
            EstaticoOpcional();
            TipoMiembro();
            nombreAuxiliar = tokenActual;
            match("idMetVar");
            RestoAtributoMetodo();
            if (noEsMetodo) {
                ts.agregarAtributos(tipoAuxiliar,nombreAuxiliar,esStatic);
                esStatic = false;
            }
            noEsMetodo = true;
        }
    }

    // <RestoAtributoMetodo> ::= ; | <ArgsFormales> <Bloque>
    private void RestoAtributoMetodo() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (tokenActual.getToken_id().equals("puntoComa")) {
            match("puntoComa");
        } else {
            ts.insertarMetodos(tipoAuxiliar,nombreAuxiliar,esStatic);
            esStatic = false;
            ArgsFormales();
            Bloque();
        }
    }

    // <Constructor> ::= public idClase <ArgsFormales> <Bloque>
    private void Constructor() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        tipoAuxiliar = tokenActual;
        match("pr_public");
        nombreAuxiliar = tokenActual;
        match("idClase");
        ts.insertarConstructor(tipoAuxiliar,nombreAuxiliar);
        ArgsFormales();
        Bloque();
        noEsMetodo = true;
    }

    // <TipoMiembro> ::= void | <Tipo>
    private void TipoMiembro() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_void")) {
            tipoAuxiliar = tokenActual;
            match("pr_void");
        } else {
            Tipo();
        }
    }

    //<Tipo> ::= <TipoPrimitivo> | idClase
    private void Tipo() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int")) {
            TipoPrimitivo();
        } else {
            tipoAuxiliar = tokenActual;
            match("idClase");
        }
    }

    // <TipoPrimitivo> ::= boolean | char | int
    private void TipoPrimitivo() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_boolean")) {
            tipoAuxiliar = tokenActual;
            match("pr_boolean");
        } else if (tokenActual.getToken_id().equals("pr_char")) {
            tipoAuxiliar = tokenActual;
            match("pr_char");
        } else if (tokenActual.getToken_id().equals("pr_int")) {
            tipoAuxiliar = tokenActual;
            match("pr_int");
        }
    }

    //<EstaticoOpcional> ::= static | e
    private void EstaticoOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_static")) {
            match("pr_static");
            esStatic = true;
        }
    }

    // <ArgsFormales> ::= ( <ListaArgsFormalesOpcional> )
    private void ArgsFormales() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        match("parentesisAbierto");
        ListaArgsFormalesOpcional();
        match("parentesisCerrado");
        noEsMetodo = false;
    }

    // <ListaArgsFormalesOpcional> ::= <ListaArgsFormales> | ε
    private void ListaArgsFormalesOpcional() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int") || tokenActual.getToken_id().equals("idClase")) {
            noEsMetodo = false;
            ListaArgsFormales();
        }
    }

    // <ListaArgsFormales> ::= <ArgFormal> <RestoListaArgFormal>
    private void ListaArgsFormales() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        ArgFormal();
        RestoListaArgFormal();
    }

    // <RestoListaArgFormal> ::= , <ListaArgsFormales> | ε
    private void RestoListaArgFormal() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (tokenActual.getToken_id().equals("coma")) {
            match("coma");
            ListaArgsFormales();
        }
    }

    // <ArgFormal> ::= <Tipo> idMetVar
    private void ArgFormal() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        Tipo();
        nombreAuxiliar = tokenActual;
        match("idMetVar");
        ts.agregarParametros(tipoAuxiliar,nombreAuxiliar);
    }

    // <Bloque> ::= { <ListaSentencias> }
    private NodoBloque Bloque() throws ExcepcionSintactica, ExcepcionLexica {
        NodoBloque nodoBloque= new NodoBloque(tokenActual);
        bloqueActual = nodoBloque;
        match("llaveAbierta");
        ListaSentencias();
        match("llaveCerrada");
        return nodoBloque;
    }

    // <ListaSentencias> ::= <Sentencia> <ListaSentencias> | ε
    private void ListaSentencias() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("puntoComa") || tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("pr_var") || tokenActual.getToken_id().equals("pr_return") || tokenActual.getToken_id().equals("pr_break") || tokenActual.getToken_id().equals("pr_if") || tokenActual.getToken_id().equals("pr_while") || tokenActual.getToken_id().equals("pr_switch") || tokenActual.getToken_id().equals("llaveAbierta") || esExpresion(tokenActual) || tokenActual.getToken_id().equals("idClase")) {
            NodoSentencia sentencia = Sentencia();
            bloqueActual.agregarSentencia(sentencia);
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
    private NodoSentencia Sentencia() throws ExcepcionSintactica, ExcepcionLexica {
        NodoSentencia nodoSentencia = null;
        if(esExpresion(tokenActual) || tokenActual.getToken_id().equals("idClase")){
            Expresion();
            match("puntoComa");
        }
        switch (tokenActual.getToken_id()) {
            case "puntoComa":
                match("puntoComa");
                break;
            case "pr_var":
                VarLocal();
                match("puntoComa");
                break;
            case "pr_return":
                nodoSentencia = Return();
                match("puntoComa");
                break;
            case "pr_break":
                nodoSentencia = Break();
                match("puntoComa");
                break;
            case "pr_if":
                nodoSentencia = If();
                break;
            case "pr_while":
                esWhileOrSwich = true;
                nodoSentencia = While();
                esWhileOrSwich = false;
                break;
            case "pr_switch":
                esWhileOrSwich = true;
                Switch();
                esWhileOrSwich = false;
                break;
            case "llaveAbierta":
                nodoSentencia = Bloque();
                break;
            case "llaveCerrada":
                break;
            case "idMetVar":
                break;
        }
        return nodoSentencia;
    }

    // <VarLocal> ::= var idMetVar = <ExpresionCompuesta>
    private void VarLocal() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_var");
        match("idMetVar");
        match("asignacion");
        ExpresionCompuesta();
    }

    //<Return> ::= return <ExpresionOpcional>
    private NodoSentencia Return() throws ExcepcionSintactica, ExcepcionLexica {
        NodoReturn nodoReturn = new NodoReturn(tokenActual);
        NodoExpresion nodoExpresion;
        nodoReturn.setMetodoPadre(ts.getMetodoActual());
        match("pr_return");
        nodoExpresion = ExpresionOpcional();
        nodoReturn.setExpresion(nodoExpresion);
        return nodoReturn;
    }

    // <Break> ::= break
    private NodoSentencia Break() throws ExcepcionSintactica, ExcepcionLexica {
        NodoBrake nodoBrake = new NodoBrake(tokenActual);
        nodoBrake.setEsWhileOrSwitch(esWhileOrSwich);
        match("pr_break");
        return nodoBrake;
    }

    // <ExpresionOpcional> ::= <Expresion> | ε
    private NodoExpresion ExpresionOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        NodoExpresion nodoExpresion = null;
        if (tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("intLiteral") || tokenActual.getToken_id().equals("charLiteral") || tokenActual.getToken_id().equals("pr_true") || tokenActual.getToken_id().equals("pr_false") || tokenActual.getToken_id().equals("pr_null") || tokenActual.getToken_id().equals("stringLiteral") || tokenActual.getToken_id().equals("parentesisAbierto") || tokenActual.getToken_id().equals("pr_this") || tokenActual.getToken_id().equals("pr_new") || tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("not")) {
            nodoExpresion = Expresion();
        }else {
            //nodoExpresion = new NodoExpresionVacia();
        }
        return nodoExpresion;
    }

    // <If> ::= if ( <Expresion> ) <Sentencia> <IfPrima>
    private NodoIf If() throws ExcepcionSintactica, ExcepcionLexica {
        NodoIf nodoIf = new NodoIf(tokenActual);
        match("pr_if");
        match("parentesisAbierto");
        NodoExpresion condicion = Expresion();
        nodoIf.setCondicion(condicion);
        match("parentesisCerrado");
        NodoSentencia sentencia = Sentencia();
        nodoIf.setSentencia(sentencia);
        if (tokenActual.getToken_id().equals("pr_else")) {
            NodoSentencia sentenciaElse = IfPrima();
            nodoIf.setSentenciaElse(sentenciaElse);
        }
        return nodoIf;
    }

    // <IfPrima> ::= else <Sentencia> | ε
    private NodoSentencia IfPrima() throws ExcepcionSintactica, ExcepcionLexica {
        NodoSentencia nodoSentencia = null;
        match("pr_else");
        nodoSentencia = Sentencia();
        return nodoSentencia;
    }

    // <While> ::= while ( <Expresion> ) <Sentencia>
    private NodoWhile While() throws ExcepcionSintactica, ExcepcionLexica {
        NodoWhile nodoWhile = new NodoWhile(tokenActual);
        match("pr_while");
        match("parentesisAbierto");
        NodoExpresion condocion = Expresion();
        nodoWhile.setCondicion(condocion);
        match("parentesisCerrado");
        NodoSentencia sentencia = Sentencia();
        nodoWhile.setSentencia(sentencia);
        return nodoWhile;
    }

    // <Switch> ::= switch ( <Expresion> ) { <ListaSentenciasSwitch> }
    private NodoSentencia Switch() throws ExcepcionSintactica, ExcepcionLexica {
        NodoSwitch nodoSwitch = new NodoSwitch(tokenActual);
        NodoExpresion nodoExpresion = null;

        nodoSwitchActual = nodoSwitch;
        match("pr_switch");
        match("parentesisAbierto");
        nodoExpresion = Expresion();
        nodoSwitch.setExpresion(nodoExpresion);
        match("parentesisCerrado");
        match("llaveAbierta");
        ListaSentenciasSwitch();
        match("llaveCerrada");
        return nodoSwitch;
    }

    // <ListaSentenciasSwitch> ::= <SentenciaSwitch> <ListaSentenciasSwitch> | ε
    private void ListaSentenciasSwitch() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_case") || tokenActual.getToken_id().equals("pr_default")) {
            SentenciaSwitch();
            ListaSentenciasSwitch();
        }
    }

    // <SentenciaSwitch> ::= case <LiteralPrimitivo> : <SentenciaOpcional> | default : <Sentencia>
    private void SentenciaSwitch() throws ExcepcionSintactica, ExcepcionLexica {
        NodoOperandoLiteral nodoOperandoLiteral;
        NodoSentencia sentencia;
        if (tokenActual.getToken_id().equals("pr_case")) {
            match("pr_case");
            nodoOperandoLiteral = LiteralPrimitivo();
            match("dosPuntos");
            sentencia = SentenciaOpcional();
            if (nodoOperandoLiteral != null) {
                nodoSwitchActual.agregarCaso(nodoOperandoLiteral, sentencia);
            }
        } else if (tokenActual.getToken_id().equals("pr_default")) {
            match("pr_default");
            match("dosPuntos");
            sentencia =Sentencia();
            nodoSwitchActual.setSentenciaDefault(sentencia);
        }
    }

    // <SentenciaOpcional> ::= <Sentencia> | ε
    private NodoSentencia SentenciaOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        NodoSentencia nodoSentencia = null;
        if (tokenActual.getToken_id().equals("puntoComa") || tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("pr_var") || tokenActual.getToken_id().equals("pr_return") || tokenActual.getToken_id().equals("pr_break") || tokenActual.getToken_id().equals("pr_if") || tokenActual.getToken_id().equals("pr_while") || tokenActual.getToken_id().equals("pr_switch") || tokenActual.getToken_id().equals("llaveAbierta")) {
            nodoSentencia = Sentencia();
        }
        return nodoSentencia;
    }

    // <Expresion>::= <ExpresionCompuesta> <ExpresionPrima>
    private NodoExpresion Expresion() throws ExcepcionSintactica, ExcepcionLexica {
        ExpresionCompuesta();
        ExpresionPrima();
        return null;
    }

    // <ExpresionPrima> ::= = <ExpresionCompuesta> | += <ExpresionCompuesta> | -= <ExpresionCompuesta> | ε
    private void ExpresionPrima() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("asignacion") || tokenActual.getToken_id().equals("sumaAsignacion") || tokenActual.getToken_id().equals("restaAsignacion")) {
            OperadorAsignacion();
            ExpresionCompuesta();
        }
    }

    // <OperadorAsignacion> ::= = | += | -=
    private void OperadorAsignacion() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("asignacion")) {
            match("asignacion");
        } else if (tokenActual.getToken_id().equals("sumaAsignacion")) {
            match("sumaAsignacion");
        } else if (tokenActual.getToken_id().equals("restaAsignacion")) {
            match("restaAsignacion");
        }
    }

    // <ExpresionCompuesta> ::= <ExpresionBasica> <ExpresionCompuestaPrima>
    private NodoExpresion ExpresionCompuesta() throws ExcepcionSintactica, ExcepcionLexica {
        NodoExpresion ladoIzquierdo = ExpresionBasica();
        return ExpresionCompuestaPrima(ladoIzquierdo);
    }

    // <ExpresionCompuestaPrima> ::= <OperadorBinario> <ExpresionBasica> <ExpresionCompuestaPrima> | ε
    private NodoExpresion ExpresionCompuestaPrima(NodoExpresion ladoIzquierdo) throws ExcepcionSintactica, ExcepcionLexica {
        if (esOperadorBinario(tokenActual)) {
            Token operador = OperadorBinario();
            NodoExpresion ladoDerecho = ExpresionBasica();
            NodoExpresionBinaria nodoExpresionBinaria = new NodoExpresionBinaria(ladoIzquierdo, ladoDerecho, operador);
            return ExpresionCompuestaPrima(nodoExpresionBinaria);
        }
        return ladoIzquierdo;
    }

    // <OperadorBinario> ::= or | and | == | != | < | > | <= | >= | + | - | * | / | %
    private Token OperadorBinario() throws ExcepcionSintactica, ExcepcionLexica {
        Token toReturn = null;
        //Aca se determina el lado derecho esto deberia devolver un toekn
        switch (tokenActual.getToken_id()) {
            case "or":
                toReturn = tokenActual;
                match("or");
                break;
            case "and":
                toReturn = tokenActual;
                match("and");
                break;
            case "comparacion":
                toReturn = tokenActual;
                match("comparacion");
                break;
            case "distinto":
                toReturn = tokenActual;
                match("distinto");
                break;
            case "menor":
                toReturn = tokenActual;
                match("menor");
                break;
            case "mayor":
                toReturn = tokenActual;
                match("mayor");
                break;
            case "menorIgual":
                toReturn = tokenActual;
                match("menorIgual");
                break;
            case "mayorIgual":
                toReturn = tokenActual;
                match("mayorIgual");
                break;
            case "suma":
                toReturn = tokenActual;
                match("suma");
                break;
            case "resta":
                toReturn = tokenActual;
                match("resta");
                break;
            case "multiplicacion":
                toReturn = tokenActual;
                match("multiplicacion");
                break;
            case "division":
                toReturn = tokenActual;
                match("division");
                break;
            case "porcentaje":
                toReturn = tokenActual;
                match("porcentaje");
                break;
        }
        return toReturn;
    }

    // <ExpresionBasica> ::= <OperadorUnario> <Operando> | <Operando>
    private NodoExpresion ExpresionBasica() throws ExcepcionSintactica, ExcepcionLexica {
        NodoExpresion nodoExpresion;
        if (tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("not")) {
            Token operadorUnario = OperadorUnario();
            NodoExpresion operando = Operando();
            nodoExpresion = null;
            //Aca hay algo que cambiar
        } else {
            nodoExpresion = Operando();
        }
        return nodoExpresion;
    }

    // <OperadorUnario> ::= + | - | !
    private Token OperadorUnario() throws ExcepcionSintactica, ExcepcionLexica {
        Token operador = tokenActual;
        if (tokenActual.getToken_id().equals("suma")) {
            match("suma");
        } else if (tokenActual.getToken_id().equals("resta")) {
            match("resta");
        } else if (tokenActual.getToken_id().equals("not")) {
            match("not");
        }
        return operador;
    }

    // <Operando> ::= <Literal> | <Acceso>
    private NodoExpresion Operando() throws ExcepcionSintactica, ExcepcionLexica {
        NodoExpresion nodoExpresion = null;
        if (tokenActual.getToken_id().equals("intLiteral") || tokenActual.getToken_id().equals("charLiteral") || tokenActual.getToken_id().equals("pr_true") || tokenActual.getToken_id().equals("pr_false") || tokenActual.getToken_id().equals("pr_null") || tokenActual.getToken_id().equals("stringLiteral")) {
            nodoExpresion = Literal();
        } else {
            //nodoExpresion = Acceso();
        }
        return nodoExpresion;
    }

    // <Literal> ::= <LiteralPrimitivo> | <LiteralObjeto>
    private NodoExpresion Literal() throws ExcepcionSintactica, ExcepcionLexica {
        NodoOperandoLiteral nodoOperandoLiteral = null;
        if (esLiteralPrimitivo(tokenActual)) {
            nodoOperandoLiteral = LiteralPrimitivo();
        } else if (esLiteralObjeto(tokenActual)) {
            nodoOperandoLiteral = LiteralObjeto();
        } else {
            throw new ExcepcionSintactica(tokenActual, "literal válido");
        }
        return nodoOperandoLiteral;
    }

    // <LiteralPrimitivo> ::= true | false | intLiteral | charLiteral
    private NodoOperandoLiteral LiteralPrimitivo() throws ExcepcionSintactica, ExcepcionLexica {
        NodoOperandoLiteral nodoOperandoLiteral = new NodoOperandoLiteral(tokenActual.getLexema());
        if (tokenActual.getToken_id().equals("pr_true")) {
            match("pr_true");
        } else if (tokenActual.getToken_id().equals("pr_false")) {
            match("pr_false");
        } else if (tokenActual.getToken_id().equals("intLiteral")) {
            match("intLiteral");
        } else if (tokenActual.getToken_id().equals("charLiteral")) {
            match("charLiteral");
        }
        return nodoOperandoLiteral;
    }

    // <LiteralObjeto> ::= null | stringLiteral
    private NodoOperandoLiteral LiteralObjeto() throws ExcepcionSintactica, ExcepcionLexica {
        NodoOperandoLiteral nodoOperandoLiteral = new NodoOperandoLiteral(tokenActual.getLexema());
        if (tokenActual.getToken_id().equals("pr_null")) {
            match("pr_null");
        } else if (tokenActual.getToken_id().equals("stringLiteral")) {
            match("stringLiteral");
        }
        return nodoOperandoLiteral;
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
        Primario(); //Porque cambia aca
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
            case "idMetVar", "idClase"://Aca iria el or
                AccesoVarMetodo();
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
        }
    }

    //<AccesoThis> ::= this
    private void AccesoThis() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_this");
    }

    private void AccesoVarMetodo() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("idMetVar")) {
            match("idMetVar");
        } else if (tokenActual.getToken_id().equals("idClase")) {
            match("idClase");
        }
        AccesoVarMetodoPrima();
    }

    // <AccesoVarMetodoPrima> ::= <ArgsActuales> | ε
    private void AccesoVarMetodoPrima() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("parentesisAbierto")) {
            ArgsActuales();
        }
    }

    //<AccesoConstructor> ::= new idClase <ArgsActuales>
    private void AccesoConstructor() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_new");
        match("idClase");
        ArgsActuales();
    }

    //<ExpresionParentizada> ::= ( <Expresion> )
    private void ExpresionParentizada() throws ExcepcionSintactica, ExcepcionLexica {
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
    }

    //<AccesoMetodoEstatico> ::= idClase . idMetVar <ArgsActuales>
    private void AccesoMetodoEstatico() throws ExcepcionSintactica, ExcepcionLexica {
        match("idClase");
        match("punto");
        match("idMetVar");
        ArgsActuales();
        EncadenadoOpcional();
    }

    //<ArgsActuales> ::= ( <ListaExpsOpcional> )
    private void ArgsActuales() throws ExcepcionSintactica, ExcepcionLexica {
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

    // <EncadenadoOpcional>::= . idMetVar<EncadenadoOpcionalPrima> | ε
    private void EncadenadoOpcional() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getToken_id().equals("punto")) {
            match("punto");
            if (tokenActual.getToken_id().equals("idMetVar")) {
                match("idMetVar");
            } else if (tokenActual.getToken_id().equals("idClase")) {
                match("idClase");
            }
            EncadenadoOpcionalPrima();
        }
    }

    // <EncadenadoOpcionalPrima>::=  <EncadenadoOpcional> | <ArgsActuales> <EncadenadoOpcional>
    private void EncadenadoOpcionalPrima() throws ExcepcionLexica, ExcepcionSintactica {
        if (tokenActual.getToken_id().equals("parentesisAbierto")) {
            ArgsActuales();
            EncadenadoOpcional();
        } else if (tokenActual.getToken_id().equals("punto")) {
            match("punto");
            if (tokenActual.getToken_id().equals("idMetVar")) {
                match("idMetVar");
            } else if (tokenActual.getToken_id().equals("idClase")) {
                match("idClase");
            }
            EncadenadoOpcionalPrima();
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

    private boolean esOperadorBinario(Token token) {
        return token.getToken_id().equals("or") || token.getToken_id().equals("and") ||
                token.getToken_id().equals("==") || token.getToken_id().equals("!=") ||
                token.getToken_id().equals("<") || token.getToken_id().equals(">") ||
                token.getToken_id().equals("<=") || token.getToken_id().equals(">=") ||
                token.getToken_id().equals("+") || token.getToken_id().equals("-") ||
                token.getToken_id().equals("*") || token.getToken_id().equals("/") ||
                token.getToken_id().equals("%");
    }
}
