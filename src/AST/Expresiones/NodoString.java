package AST.Expresiones;

import AnalizadorLexico.Token;
import AnalizadorSemantico.Tipo;
import AnalizadorSemantico.TipoClase;
import GeneradorDeCodigoFuente.GeneradorDeCodigoFuente;

import java.io.IOException;

public class NodoString extends NodoOperandoLiteral {

    int cantidadString = 0;

    public NodoString(Token valor) {
        super(valor);
    }

    @Override
    public boolean esAsignable() {
        return false;
    }

    @Override
    public boolean esInvocable() {
        return false;
    }

    @Override
    public Tipo chequear() {
        TipoClase tipo = new TipoClase();
        tipo.setNombreClase(this.getValor());
        return tipo;
    }

    @Override
    public void generar(GeneradorDeCodigoFuente gcf) throws IOException {
        gcf.setModoData();
        String label = generarLebelString();
        String introduccion = label +":";
        gcf.agregarInstruccion(introduccion + " DW " + this.getValor().getLexema() + ", 0");
        gcf.setModoCode();
        gcf.agregarInstruccion("PUSH "+label);
    }

    private String generarLebelString() {
        String label = "string_label_"+cantidadString;
        cantidadString++;
        return label;
    }
}
