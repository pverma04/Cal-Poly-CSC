import java.util.Comparator;

public class Node implements Comparable<Node> {
    private Point p;
    private Node prev;
    //private Node next;
    private int g, h, f; //g is distance from start, h is distance from goal, f = g + f
    private boolean checked;

//    public Node(Point p, Point goalPoint) {
//        this.p = p;
//        this.g = this.prev.g + 1;
//        this.h = this.findH(goalPoint);
//        this.f = this.g + this.h;
//    }
    public Node(Node prev, Point p, Point end) {
        this.p = p;
        this.h = this.findH(end);
        if (prev != null) { this.g = prev.g + 1; }
        else { this.g = 0; }
        this.f = this.g + this.h;
        this.prev = prev;
    }

    public int findH(Point goal){
        int a = Math.abs(goal.y - this.p.y);
        int b = Math.abs(goal.x - this.p.x);
        return a + b;
    }
    public void setF(Node prev) { this.g = prev.g + 1; }
    public int getF() { return this.f; }
    public boolean equals(Node n) { return this.p.x == n.p.x && this.p.y == n.p.y; }
    public Point getP() { return this.p; }
    public int getG() { return this.g; }
    public int getH() { return this.h; }
    public Node getPrev() { return this.prev; }

    public boolean equals(Object o1) {
        return o1 instanceof Node && ((Node) o1).p.x == this.p.x && ((Node) o1).p.y == this.p.y &&
                ((Node) o1).f == this.f &&
                ((Node) o1).h == this.h;
    }
    public String toString() {
        return this.getP().x + ", " + this.getP().y;
    }

    public int compareTo(Node o) {
        if (o.f > this.f ) {
            return -1;
        } else if (o.f < this.f ){
            return 1;
        }
        else {
            if (o.g > this.g) {
                return -1;
            } else if (o.g < this.g) {
                return 1;
            }
            else {
                if (o.p.x < this.p.x) {
                    return -1;
                } else if (o.p.x > this.p.x) {
                    return 1;
                }
                else {
                    if (o.p.y < this.p.y) {
                        return -1;
                    } else if (o.p.y > this.p.y) {
                        return 1;
                    }
                    else {
                        return 0;
                    }
                }
            }
        }
//        if (o.f == this.f ) { return 0; }
//        else if (o.f > this.f ) { return -1; }
//        else { return 1; }
    }
}
