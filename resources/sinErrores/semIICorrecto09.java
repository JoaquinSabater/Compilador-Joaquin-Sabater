// mediante la llamada a un metodo directo accede encadenadamente a una var
class A {

    B b1;

    A b2;

    void m1(){
        if(b1 == b2){

        }
    }

}

class B extends A {

}


class Init{
    static void main()
    { }
}


