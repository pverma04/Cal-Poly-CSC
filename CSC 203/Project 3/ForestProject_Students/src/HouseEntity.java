import processing.core.PImage;

import java.util.List;

public class HouseEntity extends Entity{

    public static final String HOUSE_KEY = "house";
    public static final int HOUSE_NUM_PROPERTIES = 4;
    public static final int HOUSE_ID = 1;
    public static final int HOUSE_COL = 2;
    public static final int HOUSE_ROW = 3;

    public HouseEntity(String id, Point position, List<PImage> images) {
        super(id, position, images, 0, 0);
    }


}
