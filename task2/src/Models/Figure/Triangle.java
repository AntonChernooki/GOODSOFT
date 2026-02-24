package Models.Figure;

import Interface.FigureInterface;

public class Triangle implements FigureInterface {
    private double a;
    private double b;
    private double c;

    public Triangle(double a,double b,double c){
        this.a=a;
        this.b=b;
        this.c=c;
    }
    @Override
    public double calculateSquare() {

        double poluPerimetr=(a+b+c)/2;
        return Math.sqrt(poluPerimetr*(poluPerimetr-a)*(poluPerimetr-b)*(poluPerimetr-c));

    }

    @Override
    public void print() {
        System.out.println("Эта фигура с площадью "+ calculateSquare());

    }
}
