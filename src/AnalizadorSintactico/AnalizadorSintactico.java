package AnalizadorSintactico;

import AnalizadorLexico.*;
import AnalizadorSemantico.*;

import java.util.Objects;

public class AnalizadorSintactico {
    analizadorLexico lexico;

    Token tokenActual;

    TS ts;

    Token tipoAuxiliar;

    Token nombreAuxiliar;

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
            match("idClase");
            ts.setHerencia(tokenActual);
        }else {
            //ts.setHerencia(null);
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
            if (ts.getMetodoActual() == null) {
                ts.agregarAtributos(tipoAuxiliar,nombreAuxiliar);
            }
            //ts.agregarAtributos(tipoAuxiliar,nombreAuxiliar);
            //El problema que tengo que es que va a entrar aca sea un mento o un atributo
            RestoAtributoMetodo();
        }
    }

    // <RestoAtributoMetodo> ::= ; | <ArgsFormales> <Bloque>
    private void RestoAtributoMetodo() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (tokenActual.getToken_id().equals("puntoComa")) {
            match("puntoComa");
        } else {
            ts.insertarMetodos(tipoAuxiliar,nombreAuxiliar);
            ArgsFormales();
            Bloque();
        }
    }

    // <Constructor> ::= public idClase <ArgsFormales> <Bloque>
    private void Constructor() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        match("pr_public");
        match("idClase");
        ArgsFormales();
        Bloque();
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
        }
    }

    // <ArgsFormales> ::= ( <ListaArgsFormalesOpcional> )
    private void ArgsFormales() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        match("parentesisAbierto");
        ListaArgsFormalesOpcional();
        match("parentesisCerrado");
    }

    // <ListaArgsFormalesOpcional> ::= <ListaArgsFormales> | ε
    private void ListaArgsFormalesOpcional() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        if (tokenActual.getToken_id().equals("pr_boolean") || tokenActual.getToken_id().equals("pr_char") || tokenActual.getToken_id().equals("pr_int") || tokenActual.getToken_id().equals("idClase")) {
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
        //Aca entra si hay mas de un argumento formal
        if (tokenActual.getToken_id().equals("coma")) {
            match("coma");
            ListaArgsFormales();
        }
    }

    // <ArgFormal> ::= <Tipo> idMetVar
    private void ArgFormal() throws ExcepcionSintactica, ExcepcionLexica, ExcepcionSemantica {
        Tipo(); //Se elige el tipo del argumento
        nombreAuxiliar = tokenActual;
        match("idMetVar");
        ts.agregarParametros(tipoAuxiliar,nombreAuxiliar);
        System.out.println(tipoAuxiliar.getLexema());
        System.out.println(nombreAuxiliar.getLexema());
    }

    // <Bloque> ::= { <ListaSentencias> }
    private void Bloque() throws ExcepcionSintactica, ExcepcionLexica {
        match("llaveAbierta");
        ListaSentencias();
        match("llaveCerrada");
    }

    // <ListaSentencias> ::= <Sentencia> <ListaSentencias> | ε
    private void ListaSentencias() throws ExcepcionSintactica, ExcepcionLexica {
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
        if(esExpresion(tokenActual)){ //Aca esta el problema, no llega al break que esta en el swith
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
            case "llaveCerrada":
                break;
            case "idMetVar":
                break;
        }
    }

    // <VarLocal> ::= var idMetVar = <ExpresionCompuesta>
    private void VarLocal() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_var");
        match("idMetVar");
        match("asignacion");
        ExpresionCompuesta();
    }

    //<Return> ::= return <ExpresionOpcional>
    private void Return() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_return");
        ExpresionOpcional();
    }

    // <Break> ::= break
    private void Break() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_break");
    }

    // <ExpresionOpcional> ::= <Expresion> | ε
    private void ExpresionOpcional() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("intLiteral") || tokenActual.getToken_id().equals("charLiteral") || tokenActual.getToken_id().equals("pr_true") || tokenActual.getToken_id().equals("pr_false") || tokenActual.getToken_id().equals("pr_null") || tokenActual.getToken_id().equals("stringLiteral") || tokenActual.getToken_id().equals("parentesisAbierto") || tokenActual.getToken_id().equals("pr_this") || tokenActual.getToken_id().equals("pr_new") || tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("not")) {
            Expresion();
        }
    }

    // <If> ::= if ( <Expresion> ) <Sentencia> <IfPrima>
    private void If() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_if");
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
        Sentencia();
        IfPrima();
    }

    // <IfPrima> ::= else <Sentencia> | ε
    private void IfPrima() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_else")) {
            match("pr_else");
            Sentencia();
        }
    }

    // <While> ::= while ( <Expresion> ) <Sentencia>
    private void While() throws ExcepcionSintactica, ExcepcionLexica {
        match("pr_while");
        match("parentesisAbierto");
        Expresion();
        match("parentesisCerrado");
        Sentencia();
    }

    // <Switch> ::= switch ( <Expresion> ) { <ListaSentenciasSwitch> }
    private void Switch() throws ExcepcionSintactica, ExcepcionLexica {
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
        if (tokenActual.getToken_id().equals("pr_case") || tokenActual.getToken_id().equals("pr_default")) {
            SentenciaSwitch();
            ListaSentenciasSwitch();
        }
    }

    // <SentenciaSwitch> ::= case <LiteralPrimitivo> : <SentenciaOpcional> | default : <Sentencia>
    private void SentenciaSwitch() throws ExcepcionSintactica, ExcepcionLexica {
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
        if (tokenActual.getToken_id().equals("puntoComa") || tokenActual.getToken_id().equals("idMetVar") || tokenActual.getToken_id().equals("pr_var") || tokenActual.getToken_id().equals("pr_return") || tokenActual.getToken_id().equals("pr_break") || tokenActual.getToken_id().equals("pr_if") || tokenActual.getToken_id().equals("pr_while") || tokenActual.getToken_id().equals("pr_switch") || tokenActual.getToken_id().equals("llaveAbierta")) {
            Sentencia();
        }
    }

    // <Expresion>::= <ExpresionCompuesta> <ExpresionPrima>
    private void Expresion() throws ExcepcionSintactica, ExcepcionLexica {
        ExpresionCompuesta();
        ExpresionPrima();
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
    private void ExpresionCompuesta() throws ExcepcionSintactica, ExcepcionLexica {
        ExpresionBasica();
        ExpresionCompuestaPrima();
    }

    // <ExpresionCompuestaPrima> ::= <OperadorBinario> <ExpresionBasica> <ExpresionCompuestaPrima> | ε
    private void ExpresionCompuestaPrima() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("or") || tokenActual.getToken_id().equals("and") || tokenActual.getToken_id().equals("comparacion") || tokenActual.getToken_id().equals("distinto") || tokenActual.getToken_id().equals("menor") || tokenActual.getToken_id().equals("mayor") || tokenActual.getToken_id().equals("menorIgual") || tokenActual.getToken_id().equals("mayorIgual") || tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("multiplicacion") || tokenActual.getToken_id().equals("division") || tokenActual.getToken_id().equals("porcentaje")) {
            OperadorBinario();
            ExpresionBasica();
            ExpresionCompuestaPrima();
        }
    }

    // <OperadorBinario> ::= or | and | == | != | < | > | <= | >= | + | - | * | / | %
    private void OperadorBinario() throws ExcepcionSintactica, ExcepcionLexica {
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
        }
    }

    // <ExpresionBasica> ::= <OperadorUnario> <Operando> | <Operando>
    private void ExpresionBasica() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("suma") || tokenActual.getToken_id().equals("resta") || tokenActual.getToken_id().equals("not")) {
            OperadorUnario();
            Operando();
        } else {
            Operando();
        }
    }

    // <OperadorUnario> ::= + | - | !
    private void OperadorUnario() throws ExcepcionSintactica, ExcepcionLexica {
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
        if (tokenActual.getToken_id().equals("intLiteral") || tokenActual.getToken_id().equals("charLiteral") || tokenActual.getToken_id().equals("pr_true") || tokenActual.getToken_id().equals("pr_false") || tokenActual.getToken_id().equals("pr_null") || tokenActual.getToken_id().equals("stringLiteral")) {
            Literal();
        } else {
            Acceso();
        }
    }

    // <Literal> ::= <LiteralPrimitivo> | <LiteralObjeto>
    private void Literal() throws ExcepcionSintactica, ExcepcionLexica {
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
        }
    }

    // <LiteralObjeto> ::= null | stringLiteral
    private void LiteralObjeto() throws ExcepcionSintactica, ExcepcionLexica {
        if (tokenActual.getToken_id().equals("pr_null")) {
            match("pr_null");
        } else if (tokenActual.getToken_id().equals("stringLiteral")) {
            match("stringLiteral");
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
            case "idMetVar":
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
        match("idMetVar");
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
