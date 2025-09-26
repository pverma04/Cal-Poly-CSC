import processing.core.PImage;

import java.util.List;

public class Obstacle extends Animated{
    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 5;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;
    public static final int OBSTACLE_ANIMATION_PERIOD = 4;
    public Obstacle(String id, Point position, List<PImage> images, int animationPeriod){
        super(id, position, images, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                createAnimationAction(0),
                getAnimationPeriod());
    }


//    @Override
//    public boolean parse(String[] properties, ImageStore imageStore, WorldModel world) {
//        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]),
//                    Integer.parseInt(properties[OBSTACLE_ROW]));
//            Entity entity = new Obstacle(properties[OBSTACLE_ID], pt,
//                    imageStore.getImageList(
//                            OBSTACLE_KEY),
//                    Integer.parseInt(properties[OBSTACLE_ANIMATION_PERIOD]));
//            world.tryAddEntity(entity);
//        }
//
//        return properties.length == OBSTACLE_NUM_PROPERTIES;
//    }
}
