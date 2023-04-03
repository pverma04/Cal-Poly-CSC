import processing.core.PImage;

import java.util.List;

public class Sapling extends HasHealth implements Transformer{
    private int healthLimit;
    public static final String SAPLING_KEY = "sapling";
    private static final int SAPLING_HEALTH_LIMIT = 5;
    private static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_NUM_PROPERTIES = 4;
    public static final int SAPLING_ID = 0;
    public static final int SAPLING_COL = 1;
    public static final int SAPLING_ROW = 2;
    public static final int SAPLING_HEALTH = 3;
    public Sapling(String id, Point position, List<PImage> images, int health) {
        super(id, position, images, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, health);
        this.healthLimit = SAPLING_HEALTH_LIMIT;
    }
    public int getHealthLimit(){
        return healthLimit;
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

    public void execute(WorldModel world,
                        ImageStore imageStore,
                        EventScheduler scheduler){
        this.setHealth(1);
        if (!transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent( this,
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

            world.addEntity(stump);

            return true;
        }
        else if (this.getHealth() >= this.healthLimit)
        {
            Entity tree = new Tree("tree_" + this.getId(),
                    this.getPosition(),
                    imageStore.getImageList(Tree.TREE_KEY),
                    Functions.getNumFromRange(Tree.TREE_ANIMATION_MAX, Tree.TREE_ANIMATION_MIN),
                    Functions.getNumFromRange(Tree.TREE_ACTION_MAX, Tree.TREE_ACTION_MIN),
                    Functions.getNumFromRange(Tree.TREE_HEALTH_MAX, Tree.TREE_HEALTH_MIN));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

//    @Override
//    public boolean parse(String[] properties, ImageStore imageStore, WorldModel world) {
//        if (properties.length == SAPLING_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[SAPLING_COL]),
//                    Integer.parseInt(properties[SAPLING_ROW]));
//            String id = properties[SAPLING_ID];
//            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
//            Entity entity = new Sapling(id, pt, imageStore.getImageList(SAPLING_KEY), health);
//            world.tryAddEntity(entity);
//        }
//
//        return properties.length == Sapling.SAPLING_NUM_PROPERTIES;
//    }
}
