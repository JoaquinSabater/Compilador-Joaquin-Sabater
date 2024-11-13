class A{
    void m2(){
        this.m1();
    }

    void m1(){

    }
}

class Main {
    A a1;
    static void main() {
        a1.m1();
    }
}


