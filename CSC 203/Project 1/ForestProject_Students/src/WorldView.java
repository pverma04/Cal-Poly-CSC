import processing.core.PApplet;

public final class WorldView
{
    private PApplet screen;
    private WorldModel world;
    public int tileWidth;
    public int tileHeight;
    public Viewport viewport;

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
}
