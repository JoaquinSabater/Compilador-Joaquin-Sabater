// mediante la llamada a un metodo directo accede encadenadamente a una var
class A {
    char a1;

    B b1;

    A m1(){
       return b1;
    }

}

class B extends A{

}


class Init{
    static void main()
    { }
}


