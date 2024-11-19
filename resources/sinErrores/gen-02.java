///1234&33&exitosamente

class A{
    int x;
      void mc(){
        var y = 10;
        var z = 10;
        x = y + z;
        debugPrint(x);
      }
}


class Init{
    static void main()
    { 
        var a = new A();
        a.mc();
    }
}


