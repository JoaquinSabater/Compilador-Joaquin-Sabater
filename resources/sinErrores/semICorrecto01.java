///[SinErrores]
//Control simple de declaracion de clases con nombres validos

class A{
    int a;
}

class B extends A{

    int c;

    void m1(int a)
    {}

    static void m2()
    {}

    void m3(int p1, char p2)
    {}

    void m4(int p3, boolean p4)
    {}

}
class C extends B{

    char d;

    void m10(int a)
    {}
}

class Init{
    static void main()
    { }
}
