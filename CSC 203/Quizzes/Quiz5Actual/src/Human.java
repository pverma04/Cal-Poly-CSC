import java.util.Scanner;

public class Human extends Player{

    public Human(String name) {
        super(name);
    }
    public void takeTurn(Pile p) {
        System.out.println(this.toString() +": How many sticks do you want to take? (1,2 or 3) ?");
        int i;
        Scanner in = new Scanner(System.in);
        i= in.nextInt();//Please assume that student enters an integer
        if(i>3 || i<0)
        {
            System.out.println("Sorry, but you are disqualified and lost.");
            sticksTaken=0;
            p.remove(p.sticks());
            stop();
            return;
        }
        sticksTaken= (Math.min(i,p.sticks()));
        takeSticks(sticksTaken,p);
    }
}
