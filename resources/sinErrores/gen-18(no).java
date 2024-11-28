///33&exitosamente

class A{
    void m1(C c1){
        c1.m3(3);
    }

    C m2(){
        debugPrint(1);
        var c = new C();
        return c;
    }
}

class C{
    void m3(int d){
        debugPrint(d);
        debugPrint(3);
    }
}

class Init{

    static void main()
    {
        var a = new A();
        a.m1(a.m2());
    }
}