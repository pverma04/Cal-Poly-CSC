/*
You MAY modify this class!
 */

import java.io.File;
import java.util.*;

public class Game
{
   private static Player p1, p2;
   private static Pile pile;

   public static void main(String[] args) {

      Scanner in;

      try {
      in = new Scanner(new File(args[0]));//used for testing
      }
      catch (Exception e) {
      in = new Scanner(System.in);
      }

      if(!setUpGame(in)) return;


      boolean done=false;
      while(!done)
      {
         done = play(p1);
         if (done)
         {
            System.out.println("\n" + p2.toString()+ " is the winner!!!");
         }
         else
         {
            done = play(p2);
            if (done)
               System.out.println("\n" + p1.toString()+ " is the winner!!!");
         }    
      }
   }

   public static boolean setUpGame(Scanner in){

      System.out.print("\nHow many sticks? ");
      int numSticks = in.nextInt();//assume that positive integer is entered
      pile = new Pile(numSticks);

      System.out.println("Create Player 1...");
      p1 = createPlayer(in);
      if(p1==null)
         return false;


      System.out.println("\nCreate Player 2...");
      p2 = createPlayer(in);
      if(p2==null)
         return false;


   return true;
   }


   public static Player createPlayer(Scanner in)
   {
      Player p;
      String name;
      int type;
      
      System.out.print("Player's name? ");
      name = in.next();
      p = new Player(name);

      System.out.print("Player type (0 - Human, 1 - Greedy, 2 - Lazy)? ");
      type = in.nextInt();

      if (type == 0) p = new Human(name);
      else if (type == 1)
      {
         in.nextLine(); // remove \n from input stream
         System.out.print("Taunt? ");
         String taunt = in.nextLine();  // gets whole line
         //p.kind = PlayerKind.GREEDY;
         p = new Greedy(name, taunt);
      }
      else if (type == 2)  p = new Lazy(name);
      else p = null;
      
      return p;   
   }
   
   public static boolean play(Player pl)
   {
      switch (pl.kind){
         case HUMAN:
            ((Human)pl).takeTurn(pile);
            break;
         case GREEDY:
            ((Greedy)pl).takeTurn(pile);
            break;
         case LAZY:
            ((Lazy)pl).takeTurn(pile);
            break;

      }

      System.out.println("\n"+ pl+ " takes " + pl.sticksTaken + " sticks.\n" +
       "There are " + pile.sticks() + " left in the pile. \n");
            
      if (pile.sticks() <= 0)
         return true;
      return false;      
   }
}

