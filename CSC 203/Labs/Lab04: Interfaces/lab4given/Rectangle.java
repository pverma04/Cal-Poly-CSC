import java.awt.Point;
import java.awt.Color;

public class Rectangle implements Shape{
    private double width;
    private double height;
    private Point topLeft;
    private Color color;

    public Rectangle(double width, double height, Point topLeft, Color color) {
        this.width = width;
        this.height = height;
        this.topLeft = topLeft;
        this.color = color;
    }
    public double getWidth() { return this.width; }
    public void setWidth(double width) { this.width = width; }
    public double getHeight() { return this.height; }
    public void setHeight(double height) {this.height = height; }
    public Point getTopLeft() { return this.topLeft; }
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (!o.getClass().equals(this.getClass())) { return false; }
        else {
            Rectangle r = (Rectangle) o;
            return this.width == r.width && this.height == r.height && this.color.equals(r.color) && this.topLeft.x == r.topLeft.x && this.topLeft.y == r.topLeft.y;
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
        return this.height * this.width;
    }

    @Override
    public double getPerimeter() {
        return (2 * this.width) + (2 * this.height);
    }

    @Override
    public void translate(Point p) {
        this.topLeft = p;
    }
}
