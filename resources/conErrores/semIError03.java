///[Error:m1|19]

class A {
    int m1(int a)
    {}
    
    static void m2()
    {}
    
    void m3(int p1, String p2)
    {}
    
    void m4(int p3, boolean p4)
    {}
    
}
//El error es porque la redefincion solo se permite en metodos de la misma clase
class B extends A{
    void m1(int a)
    {}
}



class Init{
    static void main()
    { }
}


