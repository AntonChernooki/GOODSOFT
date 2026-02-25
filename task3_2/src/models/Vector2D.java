package models;

/**
 * Класс представляет двумерный вектор с координатами (x, y).
 */
public class Vector2D {
    private double x;
    private double y;

    /**
     * Конструктор для создания двумерного вектора.
     *
     * @param x координата вектора по оси X.
     * @param y координата вектора по оси Y.
     */
    public Vector2D(double x,double y)
    {
        this.x=x;
        this.y=y;
    }

    /**
     * Возвращает координату вектора по оси X.
     *
     * @return значение координаты x.
     */
    public double getX() {
        return x;
    }

    /**
     * Возвращает координату вектора по оси Y.
     * @return значение координаты y.
     */
    public double getY() {
        return y;
    }

    /**
     * Устанавливает новое значение координаты по оси X.
     * @param x новое значение координаты x.
     */
    public void setX(double x){
        this.x=x;
    }

    /**
     * Устанавливает новое значение координаты по оси Y.
     * @param y новое значение координаты y.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Возвращает строковое представление вектора
     * @return строка с координатами вектора.
     */
    public String toString(){
        return "вектор с координатами {" +getX()+" "+ getY()+"}";
    }
}