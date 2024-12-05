///12&exitosamente
//

class A{
    static int a;
    int b;
    static void m1(){
        a = 1;
        debugPrint(a);
    }

    void m2(){
        b = 2;
        debugPrint(b);
    }
}

class Init{
    static void main()
    {
        var x = new A();
        x.m1();
        x.m2();
    }
}


