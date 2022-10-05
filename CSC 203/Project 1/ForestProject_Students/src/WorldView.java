import processing.core.PApplet;

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
    public int getTileHeight() {
        return this.tileHeight;
    }
    public Viewport getViewport() {
        return this.viewport;
    }
}
