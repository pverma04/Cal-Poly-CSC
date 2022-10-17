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
}