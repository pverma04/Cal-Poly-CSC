import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorkSpace {
    private List<Shape> shapes = new ArrayList<Shape>();

    public void add(Shape shape) { this.shapes.add(shape); }
    public Shape get(int index) { return this.shapes.get(index); }
    public int size() { return this.shapes.size(); }
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<Circle>();
        for (Shape s : this.shapes) {
            if (s instanceof Circle) {
                circles.add((Circle) s);
            }
        }
        return circles;
    }
    public List<Rectangle> getRectangles() {
        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        for (Shape s : this.shapes) {
            if (s instanceof Rectangle) {
                rectangles.add((Rectangle) s);
            }
        }
        return rectangles;
    }
    public List<Triangle> getTriangles() {
        List<Triangle> triangles = new ArrayList<Triangle>();
        for (Shape s : this.shapes) {
            if (s instanceof Triangle) {
                triangles.add((Triangle) s);
            }
        }
        return triangles;
    }
    public List<Shape> getShapesByColor(Color color) {
        List<Shape> shapesByColor = new ArrayList<Shape>();
        for (Shape s : this.shapes) {
            if (s.getColor().equals(color)) { shapesByColor.add(s); }
        }
        return shapesByColor;
    }
    public double getAreaOfAllShapes() {
        double sum = 0;
        for (Shape s : this.shapes) {
            sum += s.getArea();
        }
        return sum;
    }
    public double getPerimeterOfAllShapes() {
        double sum = 0;
        for (Shape s : this.shapes) {
            sum += s.getPerimeter();
        }
        return sum;
    }

}
