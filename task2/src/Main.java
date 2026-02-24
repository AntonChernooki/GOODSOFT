import interfaces.BodyInterface;
import interfaces.Entity;
import interfaces.FigureInterface;
import models.body.Cone;
import models.body.Cube;
import models.body.Cylinder;
import models.figure.Square;
import models.figure.Trapezoid;
import models.figure.Triangle;


void main() {

    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Entity triangle =new Triangle(3,3,3);
    Entity square = new Square(5);
    Entity trapezoid =new Trapezoid(10,6,5);
    Entity cone =new Cone(10,20);
    Entity cube =new Cube(4);
    Entity cylinder=new Cylinder(5,10);

    List<Entity> allEntity=new ArrayList<>();
    allEntity.add(triangle);
    allEntity.add(square);
    allEntity.add(trapezoid);
    allEntity.add(cone);
    allEntity.add(cube);
    allEntity.add(cylinder);
    for (Entity entity:allEntity) {
        entity.print();
        
    }
    
}
