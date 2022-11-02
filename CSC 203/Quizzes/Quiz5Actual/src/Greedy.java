public class Greedy extends Human{

    private String taunt;
    public Greedy(String name, String taunt) {
        super(name);
        this.taunt = taunt;
    }
    public void setTaunt(String taunt) {
        this.taunt = taunt;
    }
    public void takeTurn(Pile p) {
        if (p.sticks() > 3)
            this.sticksTaken=3;
        else
            this.sticksTaken= (p.sticks());
        p.remove(this.sticksTaken);
        System.out.print(this.taunt);
    }
}
