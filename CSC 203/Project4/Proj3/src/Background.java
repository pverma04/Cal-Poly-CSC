import java.util.List;
import java.util.Optional;

import processing.core.PImage;

/**
 * Represents a background for the 2D world.
 */
public final class Background implements CurrentImage
{
    private String id;
    private List<PImage> images;
    private int imageIndex;
    private static final int BGND_NUM_PROPERTIES = 4;
    private static final int BGND_ID = 1;
    private static final int BGND_COL = 2;
    private static final int BGND_ROW = 3;
    public static final String BGND_KEY = "background";

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    public static String getBgndKey(){
        return BGND_KEY;
    }

    public static int getBgndNumProperties(){
        return BGND_NUM_PROPERTIES;
    }

    public static int getBgndId(){
        return BGND_ID;
    }

    public static int getBgndCol(){
        return BGND_COL;
    }

    public static int getBgndRow(){
        return BGND_ROW;
    }

    public String getId(){
        return id;
    }

    public List<PImage> getImages(){
        return images;
    }

    public int getImageIndex(){
        return imageIndex;
    }

    public PImage getCurrentImage() {
            return images.get(imageIndex);
    }

        public void setBackground(
            WorldModel world, Point pos)
    {
        if (world.withinBounds(pos)) {
            world.setBackgroundCell(pos, this);
        }
    }

//    @Override
//    public boolean parse(String[] properties, ImageStore imageStore, WorldModel world) {
//        if (properties.length == Background.BGND_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
//                    Integer.parseInt(properties[BGND_ROW]));
//            String id = properties[BGND_ID];
//            Background bg = new Background(id, imageStore.getImageList(id));
//            bg.setBackground(world, pt);
//        }
//
//        return properties.length == BGND_NUM_PROPERTIES;
//    }

//    public void draw(){
//        for (int row = 0; row < this.viewport.numRows; row++) {
//            for (int col = 0; col < this.viewport.numCols; col++) {
//                Point worldPoint = this.viewport.viewportToWorld(col, row);
//                Optional<PImage> image =
//                        this.world.getBackgroundImage(worldPoint);
//                if (image.isPresent()) {
//                    this.screen.image(image.get(), col * this.tileWidth,
//                            row * this.tileHeight);
//                }
//            }
//        }
//    }
}
