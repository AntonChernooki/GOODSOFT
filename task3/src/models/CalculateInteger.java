package models;

import interfaces.CalculateOperation;

public class CalculateInteger implements CalculateOperation<Integer> {


    @Override
    public Integer multiplication(Integer a, Integer b) {
        return a*b;
    }

    @Override
    public Integer addition(Integer a, Integer b) {
        return a+b;
    }

    @Override
    public Integer subtraction(Integer a, Integer b) {
        return a-b;
    }

    @Override
    public Integer division(Integer a, Integer b) {

        if(b==0){
            throw new ArithmeticException("нельзя делить на ноль") ;
        }
        return a/b;
    }
}
