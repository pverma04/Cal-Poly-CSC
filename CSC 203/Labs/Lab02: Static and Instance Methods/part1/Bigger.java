public class Bigger {
    public static double whichIsBigger(Circle circ, Rectangle rect, Polygon poly) {
        double c = Util.perimeter(circ);
        double r = Util.perimeter(rect);
        double p = Util.perimeter(poly);
        if (c >= r && c >= p) { return c; }
        else if (r >= c && r >= p) { return r; }
        else { return p; }
    }
}
