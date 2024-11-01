// mediante la llamada a un metodo directo accede encadenadamente a una var
class A {
    int a1;

    void m1(){
        switch (1){
            case 1:
                var a = 1;
                break;
            case 2:
                var b = 1;
                break;
        }
    }

}



class Init{
    static void main()
    { }
}


