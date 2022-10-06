import java.util.Optional;

import processing.core.PApplet;
import processing.core.PImage;

public final class WorldView
{
    private PApplet screen;
    private WorldModel world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

    public WorldView(
            int numRows,
            int numCols,
            PApplet screen,
            WorldModel world,
            int tileWidth,
            int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    public PApplet getScreen() {
        return this.screen;
    }
    public WorldModel getWorld() {
        return this.world;
    }
    public int getTileWidth() {
        return this.tileWidth;
    }
    public int getTileHeight() { return this.tileHeight; }
    public Viewport getViewport() { return this.viewport; }


    public void drawEntities() {
        for (Entity entity : world.getEntities()) {
            Point pos = entity.position;

            if (viewport.contains(pos)) {
                Point viewPoint = viewport.worldToViewport(pos.getX(), pos.getY());
                screen.image(Entity.getCurrentImage(entity),
                        viewPoint.getX() * tileWidth,
                        viewPoint.getY() * tileHeight);
            }
        }
    }

    public void drawViewport() {
        drawBackground();
        drawEntities();
    }
    public  void drawBackground() {
        for (int row = 0; row < viewport.getNumRows(); row++) {
            for (int col = 0; col < viewport.getNumCols(); col++) {
                Point worldPoint = viewport.viewportToWorld(col, row);
                Optional<PImage> image =this.world.getBackgroundImage(worldPoint);
                if (image.isPresent()) {
                    screen.image(image.get(), col * tileWidth,
                            row * tileHeight);
                }
            }
        }
    }
    public  void shiftView( int colDelta, int rowDelta) {
        int newCol = clamp(this.viewport.getCol() + colDelta, 0,
                this.world.getNumCols() - this.viewport.getNumCols());
        int newRow = clamp(this.viewport.getRow() + rowDelta, 0,
                this.world.getNumRows() - this.viewport.getNumRows());

                this.viewport.shift( newCol, newRow);
    }

    private  int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

}
