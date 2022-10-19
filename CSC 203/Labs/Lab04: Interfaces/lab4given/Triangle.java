import java.awt.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Triangle implements Shape{
    private Point a;
    private Point b;
    private Point c;
    private Color color;
    public Triangle (Point a, Point b, Point c, Color color) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.color = color;
    }
    public Point getVertexA() { return this.a; }
    public Point getVertexB() { return this.b; }
    public Point getVertexC() { return this.c; }
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (!o.getClass().equals(this.getClass())) { return false; }
        else {
            Triangle t = (Triangle) o;
            return this.color.equals(t.color) &&
                    this.a.x == t.a.x && this.a.y == t.a.y &&
                    this.b.x == t.b.x && this.b.y == t.b.y &&
                    this.c.x == t.c.x && this.c.y == t.c.y;
        }
    }
    private double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
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
    public double getArea() { //shoelace forumla
        return Math.abs(0.5 * ((a.x - c.x) * (b.y - a.y) - (a.x - b.x) * (c.y - a.y)));
    }

    @Override
    public double getPerimeter() {
        double sideAB = this.distance(this.a, this.b);
        double sideBC = this.distance(this.b, this.c);
        double sideCA = this.distance(this.c, this.a);
        return sideAB + sideBC + sideCA;
    }

    @Override
    public void translate(Point p) {
        this.a.x += p.x;
        this.a.y += p.y;
        this.b.x += p.x;
        this.b.y += p.y;
        this.c.x += p.x;
        this.c.y += p.y;
    }
}
