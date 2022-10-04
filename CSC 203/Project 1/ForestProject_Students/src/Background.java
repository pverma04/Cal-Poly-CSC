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
    //set bg
    private void setBackground(WorldModel world, Point pos) {
        if (world.withinBounds(pos)) {
            this.setBackgroundCell(world, pos);
        }
    }
    private void setBackgroundCell(WorldModel world, Point pos) {
        world.background[pos.y][pos.x] = this;
    }
}
