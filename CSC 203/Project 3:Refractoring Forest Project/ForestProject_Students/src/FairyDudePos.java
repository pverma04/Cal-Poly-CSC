import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class FairyDudePos extends ActionEntity{

    public FairyDudePos(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public abstract boolean moveTo(WorldModel world,Entity target,EventScheduler scheduler);
    public abstract Point nextPosition(WorldModel world, Point destPos);
}
