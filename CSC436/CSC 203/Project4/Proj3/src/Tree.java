import processing.core.PImage;

import java.util.List;

public class Tree extends HasHealth implements Transformer {
    public static final String TREE_KEY = "tree";
    public static final int TREE_NUM_PROPERTIES = 7;
    public static final int TREE_ID = 1;
    public static final int TREE_COL = 2;
    public static final int TREE_ROW = 3;
    public static final int TREE_ANIMATION_PERIOD = 4;
    public static final int TREE_ACTION_PERIOD = 5;
    public static final int TREE_HEALTH = 6;
    public static final int TREE_ANIMATION_MAX = 600;
    public static final int TREE_ANIMATION_MIN = 50;
    public static final int TREE_ACTION_MAX = 1400;
    public static final int TREE_ACTION_MIN = 1000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;
    public Tree(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod,
                int health) {
        super(id, position, images, animationPeriod, actionPeriod, health);
    }

    public void execute(WorldModel world,
                                 ImageStore imageStore,
                                 EventScheduler scheduler){
        if (!transform(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }



    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            Entity stump = new Stump(this.getId(),
                    this.getPosition(),
                    imageStore.getImageList("stump"));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            System.out.println("GAME OVER");
            world.addEntity(stump);

            return true;
        }

        return false;
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                createAnimationAction(0),
                getAnimationPeriod());
    }

//    @Override
//    public boolean parse(String[] properties, ImageStore imageStore, WorldModel world) {
//        if (properties.length == TREE_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[TREE_COL]),
//                    Integer.parseInt(properties[TREE_ROW]));
//            Entity entity = new Tree(properties[TREE_ID],
//                    pt,
//                    imageStore.getImageList(TREE_KEY),
//                    Integer.parseInt(properties[TREE_ANIMATION_PERIOD]),
//                    Integer.parseInt(properties[TREE_ACTION_PERIOD]),
//                    Integer.parseInt(properties[TREE_HEALTH]));
//            world.tryAddEntity(entity);
//        }
//
//        return properties.length == TREE_NUM_PROPERTIES;
//    }
}
