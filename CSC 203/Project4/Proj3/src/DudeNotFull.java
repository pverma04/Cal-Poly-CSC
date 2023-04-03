import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeNotFull extends Resourceful implements Transformer{

    public DudeNotFull(String id, Point position, List<PImage> images,
                       int animationPeriod, int actionPeriod, int resourceLimit, int resourceCount) {
        super(id, position, images, animationPeriod, actionPeriod,
                resourceLimit, resourceCount, PathingFactory.getStrategy("AStar"));
    }

    public void execute(WorldModel world,
                        ImageStore imageStore,
                        EventScheduler scheduler){
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Tree.class)));

        if (!target.isPresent() || !moveTo(world,
                target.get(),
                scheduler)
//                || !transform(world, scheduler, imageStore)
        )
        {
            scheduler.scheduleEvent(this,
                    createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        boolean alive = world.findNearest(getPosition(),
//                new ArrayList<>(Arrays.asList(Tree.class))).isPresent();
//        if (!alive) {
            Entity miner = new DudeFull(this.getId(),
                    this.getPosition(), imageStore.getImageList("sapling"),
                    20,
                    10,
                    this.getResourceLimit(),
                    this.getResourceCount());


            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            scheduleActions(scheduler, world, imageStore);

            return true;
        }

//        return false;
//    }


    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            this.setResourceCount(1);
            HasHealth healthTarget = (HasHealth)target;
            healthTarget.setHealth(-1);
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
