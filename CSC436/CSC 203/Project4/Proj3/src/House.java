import processing.core.PImage;



import java.util.List;

public class House extends Entity{
    public static final String HOUSE_KEY = "house";
    public static final int HOUSE_NUM_PROPERTIES = 4;
    public static final int HOUSE_ID = 1;
    public static final int HOUSE_COL = 2;
    public static final int HOUSE_ROW = 3;

    public House(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }

//    @Override
//    public boolean parse(String[] properties, ImageStore imageStore, WorldModel world) {
//        if (properties.length == HOUSE_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[HOUSE_COL]),
//                    Integer.parseInt(properties[HOUSE_ROW]));
//            Entity entity = new House(properties[HOUSE_ID], pt,
//                    imageStore.getImageList(
//                            HOUSE_KEY));
//            world.tryAddEntity(entity);
//        }
//
//        return properties.length == HOUSE_NUM_PROPERTIES;
//    }
}