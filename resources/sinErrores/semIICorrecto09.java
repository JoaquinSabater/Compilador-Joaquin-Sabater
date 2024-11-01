// mediante la llamada a un metodo directo accede encadenadamente a una var
class A {

    B b1;

    void m1(){
        return b1;
    }
}

class B extends A {

}


class Init{
    static void main()
    { }
}


