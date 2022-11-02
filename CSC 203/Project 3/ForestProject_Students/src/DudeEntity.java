import processing.core.PImage;

import java.util.List;

public abstract class DudeEntity extends Excecutable{
    public static final String DUDE_KEY = "dude";
    public static final int DUDE_NUM_PROPERTIES = 7;
    public static final int DUDE_ID = 1;
    public static final int DUDE_COL = 2;
    public static final int DUDE_ROW = 3;
    public static final int DUDE_LIMIT = 4;
    public static final int DUDE_ACTION_PERIOD = 5;
    public static final int DUDE_ANIMATION_PERIOD = 6;

    public DudeEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.executeActivity(scheduler.getWorldModel(), this.getImageStore(), scheduler);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
    }
}
