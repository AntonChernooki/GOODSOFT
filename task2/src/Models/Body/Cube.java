package Models.Body;

import Interface.BodyInterface;

public class Cube implements BodyInterface {
    public double a;
    public Cube(double a){
        this.a=a;
    }
    @Override
    public double calculateVolume() {
        return a*a*a;
    }

    @Override
    public void print() {
        System.out.println("Это тело с обьемом "+ calculateVolume());

    }
}
