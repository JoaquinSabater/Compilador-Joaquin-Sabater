///[Error:x|10]
// Nombre de variable local o parametro repetido x - ln: 17
class A {
    int a1;
    
     void m1(int p1)
    {
         var x = 1;
        {
            var x = 3;
        }
    }

}




class Init{
    static void main()
    { }
}


