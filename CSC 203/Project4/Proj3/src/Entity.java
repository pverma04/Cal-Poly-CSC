import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity implements CurrentImage
{
//    public EntityKind kind;
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
//    public int resourceLimit;
//    public int resourceCount;
//    public int actionPeriod;
//    public int animationPeriod;
//    public int health;
//    public int healthLimit;
//    private static final int TREE_ANIMATION_MAX = 600;
//    private static final int TREE_ANIMATION_MIN = 50;
//    private static final int TREE_ACTION_MAX = 1400;
//    private static final int TREE_ACTION_MIN = 1000;
//    private static final int TREE_HEALTH_MAX = 3;
//    private static final int TREE_HEALTH_MIN = 1;
//    private static final String STUMP_KEY = "stump";




    public Entity(
//            EntityKind kind,
            String id,
            Point position,
            List<PImage> images
//            int resourceLimit,
//            int resourceCount,
//            int actionPeriod,
//            int animationPeriod,
//            int health,
//            int healthLimit
            )
    {
//        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
//        this.resourceLimit = resourceLimit;
//        this.resourceCount = resourceCount;
//        this.actionPeriod = actionPeriod;
//        this.animationPeriod = animationPeriod;
//        this.health = health;
//        this.healthLimit = healthLimit;
    }

    public String getId(){
        return id;
    }

    public Point getPosition(){
        return position;
    }

    public void setPosition(Point p){
        this.position = p;
    }

    public List<PImage> getImages(){
        return images;
    }
    public void setImageIndex(int imgIdx){
        this.imageIndex = imgIdx;
    }

    public int getImageIndex(){
        return imageIndex;
    }

    public PImage getCurrentImage() {
            return this.images.get(imageIndex);
    }

//    public int getAnimationPeriod() {
//        switch (this.kind) {
//            case DUDE_FULL:
//            case DUDE_NOT_FULL:
//            case OBSTACLE:
//            case FAIRY:
//            case SAPLING:
//            case TREE:
//                return this.animationPeriod;
//            default:
//                throw new UnsupportedOperationException(
//                        String.format("getAnimationPeriod not supported for %s",
//                                this.kind));
//        }
//    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

//    public void executeSaplingActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        this.health++;
//        if (!transformPlant(world, scheduler, imageStore))
//        {
//            scheduler.scheduleEvent( this,
//                    createActivityAction(world, imageStore),
//                    this.actionPeriod);
//        }
//    }
//
//    public void executeTreeActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//
//        if (!transformPlant(world, scheduler, imageStore)) {
//
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    this.actionPeriod);
//        }
//    }
//
//    public void executeFairyActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> fairyTarget =
//                world.findNearest(this.position, new ArrayList<>(Arrays.asList(EntityKind.STUMP)));
//
//        if (fairyTarget.isPresent()) {
//            Point tgtPos = fairyTarget.get().position;
//
//            if (moveToFairy(world, fairyTarget.get(), scheduler)) {
//                Entity sapling = createSapling("sapling_" + this.id, tgtPos,
//                        imageStore.getImageList(world.SAPLING_KEY));
//
//                world.addEntity(sapling);
//                scheduleActions(scheduler, world, imageStore);
//            }
//        }
//
//        scheduler.scheduleEvent(this,
//                createActivityAction(world, imageStore),
//                this.actionPeriod);
//    }
//
//    public void executeDudeNotFullActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> target =
//                world.findNearest(this.position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));
//
//        if (!target.isPresent() || !moveToNotFull(world,
//                target.get(),
//                scheduler)
//                || !transformNotFull(world, scheduler, imageStore))
//        {
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    this.actionPeriod);
//        }
//    }
//
//    public void executeDudeFullActivity(
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> fullTarget =
//                world.findNearest(this.position, new ArrayList<>(Arrays.asList(EntityKind.HOUSE)));
//
//        if (fullTarget.isPresent() && moveToFull(world,
//                fullTarget.get(), scheduler))
//        {
//            transformFull(world, scheduler, imageStore);
//        }
//        else {
//            scheduler.scheduleEvent(this,
//                    createActivityAction(world, imageStore),
//                    this.actionPeriod);
//        }
//    }

//    public void scheduleActions(
//            EventScheduler scheduler,
//            WorldModel world,
//            ImageStore imageStore)
//    {
//        switch (this.kind) {
//            case DUDE_FULL:
//                scheduler.scheduleEvent(this,
//                        createActivityAction(world, imageStore),
//                        this.actionPeriod);
//                scheduler.scheduleEvent(this,
//                        createAnimationAction(0),
//                        getAnimationPeriod());
//                break;
//
//            case DUDE_NOT_FULL:
//                scheduler.scheduleEvent(this,
//                        createActivityAction(world, imageStore),
//                        this.actionPeriod);
//                scheduler.scheduleEvent(this,
//                        createAnimationAction(0),
//                        getAnimationPeriod());
//                break;
//
//            case OBSTACLE:
//                scheduler.scheduleEvent(this,
//                        createAnimationAction(0),
//                        getAnimationPeriod());
//                break;
//
//            case FAIRY:
//                scheduler.scheduleEvent(this,
//                        createActivityAction(world, imageStore),
//                        this.actionPeriod);
//                scheduler.scheduleEvent(this,
//                        createAnimationAction(0),
//                        getAnimationPeriod());
//                break;
//
//            case SAPLING:
//                scheduler.scheduleEvent(this,
//                        createActivityAction(world, imageStore),
//                        this.actionPeriod);
//                scheduler.scheduleEvent(this,
//                        createAnimationAction(0),
//                        getAnimationPeriod());
//                break;
//
//            case TREE:
//                scheduler.scheduleEvent(this,
//                        createActivityAction(world, imageStore),
//                        this.actionPeriod);
//                scheduler.scheduleEvent(this,
//                        createAnimationAction(0),
//                        getAnimationPeriod());
//                break;
//
//            default:
//        }
//    }

