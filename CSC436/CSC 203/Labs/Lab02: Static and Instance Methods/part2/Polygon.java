import java.util.*;
public class Polygon {
    private final List<Point> points;

    //constructors
    public Polygon(List<Point> points) {
        this.points = points;
    }

    //accessors
    public List<Point> getPoints() {
        return this.points;
    }
    public double perimeter() {
        double rv = 0;
        Point secondPoint;
        int size = this.getPoints().size();
        for (int i = 0; i < size - 1; i++) {
            secondPoint = this.getPoints().get(i + 1);
            rv += Math.sqrt(Math.pow((secondPoint.getY() - this.getPoints().get(i).getY()), 2) + Math.pow((secondPoint.getX() - this.getPoints().get(i).getX()), 2));
        }
        Point last = this.getPoints().get(size - 1);
        rv += Math.sqrt(Math.pow((last.getY() - this.getPoints().get(0).getY()), 2) + Math.pow((last.getX() - this.getPoints().get(0).getX()), 2));
        return rv;
    }
}
