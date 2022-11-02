import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeFullEntity extends DudeEntity{

    public DudeFullEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    @Override
    public void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(HouseEntity.class)));

        if (fullTarget.isPresent() && this.moveToFull(world, fullTarget.get(), scheduler)) {
            this.transform(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),this.getActionPeriod());
        }
    }

    @Override
    public void executeActivityAction(EventScheduler scheduler) {

    }

    public void transform(WorldModel world,EventScheduler scheduler,ImageStore imageStore) {
        Entity miner = new DudeNotFullEntity(this.getId(),
                this.getPosition(), this.getImages(), this.getResourceLimit(), this.getResourceCount(),
                this.getActionPeriod(), this.getAnimationPeriod(), this.health, this.getHealthLimit()
        );
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);
        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }
}
