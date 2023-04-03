import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Fairy extends Moveable implements Transformer{
    public static final String FAIRY_KEY = "fairy";
    public static final int FAIRY_NUM_PROPERTIES = 6;
    public static final int FAIRY_ID = 1;
    public static final int FAIRY_COL = 2;
    public static final int FAIRY_ROW = 3;
    public static final int FAIRY_ANIMATION_PERIOD = 4;
    public static final int FAIRY_ACTION_PERIOD = 5;
    public Fairy(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod) {
        super(id, position, images, animationPeriod, actionPeriod, PathingFactory.getStrategy("Dijkstra"));
    }

    public void execute(WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler){
        Optional<Entity> fairyTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Tree.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();
            if (moveTo(world, fairyTarget.get(), scheduler)) {
                Entity sapling = new Stump("sapling_" + this.getId(), tgtPos,
                        imageStore.getImageList("stump"));

                world.addEntity(sapling);
                scheduleActions(scheduler, world, imageStore);
            }

//                moveTo(world, fairyTarget.get(), scheduler);
        }

        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                this.getActionPeriod());
    }


@Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
                Point nextPos = nextPosition(world, target.getPosition());
                if (!this.getPosition().equals(nextPos)) {
                    Optional<Entity> occupant = world.getOccupant(nextPos);
                    if (occupant.isPresent()) {
                        scheduler.unscheduleAllEvents(occupant.get());
                    }
                    world.moveEntity(this, nextPos);
                }
            }
            return false;
        }



//    @Override
//    public Point nextPosition(WorldModel world, Point destPos) {
//
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
//
//        return new Point(5, 5);
//
//
//    }

//    public boolean neighbors(Point p1, Point p2){
//        return p1.getX() + 1 == p2.getX() && p1.getY() == p2.getY() ||
//                p1.getX() - 1 == p2.getX() && p1.getY() == p2.getY() ||
//                p1.getX() == p2.getX() && p1.getY()+1 == p2.getY() ||
//                p1.getX() == p2.getX() && p1.getY()-1 == p2.getY();
//    }



    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                createAnimationAction(0),
                getAnimationPeriod());
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity miner = new DudeFull(this.getId(),
                this.getPosition(), imageStore.getImageList("sapling"),
                20,
                10,
                0,
                0);


        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        scheduleActions(scheduler, world, imageStore);

        return true;
    }

//    @Override
//    public boolean parse(String[] properties, ImageStore imageStore, WorldModel world) {
//        if (properties.length == FAIRY_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[FAIRY_COL]),
//                    Integer.parseInt(properties[FAIRY_ROW]));
//            Entity entity = new Fairy(properties[FAIRY_ID],
//                    pt,
//                    imageStore.getImageList(FAIRY_KEY),
//                    Integer.parseInt(properties[FAIRY_ANIMATION_PERIOD]),
//                    Integer.parseInt(properties[FAIRY_ACTION_PERIOD]));
//            world.tryAddEntity(entity);
//        }
//
//        return properties.length == Fairy.FAIRY_NUM_PROPERTIES;
//    }
}
