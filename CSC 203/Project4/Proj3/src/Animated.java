import processing.core.PImage;

import java.util.List;

public abstract class Animated extends Entity{
    private int animationPeriod;

    public Animated(String id, Point position, List<PImage> images, int animationPeriod) {
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod(){
        return animationPeriod;
    }

    public abstract void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore);


}
