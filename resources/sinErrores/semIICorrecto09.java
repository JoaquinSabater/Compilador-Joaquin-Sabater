// mediante la llamada a un metodo directo accede encadenadamente a una var
class A {

    B b1;

    A b2;

    void m1(){
       if(1<2){
           var x = 4;
           var x = 5;
       }
    }

    void m2(A b3){

    }

}

class B extends A {

}


class Init{
    static void main()
    { }
}


