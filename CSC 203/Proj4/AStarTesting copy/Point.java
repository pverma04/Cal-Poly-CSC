import java.util.List;
import java.util.Optional;

/**
 * A simple class representing a location in 2D space.
 */
public final class Point
{
   private final int x;
   private final int y;

   public Point(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public int getX(){
      return x;
   }

   public int getY(){
      return y;
   }

   public String toString() {
      return "(" + x + "," + y + ")";
   }

   public boolean equals(Object other) {
      return other instanceof Point && ((Point)other).x == this.x
              && ((Point)other).y == this.y;
   }

   public int hashCode() {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public boolean adjacent(Point p2) {
      return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) || (this.y == p2.y
              && Math.abs(this.x - p2.x) == 1);
   }

//   public Optional<Entity> nearestEntity(
//           List<Entity> entities)
//   {
//      if (entities.isEmpty()) {
//         return Optional.empty();
//      }
//      else {
//         Entity nearest = entities.get(0);
//         int nearestDistance = distanceSquared(nearest.getPosition());
//
//         for (Entity other : entities) {
//            int otherDistance = distanceSquared(other.getPosition());
//
//            if (otherDistance < nearestDistance) {
//               nearest = other;
//               nearestDistance = otherDistance;
//            }
//         }
//
//         return Optional.of(nearest);
//      }
//   }

   public int distanceSquared(Point p2) {
      int deltaX = this.x - p2.x;
      int deltaY = this.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }

   public double distance(Point p){
      return Math.sqrt(Math.pow(x-p.x, 2) + Math.pow(y-p.y, 2));
//      return Math.abs(x-p.x) + Math.abs(y-p.y);
   }

   public int manhattan(Point p){
      return Math.abs(x-p.x) + Math.abs(y-p.y);
   }

   public boolean neighbors(Point p){
      return x + 1 == p.getX() && y == p.getY() ||
              x - 1 == p.getX() && y == p.getY() ||
              x == p.getX() && y+1 == p.getY() ||
              x == p.getX() && y-1 == p.getY();
   }
}

