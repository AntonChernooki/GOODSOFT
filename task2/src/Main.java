import Models.Body.Cone;
import Models.Body.Cube;
import Models.Body.Cylinder;
import Models.Figure.Square;
import Models.Figure.Trapezoid;
import Models.Figure.Triangle;


void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    Triangle triangle =new Triangle(3,3,3);
    triangle.print();
    Square square = new Square(5);
    square.print();
    Trapezoid trapezoid =new Trapezoid(10,6,5);
    trapezoid.print();
    Cone cone =new Cone(10,20);
    cone.print();
    Cube cube =new Cube(4);
    cube.print();
    Cylinder cylinder=new Cylinder(5,10);
    cylinder.print();
}
