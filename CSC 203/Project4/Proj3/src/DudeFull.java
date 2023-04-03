import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeFull extends Resourceful implements Transformer{

    public DudeFull(String id,
                    Point position,
                    List<PImage> images,
                    int animationPeriod,
                    int actionPeriod,
                    int resourceLimit,
                    int resourceCount) {
        super(id,
                position,
                images,
                animationPeriod,
                actionPeriod,
                resourceLimit,
                resourceCount,
                PathingFactory.getStrategy("Single Step"));
    }

    public void execute(WorldModel world,
                        ImageStore imageStore,
                        EventScheduler scheduler){
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Obstacle.class)));

        if (fullTarget.isPresent() && moveTo(world,
                fullTarget.get(), scheduler))
        {
            world.removeEntity(this);
//            transform(world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }

    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity miner = new DudeFull(this.getId(),
                this.getPosition(), this.getImages(),
                getAnimationPeriod(),
                this.getActionPeriod(),
                this.getResourceLimit(),
                this.getResourceCount());

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        scheduleActions(scheduler, world, imageStore);
        return false;
    }


    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        }
        else {
            Point nextPos = nextPosition(world, target.getPosition());
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

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                createActivityAction(world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                createAnimationAction(0),
                getAnimationPeriod());
    }
}
