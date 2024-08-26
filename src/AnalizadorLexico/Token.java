package AnalizadorLexico;

public class Token {
    private String id;
    private String lexema;
    private int nro_linea;
    public Token(String id, String lexema, int nro_linea){
        this.id = id;
        this.lexema = lexema;
        this.nro_linea = nro_linea;
    }

    public String getToken_id(){
        return this.id;
    }

    public int getNro_linea(){return this.nro_linea;}

    public String getLexema() { return this.lexema;}
}
