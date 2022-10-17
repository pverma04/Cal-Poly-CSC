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
}
