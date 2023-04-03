public class Vertex {
    public Point point;
    public double weight;
    public Vertex prior;
    public Vertex(Point point, double weight, Vertex prior){
        this.point = point;
        this.weight = weight;
        this.prior = prior;
    }

    public boolean equals(Object other){
        return other instanceof Vertex && ((Vertex)other).point.equals(this.point);
//                && ((Node)other).f == this.f;
    }

    public int hashCode(){
        return 5* point.getX() + 6* point.getY();
    }

}

