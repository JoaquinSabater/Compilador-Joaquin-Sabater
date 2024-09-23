// Clase compleja con herencia, métodos, variables estáticas, constructores,
// sentencias condicionales, bucles, switch y expresiones aritméticas.
class MyClass extends ParentClass {
    // Atributos
    int myIntVar;
    static int staticIntVar;
    boolean flag;
    char myChar;

    public MyClass(int initialValue, boolean initialFlag) {
        myChar = 'a';
        this.myIntVar = initialValue;
    }

    // Método sin retorno (void) y sin parámetros
    void reset() {
        myIntVar = 0;
    }

    // Método con parámetros y operaciones aritméticas
    int addValues(int a, int b) {
        return a + b;
    }

    // Método con condicional `if-else`
    void checkValue() {
        if (myIntVar > 10) {
            myIntVar = 10;
        } else {
            myIntVar = 0;
        }
    }

    // Método con bucle `while`
    void incrementUntil(int limit) {
        while (myIntVar < limit) {
            myIntVar+1;
        }
    }

    // Método que usa `switch`
    void evaluateChar(char inputChar) {
        switch (inputChar) {
            case 'a': myChar = 'A'; break;
            case 'b': myChar = 'B'; break;
            default: myChar = '?';
        }
    }

    // Método con expresiones lógicas y aritméticas
    boolean evaluateCondition(int a, int b) {
        return (a > b && a < 100) || flag;
    }

    // Método estático
    static void setStaticValue(int value) {
        staticIntVar = value;
    }
}

