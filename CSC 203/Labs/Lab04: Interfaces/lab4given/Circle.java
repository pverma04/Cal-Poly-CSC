import java.awt.Point;
import java.awt.Color;

public class Circle implements Shape{
    private double radius;
    private Point center;
    private Color color;
    public Circle (double radius, Point center, Color color) {
        this.radius = radius;
        this.center = center;
        this.color = color;
    }

    public double getRadius() {
        return this.radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }
    public Point getCenter() {
        return this.center;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (!o.getClass().equals(this.getClass())) { return false; }
        else {
            Circle c = (Circle) o;
            return this.radius == c.radius && this.color.equals(c.color) && this.center.x == c.center.x && this.center.y == c.center.y;
        }
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(this.radius, 2);
    }

    @Override
    public double getPerimeter() {
        return Math.PI * 2 * this.radius;
    }

    @Override
    public void translate(Point p) {
        this.center = p;
    }
}
