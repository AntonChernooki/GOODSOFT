package models.body;

import interfaces.BodyInterface;

public class Cylinder implements BodyInterface {
    private double radius;
    private double height;

    public Cylinder(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }
    @Override
    public double calculateVolume() {
        return Math.PI * radius * radius * height;
    }

    @Override
    public void print() {
        System.out.println("Это тело цилиндр с обьемом "+ calculateVolume());
    }
}
