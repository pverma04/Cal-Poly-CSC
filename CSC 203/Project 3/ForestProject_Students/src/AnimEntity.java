import processing.core.PImage;

import java.util.List;

public abstract class AnimEntity extends Entity{
    public AnimEntity(String id, Point position, List<PImage> images, int health) {
        super(id, position, images, health);
    }
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);

}
