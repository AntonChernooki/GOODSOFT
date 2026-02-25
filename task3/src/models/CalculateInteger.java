package models;

import interfaces.CalculateOperation;

/**
 * Реализация арифметических операций для целых чисел.
 * <p>
 * Выполняет базовые математические операции над целыми числами.
 * </p>
 *
 */
public class CalculateInteger implements CalculateOperation<Integer> {

    /**
     * Выполняет операцию умножения двух целых чисел.
     *
     * @param a первый операнд (множимое).
     * @param b второй операнд (множитель).
     * @return произведение чисел a и b.
     */
    @Override
    public Integer multiplication(Integer a, Integer b) {
        return a*b;
    }

    //**
     * Выполняет операцию сложения двух целых чисел.
            *
            * @param a первый операнд (слагаемое).
            * @param b второй операнд (слагаемое).
            * @return сумма чисел a и b.
     */
    @Override
    public Integer addition(Integer a, Integer b) {
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
    public Integer subtraction(Integer a, Integer b) {
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
    public Integer division(Integer a, Integer b) {

        if(b==0){
            throw new ArithmeticException("нельзя делить на ноль") ;
        }
        return a/b;
    }
}
