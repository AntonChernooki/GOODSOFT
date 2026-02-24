package models.figure;

import interfaces.FigureInterface;

public class Trapezoid implements FigureInterface {
    private double a;
    private double b;
    private double height;

    public Trapezoid(double a,double b,double height){
        this.a=a;
        this.b=b;
        this.height=height;
    }
    @Override
    public double calculateSquare() {
        return (a+b)*height/2;
    }

    @Override
    public void print() {
        System.out.println("Эта фигура трапеция с площадью "+ calculateSquare());

    }
}
