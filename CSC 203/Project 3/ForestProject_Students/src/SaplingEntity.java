import processing.core.PImage;

import java.util.List;

public class SaplingEntity extends PlantEntity implements Transform{
    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_NUM_PROPERTIES = 4;
    public static final int SAPLING_ID = 1;
    public static final int SAPLING_COL = 2;
    public static final int SAPLING_ROW = 3;
    public static final int SAPLING_HEALTH = 4;
    public SaplingEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
        this.health++;
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }
    @Override
    public  boolean transform(WorldModel world,EventScheduler scheduler,ImageStore imageStore){
        if (this.health <= 0) {
            Entity stump = new StumpEntity(this.getId(),this.getPosition(),imageStore.getImageList(STUMP_KEY),
                    this.resourceLimit, this.resourceCount, this.actionPeriod, this.animationPeriod,
                    this.health, this.healthLimit);
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(stump);

            return true;
        }
        else if (this.getHealth() >= this.getHealthLimit()) {
            Entity tree = new TreeEntity("tree_" + this.getId(), this.getPosition(),
                    imageStore.getImageList(TREE_KEY),
                    this.getResourceLimit(), this.getResourceCount(),
                    this.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
                    this.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
                    this.getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
                    this.healthLimit
            );
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }
}
