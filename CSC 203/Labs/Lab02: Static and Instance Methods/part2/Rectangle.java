public class Rectangle {
    private final Point topLeft;
    private final Point bottomRight;

    //constructors
    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    //accessors
    public Point getTopLeft() {
        return this.topLeft;
    }
    public Point getBottomRight() {
        return this.bottomRight;
    }
    public double perimeter() {
        return (2 * (this.getTopLeft().getY() - this.getBottomRight().getY())) + (2 * (this.getBottomRight().getX() - this.getTopLeft().getX()));
    }
}
