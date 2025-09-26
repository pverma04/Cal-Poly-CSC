import processing.core.PImage;

import java.util.List;

public abstract class ActionEntity extends Entity{
    int resourceLimit;
    int resourceCount;
    int actionPeriod;
    int healthLimit;
    //int health;

    public ActionEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, health, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.healthLimit = healthLimit;
    }
//    public int getResourceCount()
//    {
//        return this.resourceCount;
//    }
//    public int getResourceLimit()
//    {
//        return this.resourceLimit;
//    }
//    public int getActionPeriod()
//    {
//        return this.actionPeriod;
//    }
    public int getHealth() {
        return this.health;
    }
//    public int getHealthLimit() {
//        return this.healthLimit;
//    }
    public abstract void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler);
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        System.out.println("ActionEntity ScheduleAction");
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
        scheduler.scheduleEvent(this, this.createAnimationAction(0),this.getAnimationPeriod());
    }

}
