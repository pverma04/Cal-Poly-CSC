import processing.core.PImage;

import java.util.List;

public class TreeEntity extends Entity{
    public TreeEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public  void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,this.createActivityAction(world, imageStore),this.getActionPeriod());
        }
    }
}
