///3333&exitosamente

class A{
    void mc(){
        var x = 1;
        var y = 2;
        var z = 3;
        if (true){
            debugPrint(1);
        }else {
            debugPrint(2);
        }
        var w = 3;
    }
}


class Init{
    static void main()
    {
        var a = new A();
        a.mc();
    }
}

