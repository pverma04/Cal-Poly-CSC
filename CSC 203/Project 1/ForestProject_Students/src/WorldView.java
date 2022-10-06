import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

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
    public void drawBackground() {
        for (int row = 0; row < this.getViewport().getNumRows(); row++) {
            for (int col = 0; col < this.getViewport().getNumCols(); col++) {
                Point worldPoint = this.getViewport().viewportToWorld(col, row);
                Optional<PImage> image =
                        this.getWorld().getBackgroundImage(worldPoint);
                if (image.isPresent()) {
                    this.getScreen().image(image.get(), col * this.getTileWidth(),
                            row * this.getTileHeight());
                }
            }
        }
    }
    public void shiftView(int colDelta, int rowDelta) {
        int newCol = Functions.clamp(this.getViewport().getCol() + colDelta, 0,
                this.getWorld().getNumCols() - this.getViewport().getNumCols());
        int newRow = Functions.clamp(this.getViewport().getRow() + rowDelta, 0,
                this.getWorld().getNumRows() - this.getViewport().getNumRows());

        this.getViewport().shift(newCol, newRow);
    }
    public void drawEntities() {
        for (Entity entity : this.getWorld().getEntities()) {
            Point pos = entity.getPos();

            if (this.getViewport().contains(pos)) {
                Point viewPoint = this.getViewport().worldToViewport(pos.getX(), pos.getY());
                this.getScreen().image(Functions.getCurrentImage(entity),
                        viewPoint.getX() * this.getTileWidth(),
                        viewPoint.getY() * this.getTileHeight());
            }
        }
    }
    public void drawViewport() {
        this.drawBackground();
        this.drawEntities();
    }



}
