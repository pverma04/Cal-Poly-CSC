import java.util.List;

import processing.core.PImage;

/**
 * Represents a background for the 2D world.
 */
public final class Background
{
    public String id;
    public List<PImage> images;
    public int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }
    private void setBackground(WorldModel world, Point pos) {
        if (world.withinBounds(pos)) {
            world.setBackgroundCell(pos, this);
        }
    }

}
