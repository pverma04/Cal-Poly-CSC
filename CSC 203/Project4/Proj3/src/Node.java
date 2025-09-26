import java.util.Comparator;
import java.util.List;

public class Node {
    public Point point;
    public double g;
    public int h;
    public double f;
    public Node prior;
    public Node(Point point, double g, int h, double f, Node prior){
        this.point = point;
        this.g = g;
        this.h = h;
        this.f = f;
        this.prior = prior;
    }

    public boolean equals(Object other){
        return other instanceof Node && ((Node)other).point.equals(this.point);
//                && ((Node)other).f == this.f;
    }

    public int hashCode(){
        return 2* point.getX() + 3* point.getY();
    }

}
