package Models.Figure;

import Interface.FigureInterface;

public class Square implements FigureInterface {
    private double a;
    public Square(double a){
        this.a=a;
    }
    @Override
    public double calculateSquare() {

        return a*a;
    }

    @Override
    public void print() {
        System.out.println("Эта фигура с площадью "+ calculateSquare());

    }
}
