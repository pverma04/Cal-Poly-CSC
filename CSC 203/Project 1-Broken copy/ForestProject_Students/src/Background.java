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
}