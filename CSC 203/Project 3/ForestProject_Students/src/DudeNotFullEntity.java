import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeNotFullEntity extends DudeEntity{
    public DudeNotFullEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));

        if (!target.isPresent() || !this.moveToNotFull( world, target.get(), scheduler) || !this.transformNotFull( world, scheduler, imageStore))  {
            scheduler.scheduleEvent(this,this.createActivityAction(world, imageStore),this.getActionPeriod());
        }
    }
}
