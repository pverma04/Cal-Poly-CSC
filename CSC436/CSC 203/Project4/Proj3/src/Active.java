import processing.core.PImage;

import java.util.List;

public abstract class Active extends Animated{
    private int actionPeriod;

    public Active(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod) {
        super(id, position, images, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod(){
        return actionPeriod;
    }

    public abstract void execute(WorldModel world,
                                 ImageStore imageStore,
                                 EventScheduler scheduler);
}
