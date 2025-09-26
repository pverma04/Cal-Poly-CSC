public class Lazy extends Human{

    public Lazy(String name) {
        super(name);
    }

    public void takeTurn(Pile p) {
        p.remove(p.sticks());
        stop();
    }
}
