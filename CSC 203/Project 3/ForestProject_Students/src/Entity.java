import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity { // NO LONGER FINAL
    //private EntityKind kind; //NOT NEEDED ANYMORE
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    protected int health;
    private int healthLimit;
//    private final String STUMP_KEY = "stump";
//    public static final String TREE_KEY = "tree";
//    public static final int TREE_NUM_PROPERTIES = 7;
//    public static final int TREE_ID = 1;
//    public static final int TREE_COL = 2;
//    public static final int TREE_ROW = 3;
//    public static final int TREE_ANIMATION_PERIOD = 4;
//    public static final int TREE_ACTION_PERIOD = 5;
//    public static final int TREE_HEALTH = 6;
//
//    public static final int TREE_ANIMATION_MAX = 600;
//    public static final int TREE_ANIMATION_MIN = 50;
//    public static final int TREE_ACTION_MAX = 1400;
//    public static final int TREE_ACTION_MIN = 1000;
//    public static final int TREE_HEALTH_MAX = 3;
//    public static final int TREE_HEALTH_MIN = 1;

//    public static final String SAPLING_KEY = "sapling";
//    public static final int SAPLING_HEALTH_LIMIT = 5;
//    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
//    public static final int SAPLING_NUM_PROPERTIES = 4;
//    public static final int SAPLING_ID = 1;
//    public static final int SAPLING_COL = 2;
//    public static final int SAPLING_ROW = 3;
//    public static final int SAPLING_HEALTH = 4;

//    public static final String OBSTACLE_KEY = "obstacle";
//    public static final int OBSTACLE_NUM_PROPERTIES = 5;
//    public static final int OBSTACLE_ID = 1;
//    public static final int OBSTACLE_COL = 2;
//    public static final int OBSTACLE_ROW = 3;
//    public static final int OBSTACLE_ANIMATION_PERIOD = 4;

//    public static final String DUDE_KEY = "dude";
//    public static final int DUDE_NUM_PROPERTIES = 7;
//    public static final int DUDE_ID = 1;
//    public static final int DUDE_COL = 2;
//    public static final int DUDE_ROW = 3;
//    public static final int DUDE_LIMIT = 4;
//    public static final int DUDE_ACTION_PERIOD = 5;
//    public static final int DUDE_ANIMATION_PERIOD = 6;

//    public static final String HOUSE_KEY = "house";
//    public static final int HOUSE_NUM_PROPERTIES = 4;
//    public static final int HOUSE_ID = 1;
//    public static final int HOUSE_COL = 2;
//    public static final int HOUSE_ROW = 3;

//    public static final String FAIRY_KEY = "fairy";
//    public static final int FAIRY_NUM_PROPERTIES = 6;
//    public static final int FAIRY_ID = 1;
//    public static final int FAIRY_COL = 2;
//    public static final int FAIRY_ROW = 3;
//    public static final int FAIRY_ANIMATION_PERIOD = 4;
//    public static final int FAIRY_ACTION_PERIOD = 5;


    public Entity(
            //EntityKind kind,
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod,
            int health,
            int healthLimit)
    {
        //this.kind = kind;
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

    public String getId() {
        return this.id;
    }
    public int getResourceCount()
    {
        return this.resourceCount;
    }
    public int getResourceLimit()
    {
        return this.resourceLimit;
    }
    public int getActionPeriod()
    {
        return this.actionPeriod;
    }
    public Point getPosition() {
        return this.position;
    }
    public void setPosition(Point p) {
        this.position = p;
    }
    public int getHealth() {
        return this.health;
    }
    public int getHealthLimit() {
        return this.healthLimit;
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
    public Class getEntityKind() { return this.getClass(); }



    public int getAnimationPeriod() {
        if (this instanceof DudeFullEntity){

        }
        else if (this instanceof DudeNotFullEntity) {

        }
        else if (this instanceof ObstacleEntity) {

        }
        else if(this instanceof FairyEntity) {

        }
        else if (this instanceof TreeEntity) {
            return this.animationPeriod;
        }
        else {
            throw new UnsupportedOperationException(
                    String.format("getAnimationPeriod not supported for %s",
                            this.getClass()));
        }
//        switch (this.getClass()) {
//            case DudeFullEntity.class:
//            case DudeNotFullEntity.class:
//            case ObstacleEntity.class:
//            case FairyEntity.class:
//            case SaplingEntity.class:
//            case TreeEntity.class:
//                return this.animationPeriod;
//            default:
//                throw new UnsupportedOperationException(
//                        String.format("getAnimationPeriod not supported for %s",
//                                this.getClass()));
//        }
//        switch (this.getClass()) {
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
//                                this.getEntityKind()));
//        }
        return 0;
    }

    public  void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    public Point nextPositionDude(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof StumpEntity) ) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos) && !(world.getOccupancyCell(newPos) instanceof StumpEntity)) {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }
    public Point nextPositionFairy(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }



    // don't technically need resource count ... full




    public  boolean moveToFull(WorldModel world,Entity target,EventScheduler scheduler) {
        if (this.adjacent(this.getPosition(), target.position)) {
            return true;
        }
        else {
            Point nextPos = this.nextPositionDude(world, target.position);

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant( nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public  boolean moveToFairy(WorldModel world,Entity target,EventScheduler scheduler) {
        if (this.adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents( target);
            return true;
        }
        else {
            Point nextPos = this.nextPositionFairy(world, target.position);

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant( nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public boolean moveToNotFull(WorldModel world,Entity target,EventScheduler scheduler)  {
        if (this.adjacent(this.getPosition(), target.getPosition())) {
            this.resourceCount += 1;
            target.health--;
            return true;
        }
        else {
            Point nextPos = this.nextPositionDude(world, target.position);

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






//    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore)  {
//        if (this instanceof TreeEntity){
//            return transformTree(world, scheduler, imageStore);
//        }
//        else if (this instanceof SaplingEntity){
//            return this.transformSapling(world, scheduler, imageStore);
//        }
//        else{
//            throw new UnsupportedOperationException(
//            String.format("transformPlant not supported for %s", this));
//        }
//    }

//    public  boolean transformSapling(WorldModel world,EventScheduler scheduler,ImageStore imageStore){
//        if (this.health <= 0) {
//            Entity stump = new StumpEntity(this.getId(),this.getPosition(),imageStore.getImageList( STUMP_KEY),
//                    this.resourceLimit, this.resourceCount, this.actionPeriod, this.animationPeriod,
//                    this.health, this.healthLimit);
//                    world.removeEntity(this);
//                    scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(stump);
//
//            return true;
//        }
//        else if (this.getHealth() >= this.getHealthLimit()) {
//            Entity tree = new TreeEntity("tree_" + this.getId(), this.getPosition(),
//                    imageStore.getImageList(TREE_KEY),
//                    this.resourceLimit, this.resourceCount,
//                    this.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
//                    this.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
//                    this.getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
//                    this.healthLimit
//                    );
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(tree);
//            tree.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//        return false;
//    }
//    public  boolean transformTree(WorldModel world,EventScheduler scheduler,ImageStore imageStore) {
//        if (this.health <= 0) {
//            Entity stump = new StumpEntity(this.id, this.position,
//                    imageStore.getImageList(STUMP_KEY), this.resourceLimit, this.resourceCount, this.actionPeriod,
//                    this.animationPeriod, this.health, this.healthLimit);
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//            world.addEntity(stump);
//            return true;
//        }
//        return false;
//    }
//    public  boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        if (this.getResourceCount() >= this.getResourceLimit()) {
//            Entity miner = new DudeFullEntity(this.getId(),
//                    this.getPosition(), this.getImages(), this.getResourceLimit(),
//                    this.getResourceCount(), this.getActionPeriod(), this.getAnimationPeriod(),
//                    this.health, this.healthLimit);
//            world.removeEntity(this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(miner);
//            miner.scheduleActions(scheduler, world, imageStore);
//
//            return true;
//        }
//        return false;
//    }

//    public void transformFull(WorldModel world,EventScheduler scheduler,ImageStore imageStore) {
//        Entity miner = new DudeNotFullEntity(this.getId(),
//                this.getPosition(), this.getImages(), this.getResourceLimit(), this.getResourceCount(),
//                this.getActionPeriod(), this.getAnimationPeriod(), this.health, this.healthLimit
//                );
//
//        world.removeEntity(this);
//        scheduler.unscheduleAllEvents(this);
//        world.addEntity(miner);
//        miner.scheduleActions(scheduler, world, imageStore);
//    }



//    public void executeSaplingActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
//        this.health++;
//        if (!this.transform(world, scheduler, imageStore)) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),
//                    this.getActionPeriod());
//        }
//    }

//    public void executeTreeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
//        if (!this.transform(world, scheduler, imageStore)) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),this.getActionPeriod());
//        }
//    }

//    public void executeFairyActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
//        Optional<Entity> fairyTarget =
//                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(StumpEntity.class)));
//        if (fairyTarget.isPresent()) {
//            Point tgtPos = fairyTarget.get().position;
//
//            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
//                Entity sapling = new SaplingEntity("sapling_" + this.id, tgtPos,
//                imageStore.getImageList(SaplingEntity.SAPLING_KEY));
//
//                world.addEntity(sapling);
//                sapling.scheduleActions(scheduler, world, imageStore);
//            }
//        }
//        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),this.getActionPeriod());
//    }
    
//    public void executeDudeNotFullActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
//        Optional<Entity> target =
//                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Transform.class, SaplingEntity.class)));
//
//        if (!target.isPresent() || !this.moveToNotFull( world, target.get(), scheduler) || !this.transform(world, scheduler, imageStore))  {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),this.getActionPeriod());
//        }
//    }

//    public void executeDudeFullActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
//        Optional<Entity> fullTarget =
//                world.findNearest(this.position, new ArrayList<>(Arrays.asList(HouseEntity.class)));
//
//        if (fullTarget.isPresent() && this.moveToFull(world, fullTarget.get(), scheduler)) {
//            this.transform(world, scheduler, imageStore);
//        }
//        else {
//            scheduler.scheduleEvent(this,new ActivityAction(this, world, imageStore),this.getActionPeriod());
//        }
//    }


    protected int getNumFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max - min);
    }

    private boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY()
                && Math.abs(p1.getX() - p2.getX()) == 1);
    }

    public PImage getCurrentImage() {
        /*
        if (entity instanceof Background) {
            return ((Background)entity).getImages().get(
                    ((Background)entity).getImageIndex());
        }
        */

        //if (entity instanceof Entity) {
            return this.getImages().get(this.getImageIndex());
        //}
        /*
        else {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            entity));
        }
         */
    }
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
//        if (this instanceof DudeFullEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        }
//        else if (this instanceof DudeNotFullEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        }
//        else if (this instanceof ObstacleEntity) {
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        }
//        else if (this instanceof FairyEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        }
//        else if (this instanceof SaplingEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        }
//        else if (this instanceof TreeEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        }






    //    public static Entity createDudeFull(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int resourceLimit,
//            List<PImage> images) {
//        return new DudeFullEntity(id, position, images, resourceLimit, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }
//    public static Entity createFairy(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            List<PImage> images) {
//
//        return new FairyEntity(id, position, images, 0, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }
    // need resource count, though it always starts at 0
//     public static Entity createDudeNotFull(
//        String id,
//        Point position,
//        int actionPeriod,
//        int animationPeriod,
//        int resourceLimit,
//        List<PImage> images) {
//        return new DudeNotFullEntity(id, position, images, resourceLimit, 0,
//                actionPeriod, animationPeriod, 0, 0);
//    }
//
//    public static Entity createHouse(String id, Point position, List<PImage> images) {
//        return new HouseEntity(id, position, images, 0, 0, 0,
//            0, 0, 0);
//    }
//
//    public static Entity createObstacle(String id, Point position, int animationPeriod, List<PImage> images) {
//        return new ObstacleEntity(id, position, images, 0, 0, 0,
//        animationPeriod, 0, 0);
//    }
//
//    public static Entity createTree(String id, Point position, int actionPeriod, int animationPeriod, int health, List<PImage> images)  {
//        return new TreeEntity(id, position, images, 0, 0,
//                actionPeriod, animationPeriod, health, 0);
//    }
//
//    public static Entity createStump(String id,Point position,List<PImage> images) {
//        return new StumpEntity(id, position, images, 0, 0,0,
//                0, 0, 0);
//    }
//
//    // health starts at 0 and builds up until ready to convert to Tree
//    public static Entity createSapling(String id,Point position,List<PImage> images) {
//        return new SaplingEntity(id, position, images, 0, 0,
//                SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
//    }
//
//
//
//    public Action createAnimationAction(int repeatCount) {
//        return new AnimationAction(this, null, null,
//            repeatCount);
//    }
//    public Action createActivityAction(WorldModel world, ImageStore imageStore){
//        return new ActivityAction(this, world, imageStore, 0);
//    }

}
