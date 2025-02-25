package AST.Encadenado;

import AnalizadorLexico.Token;
import AnalizadorSemantico.*;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class VarEncadenada extends Encadenado {

    Atributo atributo;

    public VarEncadenada(Token token, TS ts) {
        super(token, ts);
        this.esAsignable = true;
    }

    //Yo tengo que revisar que exista en la clase que me dan por la izquierda
    @Override
    public Tipo chequear(Tipo tipoLadoIzquierdo) throws ExcepcionSemantica {
        Tipo tipoVariableEncadenada = null;

        Clase claseActual = ts.getClase(tipoLadoIzquierdo.getNombreClase().getLexema());

        if (claseActual == null){
            throw new ExcepcionSemantica(token, "Clase " + tipoLadoIzquierdo.getNombreClase().getLexema() + " no encontrada");
        }

        atributo = claseActual.getAtributo(token.getLexema());
        if (atributo == null) {
            throw new ExcepcionSemantica(token, "Atributo " + token.getLexema() + " no encontrado en la clase " + claseActual.getNombre());
        }

        tipoVariableEncadenada = atributo.getTipo();

        if(this.encadenado != null){
            if(!tipoVariableEncadenada.esTipoPrimitivo())
                return this.encadenado.chequear(tipoVariableEncadenada);
            else
                throw new ExcepcionSemantica(this.token, "la variable encadenada "+this.token.getLexema()+ " no debe ser de tipo primitivo para tener un encadenado.");

        }

        return tipoVariableEncadenada;
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        if(!esLadoIzquierdo || encadenado != null){
            gcf.agregarInstruccion("LOADREF "+atributo.getOffset()+" ; Cargo el atributo "+atributo.getNombre());
        }else {
            gcf.agregarInstruccion("SWAP");
            gcf.agregarInstruccion("STOREREF "+atributo.getOffset()+" ; Guardo el atributo "+atributo.getNombre());
        }
        if(encadenado != null){
            encadenado.generar(gcf);
        }
    }

    public boolean esAsignable(){
        if(this.encadenado == null){
            return true;
        } else {
            return this.encadenado.esAsignable();
        }
    }
    public boolean esInvocable(){
        if(this.encadenado == null){
            return false;
        } else {
            return this.encadenado.esInvocable();
        }
    }

}
