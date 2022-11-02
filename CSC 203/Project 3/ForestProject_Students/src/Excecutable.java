import processing.core.PImage;

import java.util.List;

public abstract class Excecutable extends Entity{

    public Excecutable(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public abstract void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler);
    public abstract void executeActivityAction(EventScheduler scheduler);
}
