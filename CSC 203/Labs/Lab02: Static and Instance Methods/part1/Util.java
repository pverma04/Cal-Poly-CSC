public class Util {
    private final Object shape;

    public Util(Object shape) {
        this.shape = shape;
    }


    public static double perimeter(Circle c) {
        return Math.PI * 2 * c.getRadius();
    }
    public static double perimeter(Rectangle r) {
        return (2 * (r.getTopLeft().getY() - r.getBottomRight().getY())) + (2 * (r.getBottomRight().getX() - r.getTopLeft().getX()));
    }

    public static double perimeter(Polygon p) {
        double rv = 0;
        Point secondPoint;
        int size = p.getPoints().size();
        for (int i = 0; i < size - 1; i++) {
            secondPoint = p.getPoints().get(i + 1);
            rv += Math.sqrt(Math.pow((secondPoint.getY() - p.getPoints().get(i).getY()), 2) + Math.pow((secondPoint.getX() - p.getPoints().get(i).getX()), 2));
        }
        Point last = p.getPoints().get(size - 1);
        rv += Math.sqrt(Math.pow((last.getY() - p.getPoints().get(0).getY()), 2) + Math.pow((last.getX() - p.getPoints().get(0).getX()), 2));
        return rv;
    }

}
