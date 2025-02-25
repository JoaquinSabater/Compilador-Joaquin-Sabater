package AST.Acceso;

import AnalizadorLexico.Token;
import AnalizadorSemantico.*;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoAccesoThis extends NodoAcceso{

    Token token;

    public NodoAccesoThis(Token token, TS ts) {
        super(token,ts);
    }

    public boolean esAsignable(){
        if(this.encadenado == null){
            return false;
        } else {
            return this.encadenado.esAsignable();
        }
    }
    public boolean esInvocable(){
        if(this.encadenado == null){
            return false;
        } else {
            return this.encadenado.esAsignable();
        }
    }

    @Override
    public Tipo chequear() throws ExcepcionSemantica {
        Clase claseActual = ts.getClaseActual();
        if (claseActual == null) {
            throw new ExcepcionSemantica(token, "Clase actual no definida");
        }
        TipoClase tipoClaseActual = new TipoClase();
        tipoClaseActual.setNombreClase(claseActual.getNombre());

        if (this.encadenado != null) {
            return encadenado.chequear(tipoClaseActual);
        } else {
            return tipoClaseActual;
        }
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        gcf.agregarInstruccion("LOAD 3  ; apilo el this");
        if (this.encadenado != null) {
            encadenado.generar(gcf);
        }
    }
}
