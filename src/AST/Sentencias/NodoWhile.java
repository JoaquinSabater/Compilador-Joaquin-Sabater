package AST.Sentencias;

import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AST.Expresiones.*;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoWhile extends NodoSentencia {

    private NodoExpresion condicion;
    private NodoSentencia sentencia;

    public NodoWhile(Token token) {
        super(token);
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        Tipo tipoCondicion = condicion.chequear();
        if (!tipoCondicion.getNombreClase().getToken_id().equals("pr_boolean")) {
            throw new ExcepcionSemantica(token, "La condición del while debe ser de tipo booleano");
        }
        sentencia.chequear();
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {

    }

    public void setCondicion(NodoExpresion condicion) {
        this.condicion = condicion;
    }

    public void setSentencia(NodoSentencia sentencia) {
        this.sentencia = sentencia;
    }

    public NodoExpresion getCondicion() {
        return condicion;
    }

    public NodoSentencia getSentencia() {
        return sentencia;
    }
}
