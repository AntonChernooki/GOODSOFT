package models;

import interfaces.CalculateOperation;

public class CalculateDouble implements CalculateOperation<Double> {
    @Override
    public Double multiplication(Double a, Double b) {
        return a*b;
    }

    @Override
    public Double addition(Double a, Double b) {
        return a+b;
    }

    @Override
    public Double subtraction(Double a, Double b) {
        return a-b;
    }

    @Override
    public Double division(Double a, Double b) {
        if(b==0){
            throw new ArithmeticException("нельзя делить на ноль") ;
        }
        return a/b;
    }
}
