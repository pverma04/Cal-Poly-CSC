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
            ((ActionEntity)miner).scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }
    @Override
    public void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
        System.out.println("execute activity not full");
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Transform.class, SaplingEntity.class)));

        if (!target.isPresent() || !this.moveTo( world, target.get(), scheduler) || !this.transform( world, scheduler, imageStore))  {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),this.getActionPeriod());
        }
    }

    @Override
    public boolean moveTo(WorldModel world,Entity target,EventScheduler scheduler){
        if (world.adjacent(this.getPosition(), target.getPosition())) {
            this.resourceCount += 1;
            target.health--;
            return true;
        }
        else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant( nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents( occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

}
