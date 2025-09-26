public class Bigger {
    public static double whichIsBigger(Circle circ, Rectangle rect, Polygon poly) {
        double c = circ.perimeter();
        double r = rect.perimeter();
        double p = poly.perimeter();
        if (c >= r && c >= p) { return c; }
        else if (r >= c && r >= p) { return r; }
        else { return p; }
    }
}
