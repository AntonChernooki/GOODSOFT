package models.body;

import interfaces.BodyInterface;

public class Cube implements BodyInterface {
    private double a;
    public Cube(double a){
        this.a=a;
    }
    @Override
    public double calculateVolume() {
        return a*a*a;
    }

    @Override
    public void print() {
        System.out.println("Это тело куб с обьемом "+ calculateVolume());

    }
}
