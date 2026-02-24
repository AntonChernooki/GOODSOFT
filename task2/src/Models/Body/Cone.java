package Models.Body;

import Interface.BodyInterface;

public class Cone implements BodyInterface {
    private double radius;
    private double height;

    public Cone(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }


    @Override
    public double calculateVolume() {
        return Math.PI * radius * radius * height / 3;
    }

    @Override
    public void print() {
        System.out.println("Это тело с обьемом "+ calculateVolume());
    }
}
