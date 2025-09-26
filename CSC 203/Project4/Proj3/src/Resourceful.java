import processing.core.PImage;

import java.util.List;

public abstract class Resourceful extends Moveable {
    public static final String DUDE_KEY = "dude";
    public static final int DUDE_NUM_PROPERTIES = 7;
    public static final int DUDE_ID = 1;
    public static final int DUDE_COL = 2;
    public static final int DUDE_ROW = 3;
    public static final int DUDE_LIMIT = 4;
    public static final int DUDE_ACTION_PERIOD = 5;
    public static final int DUDE_ANIMATION_PERIOD = 6;
    private int resourceLimit;
    private int resourceCount;

    public Resourceful(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod,
                       int resourceLimit, int resourceCount, PathingStrategy pathStrat) {
        super(id, position, images, animationPeriod, actionPeriod, pathStrat);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }
    public int getResourceLimit(){
        return resourceLimit;
    }
    public int getResourceCount(){
        return resourceCount;
    }
    public void setResourceCount(int inc){
        this.resourceCount += inc;
    }


//    public Point nextPosition(WorldModel world, Point destPos)
//    {
//        List<Point> PosList = getPathStrat().computePath(
//                getPosition(),
//                destPos,
//                p -> !world.isOccupied(p) && world.withinBounds(p),
//                (p1, p2) -> p1.neighbors(p2),
//                PathingStrategy.DIAGONAL_CARDINAL_NEIGHBORS
//        );
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
//        if(PosList.isEmpty())
//            return getPosition();
//
//        return PosList.get(0);

//        return new Point(5, 5);
//    }

    public boolean parse(String[] properties, ImageStore imageStore, WorldModel world){
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[DUDE_COL]),
                    Integer.parseInt(properties[DUDE_ROW]));
            Entity entity = new DudeNotFull(properties[DUDE_ID],
                    pt,
                    imageStore.getImageList(DUDE_KEY),
                    Integer.parseInt(properties[DUDE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[DUDE_ACTION_PERIOD]),
                    Integer.parseInt(properties[DUDE_LIMIT]),
                    0);
            world.tryAddEntity(entity);
        }

        return properties.length == Resourceful.DUDE_NUM_PROPERTIES;
    }

}