//    public boolean transformNotFull(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        if (this.resourceCount >= this.resourceLimit) {
//            Entity miner = createDudeFull(this.id,
//                    this.position, this.actionPeriod,
//                    this.animationPeriod,
//                    this.resourceLimit,
//                    this.images);
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(miner);
//            scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

//    public void transformFull(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        Entity miner = createDudeNotFull(this.id,
//                this.position, this.actionPeriod,
//                this.animationPeriod,
//                this.resourceLimit,
//                this.images);
//
//        world.removeEntity(this);
//        scheduler.unscheduleAllEvents(this);
//
//        world.addEntity(miner);
//        scheduleActions(scheduler, world, imageStore);
//    }


//    public boolean transformPlant(
//                                          WorldModel world,
//                                          EventScheduler scheduler,
//                                          ImageStore imageStore)
//    {
//        if (this.kind == EntityKind.TREE)
//        {
//            return transformTree(world, scheduler, imageStore);
//        }
//        else if (this.kind == EntityKind.SAPLING)
//        {
//            return transformSapling(world, scheduler, imageStore);
//        }
//        else
//        {
//            throw new UnsupportedOperationException(
//                    String.format("transformPlant not supported for %s", this));
//        }
//    }

//    public boolean transformTree(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        if (this.health <= 0) {
//            Entity stump = createStump(this.id,
//                    this.position,
//                    imageStore.getImageList(STUMP_KEY));
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(stump);
//
//            return true;
//        }
//
//        return false;
//    }

//    public boolean transformSapling(
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        if (this.health <= 0) {
//            Entity stump = createStump(this.id,
//                    this.position,
//                    imageStore.getImageList(STUMP_KEY));
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(stump);
//
//            return true;
//        }
//        else if (this.health >= this.healthLimit)
//        {
//            Entity tree = createTree("tree_" + this.id,
//                    this.position,
//                    Functions.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
//                    Functions.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
//                    Functions.getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
//                    imageStore.getImageList(world.TREE_KEY));
//
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(tree);
//            scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

//    public boolean moveToFairy(
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (this.position.adjacent(target.position)) {
//            world.removeEntity(target);
//            scheduler.unscheduleAllEvents(target);
//            return true;
//        }
//        else {
//            Point nextPos = nextPositionFairy(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean moveToNotFull(
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (this.position.adjacent(target.position)) {
//            this.resourceCount += 1;
//            target.health--;
//            return true;
//        }
//        else {
//            Point nextPos = nextPositionDude(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean moveToFull(
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (this.position.adjacent(target.position)) {
//            return true;
//        }
//        else {
//            Point nextPos = nextPositionDude(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                Optional<Entity> occupant = world.getOccupant(nextPos);
//                if (occupant.isPresent()) {
//                    scheduler.unscheduleAllEvents(occupant.get());
//                }
//
//                world.moveEntity(this, nextPos);
//            }
//            return false;
//        }
//    }

//    public Point nextPositionFairy(WorldModel world, Point destPos)
//    {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }

//    public Point nextPositionDude(WorldModel world, Point destPos)
//    {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) &&  world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }


    public Action createAnimationAction(int repeatCount) {
        return new AnimationAction(this,
                repeatCount);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new ActivityAction(this, world, imageStore);
    }
//
//    public static Entity createHouse(
//            String id, Point position, List<PImage> images)
//    {
//        return new Entity(EntityKind.HOUSE, id, position, images, 0, 0, 0,
//                0, 0, 0);
//    }
//
//    public static Entity createObstacle(
//            String id, Point position, int animationPeriod, List<PImage> images)
//    {
//        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0,
//                animationPeriod, 0, 0);
//    }
//
//    public static Entity createTree(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int health,
//            List<PImage> images)
//    {
//        return new Entity(EntityKind.TREE, id, position, images, 0, 0,
//                actionPeriod, animationPeriod, health, 0);
//    }
//
//    public Entity createStump(
//            String id,
//            Point position,
//            List<PImage> images)
//    {
//        return new Entity(EntityKind.STUMP, id, position, images, 0, 0,
//                0, 0, 0, 0);
//    }
//
//    // health starts at 0 and builds up until ready to convert to Tree
//    public Entity createSapling(
//            String id,
//            Point position,
//            List<PImage> images)
//    {
//        return new Entity(EntityKind.SAPLING, id, position, images, 0, 0,
//                WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, 0, WorldModel.SAPLING_HEALTH_LIMIT);
//    }
//
//    public static Entity createFairy(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            List<PImage> images)
//    {
//        return new Entity(EntityKind.FAIRY, id, position, images, 0, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }
//
//    // need resource count, though it always starts at 0
//    public static Entity createDudeNotFull(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int resourceLimit,
//            List<PImage> images)
//    {
//        return new Entity(EntityKind.DUDE_NOT_FULL, id, position, images, resourceLimit, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }
//
//    // don't technically need resource count ... full
//    public Entity createDudeFull(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int resourceLimit,
//            List<PImage> images) {
//        return new Entity(EntityKind.DUDE_FULL, id, position, images, resourceLimit, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }


//    public void draw(){
//        for (Entity entity : this.world.entities) {
//            Point pos = entity.position;
//
//            if (this.viewport.contains(pos)) {
//                Point viewPoint = this.viewport.worldToViewport(pos.x, pos.y);
//                this.screen.image(entity.getCurrentImage(),
//                        viewPoint.x * this.tileWidth,
//                        viewPoint.y * this.tileHeight);
//            }
//        }
//    }
}
