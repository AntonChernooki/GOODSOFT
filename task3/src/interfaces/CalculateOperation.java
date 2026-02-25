package interfaces;

public interface CalculateOperation<T> {

    T multiplication(T a,T b);
    T addition(T a,T b);
    T subtraction(T a,T b);
    T division(T a,T b);
}
