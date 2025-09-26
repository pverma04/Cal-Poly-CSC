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
//    private int resourceLimit;
//    private int resourceCount;
//    private int actionPeriod;
    private int animationPeriod;
    protected int health;
//    private int healthLimit;
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
            String id, Point position, List<PImage> images, int health
            , int animationPeriod
//            int resourceLimit,
//            int resourceCount,
//            int actionPeriod,
//
//            int healthLimit
            )
    {
        //this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
//        this.resourceLimit = resourceLimit;
//        this.resourceCount = resourceCount;
//        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
//        this.healthLimit = healthLimit;
    }

    public String getId() {
        return this.id;
    }
//    public int getResourceCount()
//    {
//        return this.resourceCount;
//    }
//    public int getResourceLimit()
//    {
//        return this.resourceLimit;
//    }
//    public int getActionPeriod()
//    {
//        return this.actionPeriod;
//    }
    public Point getPosition() {
        return this.position;
    }
    public void setPosition(Point p) {
        this.position = p;
    }
    public int getAnimationPeriod() {
        return this.animationPeriod;
    }
    public int getHealth() {
        return this.health;
    }
//    public int getHealthLimit() {
//        return this.healthLimit;
//    }
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

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

    protected int getNumFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max - min);
    }



    public PImage getCurrentImage() {
        return this.getImages().get(this.getImageIndex());
    }
//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        if (this instanceof DudeFullEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        } else if (this instanceof DudeNotFullEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        } else if (this instanceof ObstacleEntity) {
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        } else if (this instanceof FairyEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        } else if (this instanceof SaplingEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        } else if (this instanceof TreeEntity) {
//            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getActionPeriod());
//            scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
//        }
//    }

    public static Entity createDudeFull(String id, Point position, int actionPeriod, int animationPeriod, int resourceLimit,
            List<PImage> images) {
        return new DudeFullEntity(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod, 0, 0);
    }
    public static Entity createFairy(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images) {

        return new FairyEntity(id, position, images, 0, 0,
                actionPeriod, animationPeriod, 0, 0);
    }
     //need resource count, though it always starts at 0
     public static Entity createDudeNotFull(
        String id,
        Point position,
        int actionPeriod,
        int animationPeriod,
        int resourceLimit,
        List<PImage> images) {
        return new DudeNotFullEntity(id, position, images, resourceLimit, 0,
                actionPeriod, animationPeriod, 0, 0);
    }

    public static Entity createHouse(String id, Point position, List<PImage> images) {
        return new HouseEntity(id, position, images);
    }

    public static Entity createObstacle(String id, Point position, List<PImage> images) {
        return new ObstacleEntity(id, position, images, 0);
    }

    public static Entity createTree(String id, Point position, int actionPeriod, int animationPeriod, int health, List<PImage> images)  {
        return new TreeEntity(id, position, images, 0, 0,
                actionPeriod, animationPeriod, health, 0);
    }

    public static Entity createStump(String id,Point position,List<PImage> images) {
        return new StumpEntity(id, position, images);
    }

    // health starts at 0 and builds up until ready to convert to Tree
    public static Entity createSapling(String id,Point position,List<PImage> images) {
        return new SaplingEntity(id, position, images, 0, 0,
                SaplingEntity.SAPLING_ACTION_ANIMATION_PERIOD, SaplingEntity.SAPLING_ACTION_ANIMATION_PERIOD, 0, SaplingEntity.SAPLING_HEALTH_LIMIT);
    }


    public Action createAnimationAction(int repeatCount) {
        return new AnimationAction(this, null, null,
            repeatCount);
    }
    public Action createActivityAction(WorldModel world, ImageStore imageStore){
        return new ActivityAction((ActionEntity) this, world, imageStore);
    }

    //public int getHealth() { return this.health; }
}
