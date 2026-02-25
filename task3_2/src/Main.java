import models.*;


void main() {
    CalculateInteger calculateInteger =new CalculateInteger();
    System.out.println("сложение целых чилсел "+ calculateInteger.addition(64,8));
    System.out.println("умножение целых чилсел "+ calculateInteger.multiplication(64,8));
    System.out.println("вычитание целых чилсел "+ calculateInteger.subtraction(64,8));
    System.out.println("деление целых чилсел "+ calculateInteger.division(64,8));
    try {
        System.out.println("деление целых чилсел "+ calculateInteger.division(64,0));
    }catch (ArithmeticException e)
    {
        System.out.println(e.getMessage());
    }


    CalculateDouble calculateDouble =new CalculateDouble();
    System.out.println("сложение дробных чилсел "+ calculateDouble.addition(64.64,8.8));
    System.out.println("умножение дробных чилсел "+ calculateDouble.multiplication(64.64,8.8));
    System.out.println("вычитание дробных чилсел "+ calculateDouble.subtraction(64.64,8.8));
    System.out.println("деление дробных чилсел "+ calculateDouble.division(64.64,8.8));
    try {
        System.out.println("деление дробных чилсел "+ calculateDouble.division(64.64,0.0));
    }catch (ArithmeticException e)
    {
        System.out.println(e.getMessage());
    }

    CalculateString calculateString =new CalculateString();
    System.out.println("сложение строк "+ calculateString.addition("hello","world"));
    System.out.println("умножение строк "+ calculateString.multiplication("hello","world"));
    System.out.println("умножение строк "+ calculateString.multiplication("hello",""));
    System.out.println("вычитание строк "+ calculateString.subtraction("hello","hel"));
    System.out.println("вычитание строк "+ calculateString.subtraction("hello"," "));
    System.out.println("деление строк "+ calculateString.division("hello","wo"));


    Vector2D vector2D1=new Vector2D(14,15);
    Vector2D vector2D2=new Vector2D(7,-1);
    CalculateVector2D calculateVector2D=new CalculateVector2D();
    System.out.println("сложение векторов "+ calculateVector2D.addition(vector2D1,vector2D2));
    System.out.println("умножение векторов "+ calculateVector2D.multiplication(vector2D1,vector2D2));
    System.out.println("вычитание векторов "+ calculateVector2D.subtraction(vector2D1,vector2D2));
    System.out.println("деление векторов "+ calculateVector2D.division(vector2D1,vector2D2));
}