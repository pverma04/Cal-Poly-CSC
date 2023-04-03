import processing.core.PImage;

import java.util.List;

public abstract class Moveable extends Active {
    private PathingStrategy pathStrat;

    public Moveable(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod,
                    PathingStrategy pathStrat) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.pathStrat = pathStrat;
    }

    public PathingStrategy getPathStrat() {
        return pathStrat;
    }

    abstract boolean moveTo(WorldModel world,
                            Entity target,
                            EventScheduler scheduler);


    public Point nextPosition(WorldModel world, Point destPos) {
        List<Point> PosList = getPathStrat().computePath(
                getPosition(),
                destPos,
                p -> !world.isOccupied(p) && world.withinBounds(p),
                (p1, p2) -> p1.neighbors(p2),
                PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS
        );
//        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
//        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
//            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.getPosition();
//            }
//        }
        if(PosList == null || PosList.isEmpty())
            return getPosition();
        return PosList.get(0);

//        return new Point(5, 5);

    }
}
