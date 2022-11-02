import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeNotFullEntity extends DudeEntity implements Transform{
    public DudeNotFullEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            Entity miner = new DudeFullEntity(this.getId(),
                    this.getPosition(), this.getImages(), this.getResourceLimit(),
                    this.getResourceCount(), this.getActionPeriod(), this.getAnimationPeriod(),
                    this.health, this.getHealthLimit());
            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }
    @Override
    public void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Transform.class, SaplingEntity.class)));

        if (!target.isPresent() || !this.moveToNotFull( world, target.get(), scheduler) || !this.transform( world, scheduler, imageStore))  {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),this.getActionPeriod());
        }
    }

    @Override
    public void executeActivityAction(EventScheduler scheduler) {

    }
}
