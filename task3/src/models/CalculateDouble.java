package models;

import interfaces.CalculateOperation;
/**
 * Реализация арифметических операций для дробных чисел.
 * Выполняет базовые математические операции.
 *
 */
public class CalculateDouble implements CalculateOperation<Double> {
    /**
     * Выполняет операцию умножения двух чисел.
     *
     * @param a первый операнд (множимое).
     * @param b второй операнд (множитель).
     * @return произведение чисел a и b.
     */
    @Override
    public Double multiplication(Double a, Double b) {
        return a*b;
    }

    /**
     * Выполняет операцию сложения двух чисел.
     *
     * @param a первый операнд (слагаемое).
     * @param b второй операнд (слагаемое).
     * @return сумма чисел a и b.
     */
    @Override
    public Double addition(Double a, Double b) {
        return a+b;
    }

    /**
     * Выполняет операцию вычитания второго числа из первого.
     *
     * @param a первый операнд (уменьшаемое).
     * @param b второй операнд (вычитаемое).
     * @return разность чисел a и b.
     */
    @Override
    public Double subtraction(Double a, Double b) {
        return a-b;
    }

    /**
     * Выполняет операцию деления первого числа на второе.
     *
     * @param a первый операнд (делимое).
     * @param b второй операнд (делитель).
     * @return частное от деления a на b.
     * @throws ArithmeticException если делитель b равен 0.
     */
    @Override
    public Double division(Double a, Double b) {
        if(b==0){
            throw new ArithmeticException("нельзя делить на ноль") ;
        }
        return a/b;
    }
}
