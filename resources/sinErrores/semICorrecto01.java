///[SinErrores]
//Control simple de declaracion de clases con nombres validos

class A {

    int a;

    static int b;

    void m1(int a)
    {}

    static void m2()
    {}

    void m3(int p1, char p2)
    {}

    void m4(int p3, boolean p4)
    {}

}
//El error es porque la redefincion solo se permite en metodos de la misma clase
class B extends A{

    int x;

    void m1(int a)
    {}
}
class Init{
    static void main()
    { }
}
