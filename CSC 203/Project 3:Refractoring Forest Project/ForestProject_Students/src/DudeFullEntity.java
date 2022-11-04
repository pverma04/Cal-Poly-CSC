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

        if (fullTarget.isPresent() && this.moveTo(world, fullTarget.get(), scheduler)) {
            this.transform(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),this.actionPeriod);
        }
    }

    public void transform(WorldModel world,EventScheduler scheduler,ImageStore imageStore) {
        Entity miner = Entity.createDudeFull(this.getId(),
                this.getPosition(),
                this.actionPeriod,
                this.getAnimationPeriod(),
                this.resourceLimit,
                this.getImages());
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);
        world.addEntity(miner);
        ((ActionEntity)miner).scheduleActions(scheduler, world, imageStore);
    }
    public boolean moveTo(WorldModel world,Entity target,EventScheduler scheduler){
        if (world.adjacent(this.getPosition(), target.getPosition())) {
            return true;
        }
        else {
            System.out.println("moveTo full");
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
}
