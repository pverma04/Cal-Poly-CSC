import processing.core.PImage;

import java.util.List;

public abstract class ActionEntity extends AnimEntity{
    int resourceLimit;
    int resourceCount;
    int actionPeriod;
    int animationPeriod;
    int healthLimit;

    public ActionEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, health);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.healthLimit = healthLimit;
    }
    public int getResourceCount()
    {
        return this.resourceCount;
    }
    public int getResourceLimit()
    {
        return this.resourceLimit;
    }
    public int getActionPeriod()
    {
        return this.actionPeriod;
    }
    public int getHealth() {
        return this.health;
    }
    public int getHealthLimit() {
        return this.healthLimit;
    }
    public abstract void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler);
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.animationPeriod);
    }

}
