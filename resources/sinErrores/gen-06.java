///2222&exitosamente

class A{
    void mc(){
        switch (2){
            case 1:
                debugPrint(2222);
            case 2:
                debugPrint(3333);
            case 3:
                debugPrint(4444);
        }
    }
}


class Init{
    static void main()
    {
        var a = new A();
        a.mc();
    }
}


