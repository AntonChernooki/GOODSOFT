package models;

import interfaces.CalculateOperation;

public class CalculateVector2D implements CalculateOperation<Vector2D> {


    @Override
    public Vector2D multiplication(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX()* b.getX(),a.getY()*b.getY());
    }

    @Override
    public Vector2D addition(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX()+ b.getX(),a.getY()+b.getY());

    }

    @Override
    public Vector2D subtraction(Vector2D a, Vector2D b) {
        return new Vector2D(a.getX()- b.getX(),a.getY()-b.getY());
    }

    @Override
    public Vector2D division(Vector2D a, Vector2D b) {
        if (b.getX() == 0 || b.getY() == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Vector2D(a.getX()/ b.getX(),a.getY()/b.getY());
    }
}
