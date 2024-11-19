package AST.Sentencias;

import AST.Expresiones.NodoExpresion;
import AnalizadorLexico.Token;
import AnalizadorSemantico.ExcepcionSemantica;
import AnalizadorSemantico.Metodo;
import AnalizadorSemantico.TS;
import AnalizadorSemantico.Tipo;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoReturn extends NodoSentencia {

    Metodo metodoPadre;

    NodoExpresion expresion;

    TS ts;

    public NodoReturn(Token token, TS ts) {
        super(token);
        this.ts = ts;
    }

    public void setExpresion(NodoExpresion expresion) {
        this.expresion = expresion;
    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    public void setMetodoPadre(Metodo metodoPadre) {
        this.metodoPadre = metodoPadre;
    }

    public Metodo getMetodoPadre() {
        return metodoPadre;
    }

    @Override
    public void chequear() throws ExcepcionSemantica {
        Tipo tipoMetodo = metodoPadre.getTipo();
        if(metodoPadre.esConstructor()){
            throw new ExcepcionSemantica(token, "No se puede retornar un valor en un constructor");
        }
        if (expresion != null) {
            Tipo tipoExpresion = expresion.chequear();
            if (!tipoExpresion.esCompatibleTipo(tipoMetodo, ts)) {
                throw new ExcepcionSemantica(token, "El tipo de la expresion de retorno no es compatible con el tipo de retorno del metodo");
            }
        }
        //Donde controlo si el tipo si un metodo con un tipo no tiene un return ?
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        if (expresion != null) {
            expresion.generar(gcf);
            int offset = 0;
            int memToFree= 0;
            if (metodoPadre.getEsStatic()){
                offset = metodoPadre.getParametros().size() + 3;
                memToFree = metodoPadre.getParametros().size();
            }else {
                memToFree = metodoPadre.getParametros().size() + 1;
                offset = metodoPadre.getParametros().size() + 4;
            }
            int variablesLocales = metodoPadre.getBloqueContenedor().cantidadDeVariablesLocales();
            gcf.agregarInstruccion("STORE "+offset+"; Nodo Return guardo el valor de retorno en la posicion de retorno");
            gcf.agregarInstruccion("STOREFP; Nodo Return guardo el FP en la posicion de retorno");
            gcf.agregarInstruccion("RET "+memToFree+"; Nodo Return Libero la memoria");
            //gcf.agregarInstruccion("FMEM "+variablesLocales+"; Nodo Return libero las variables locales");
        }
    }

}
