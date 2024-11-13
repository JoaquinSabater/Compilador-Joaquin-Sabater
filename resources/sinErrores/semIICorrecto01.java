class A {
    B getB() {
        return new B();
    }
}

class B {
    C getC() {
        return new C();
    }
}

class C {
    D getD() {
        return new D();
    }
}

class D {
    E getE() {
        return new E();
    }
}

class E {
    int getInt() {
        return 42;
    }
}

class Main {
    A a;
    int x;
    static void main() {
        a = new A();
        x = a.getB().getC().getD().getE().getInt();
    }
}


