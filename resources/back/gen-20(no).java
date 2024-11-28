///333233&exitosamente

class A{

    int x;

    void m1(C c1){
        x = 33;
        debugPrint(x);
        c1.m2(x);
    }
}

class C extends A{
    void m2(int c){
        x = 32;
        debugPrint(x);
        debugPrint(c);
    }
}

class Init{
    static void main()
    {
        var a = new A();
        var c = new C();
        a.m1(c);
    }
}


