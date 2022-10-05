import java.util.List;
import java.util.Objects;
import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Entity
{
    private EntityKind kind;
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    private int health;
    private int healthLimit;



    public Entity(
            EntityKind kind,
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod,
            int health,
            int healthLimit) {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;
    }
    public Point getPos() {
        return this.position;
    }
    public void setPos(Point p) {
        this.position = p;
    }
    public EntityKind getEntityKind() {
        return this.kind;
    }
    public String getID() {
        return this.id;
    }
    public int getImageIndex() {
        return this.imageIndex;
    }
    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
    public List<PImage> getImages() {
        return this.images;
    }
    public int getResourceLimit() {
        return this.resourceLimit;
    }
    public int getResourceCount() {
        return this.resourceCount;
    }
    public void incrementResourceCount() {
        this.resourceCount++;
    }
    public int getActionPeriod() {
        return this.actionPeriod;
    }
    //public int getAnimationPeriod() {
    //return this.animationPeriod;
    //}
    public int getHealth() {
        return this.health;
    }
    public void incrementHealth() {
        this.health++;
    }
    public void decrementHealth() {
        this.health--;
    }
    public int getHealthLimit() {
        return this.healthLimit;
    }

    public int getAnimationPeriod() {
        switch (this.getEntityKind()) {
            case DUDE_FULL:
            case DUDE_NOT_FULL:
            case OBSTACLE:
            case FAIRY:
            case SAPLING:
            case TREE:
                return this.getAnimationPeriod();
            default:
                throw new UnsupportedOperationException(
                        String.format("getAnimationPeriod not supported for %s",
                                this.getEntityKind()));
        }
    }
    /*
    public Action createAnimationAction(int repeatCount) {
        return new Action(ActionKind.ANIMATION, this, null, null,
                repeatCount);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Action(ActionKind.ACTIVITY, this, world, imageStore, 0);
    }
    */
    public void nextImage() {
        this.setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    }
    public void executeSaplingActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.incrementHealth();
        if (!Functions.transformPlant(this, world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }
    public void executeTreeActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (!Functions.transformPlant(this, world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }
    public void executeFairyActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fairyTarget =
                Functions.findNearest(world, this.getPos(), new ArrayList<>(Arrays.asList(EntityKind.STUMP)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPos();

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
                Entity sapling = Functions.createSapling("sapling_" + this.getID(), tgtPos, imageStore.getImageList(Functions.SAPLING_KEY));

                world.addEntity(sapling);
                scheduler.scheduleActions(sapling, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), this.getActionPeriod());
    }
    public boolean moveToFairy(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Functions.adjacent(this.getPos(), target.getPos())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = this.nextPositionFairy(world, target.getPos());

            if (!this.getPos().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    public Point nextPositionFairy( WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPos().getX());
        Point newPos = new Point(this.getPos().getX() + horiz, this.getPos().getY());

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.getY() - this.getPos().getY());
            newPos = new Point(this.getPos().getX(), this.getPos().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPos();
            }
        }
        return newPos;
    }

    public void executeDudeNotFullActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target =
                Functions.findNearest(world, this.getPos(), new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));

        if (!target.isPresent() || !Functions.moveToNotFull(this, world,
                target.get(),
                scheduler)
                || !Functions.transformNotFull(this, world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }
    public void executeDudeFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                Functions.findNearest(world, this.getPos(), new ArrayList<>(Arrays.asList(EntityKind.HOUSE)));

        if (fullTarget.isPresent() && Functions.moveToFull(this, world,
                fullTarget.get(), scheduler))
        {
            Functions.transformFull(this, world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }
}
