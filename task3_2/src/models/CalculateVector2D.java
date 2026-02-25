package models;

import interfaces.CalculateOperation;
/**
 * Реализация арифметических операций для двумерных векторов типа Vector2D.
 * Выполняет покомпонентные математические операции над векторами:
 */
public class CalculateVector2D implements CalculateOperation<Vector2D> {

    /**
     * Выполняет покомпонентное умножение двух векторов.
     * @param a первый вектор (множимое).
     * @param b второй вектор (множитель).
     * @return новый вектор с результатом покомпонентного умножения.
     */
    @Override
    public Vector2D multiplication(Vector2D a, Vector2D b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Векторы не могут быть null");
        }
        return new Vector2D(a.getX()* b.getX(),a.getY()*b.getY());
    }

    /**
     * Выполняет покомпонентное сложение двух векторов.
     * @param a первый вектор (слагаемое).
     * @param b второй вектор (слагаемое).
     * @return новый вектор с результатом покомпонентного сложения.
     */
    @Override
    public Vector2D addition(Vector2D a, Vector2D b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Векторы не могут быть null");
        }
        return new Vector2D(a.getX()+ b.getX(),a.getY()+b.getY());

    }

    /**
     * Выполняет покомпонентное вычитание второго вектора из первого.
     *
     * @param a первый вектор (уменьшаемое).
     * @param b второй вектор (вычитаемое).
     * @return новый вектор с результатом покомпонентного вычитания.
     */
    @Override
    public Vector2D subtraction(Vector2D a, Vector2D b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Векторы не могут быть null");
        }
        return new Vector2D(a.getX()- b.getX(),a.getY()-b.getY());
    }

    /**
     * Выполняет покомпонентное деление первого вектора на второй.
     * @param a первый вектор (делимое).
     * @param b второй вектор (делитель).
     * @return новый вектор с результатом покомпонентного деления.
     * @throws ArithmeticException если любая компонента вектора b равна 0.
     */
    @Override
    public Vector2D division(Vector2D a, Vector2D b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Векторы не могут быть null");
        }
        if (b.getX() == 0 || b.getY() == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        return new Vector2D(a.getX()/ b.getX(),a.getY()/b.getY());
    }
}