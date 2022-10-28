/*
You MAY modify this class!
    TO DO:
    a.) Refactor code using the design principles you learned in class


    c.) Override toString if you wish (not necessary)
 */
import java.util.Objects;
import java.util.Scanner;

public class Player {

    //If you move the variables to other classes, do not change their name!
    public PlayerKind kind;
    public String name;
    public int sticksTaken;
//    public String taunt;

    //do not change the argument list of this constructor in the Player class!
    public Player(String name) {
        this.name = name;
        this.sticksTaken = 0;
        this.kind=PlayerKind.HUMAN;
    }


//    public void takeTurn(Pile p)
//    {
//        switch(kind){
//            case HUMAN:
//                takeTurnHuman(p);
//                break;
//            case GREEDY:
//                takeTurnGreedy(p);
//                break;
//            case LAZY:
//                takeTurnLazy(p);
//                break;
//        }
//    }

//    public void takeTurnHuman(Pile p) {
//        System.out.println(this.toString() +": How many sticks do you want to take? (1,2 or 3) ?");
//        int i;
//        Scanner in = new Scanner(System.in);
//        i= in.nextInt();//Please assume that student enters an integer
//        if(i>3 || i<0)
//        {
//            System.out.println("Sorry, but you are disqualified and lost.");
//            sticksTaken=0;
//            p.remove(p.sticks());
//            stop();
//            return;
//        }
//        sticksTaken= (Math.min(i,p.sticks()));
//        takeSticks(sticksTaken,p);
//    }

//    public void takeTurnGreedy(Pile p) {
//
//        if (p.sticks() > 3)
//            sticksTaken=3;
//        else
//            sticksTaken= (p.sticks());
//        p.remove(sticksTaken);
//
//        System.out.print(taunt);
//
//    }

//    public void takeTurnLazy(Pile p) {
//        p.remove(p.sticks());
//        stop();
//    }

    //IMPORTANT:  Any player should have the option to stop
    public void stop() {
        System.out.println(this + " has left the game.");
    }

    //IMPORTANT:  Any player should have the option to take Sticks
    public boolean takeSticks(int num, Pile p) {
        if(p==null)
            return false;
        if(p.sticks()<num)
            return false;
        p.remove(num);
        return true;
    }
}
