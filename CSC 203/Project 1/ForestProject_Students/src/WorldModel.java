import processing.core.PImage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel
{
    private int numRows;
    private int numCols;
    private Background background[][];
    private Entity occupancy[][];
    public Set<Entity> entities;

    public int getNumRows() {
        return this.numRows;
    }
    public int getNumCols() {
        return this.numCols;
    }

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }
    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0
                && pos.x < this.numCols;
    }
    private void removeEntityAt(Point pos) {
        if (this.withinBounds(pos) && this.getOccupancyCell(pos) != null) {
            Entity entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPos(new Point(-1, -1));
            this.entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }
    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPos());
    }
    public Optional<PImage> getBackgroundImage(Point pos) {
        if (this.withinBounds(pos)) {
            return Optional.of(getCurrentImage(this.getBackgroundCell(pos)));
        }
        else {
            return Optional.empty();
        }
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPos();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.setPos(pos);
        }
    }
    private Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.y][pos.x];
    }
    private void setOccupancyCell(Point pos, Entity entity) {
        this.occupancy[pos.y][pos.x] = entity;
    }

    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.y][pos.x] = background;
    }
    private Background getBackgroundCell(Point pos) {
        return this.background[pos.y][pos.x];
    }

}
