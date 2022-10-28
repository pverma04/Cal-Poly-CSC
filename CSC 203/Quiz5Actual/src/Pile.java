/*
Do NOT modify this class!
 */
public class Pile
{
   private int sticks;

   public Pile (int sticks)
   {
      this.sticks = sticks;
   }

   public int sticks() {
      return sticks;
   }

   public void remove (int numSticks)
   {
      sticks -= numSticks;
   }
}
