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
        if ((this.resourceCount) >= this.resourceLimit) {
            Entity miner = new DudeFullEntity(this.getId(),
                    this.getPosition(), this.getImages(), this.resourceLimit,
                    this.resourceCount, this.actionPeriod, this.getAnimationPeriod(),
                    this.health, this.healthLimit);
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
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(TreeEntity.class, SaplingEntity.class)));

        if (!target.isPresent() || !this.moveTo( world, target.get(), scheduler) || !this.transform( world, scheduler, imageStore))  {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),this.actionPeriod);
        }
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler){
        if (world.adjacent(this.getPosition(), target.getPosition())) {
            this.resourceCount += 1;
            target.health--;
            return true;
        }
        else {
            System.out.println("moveTo not full");
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents( occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

}
