///33&exitosamente

class A{
    void m1(C c1){
        c1.m3(3);
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
        var c = new C();
        a.m1(c);
    }
}
