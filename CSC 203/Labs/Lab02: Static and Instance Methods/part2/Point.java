public class Point {
    // data
    private double x;
    private double y;

    // constructors
    public Point() {
        this.x = 0;
        this.y = 0;
    }
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // accessors
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getRadius() {
        return Math.sqrt(Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0));
    }
    public double getAngle() {
        return Math.atan2(this.y, this.x);
    }
    public Point rotate90() {
        double newX = this.x;
        double newY = this.y;
        return new Point((this.y * -1.0), this.x);
    }
}
