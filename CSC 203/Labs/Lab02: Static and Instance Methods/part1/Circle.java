
public class Circle {
    private final Point center;
    private final double radius;

    //constructors
    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    //accessors
    public Point getCenter() {
        return this.center;
    }
    public double getRadius() {
        return this.radius;
    }

}
