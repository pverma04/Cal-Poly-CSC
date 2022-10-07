import java.util.List;

import processing.core.PImage;

/**
 * Represents a background for the 2D world.
 */
public final class Background
{
    private String id;
    private List<PImage> images;
    private int imageIndex;

    
    public static final String BGND_KEY = "background";
    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    public String getID() {
        return this.id;
    }
    public List<PImage> getImages() {
        return this.images;
    }
    public int getImageIndex() {
        return this.imageIndex;
    }

    public PImage getCurrentImage() {
        //if (entity instanceof Background) {
            return this.getImages().get(this.getImageIndex());
        //}
        /*
        if (entity instanceof Entity) {
            return this.getImages().get(this.getImageIndex());
        }
         */
        /*
        else {
            throw new UnsupportedOperationException(
                    String.format("getCurrentImage not supported for %s",
                            entity));
        }
         */
    }

}
