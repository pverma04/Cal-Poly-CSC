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
    private Set<Entity> entities;

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
        return pos.getY() >= 0 && pos.getY() < this.numRows && pos.getX() >= 0
                && pos.getX() < this.numCols;
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

    public void setBackground( Point pos, Background background) {
        if (this.withinBounds(pos)) {
            this.setBackgroundCell(pos, background);
        }
    }

    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && this.getOccupancyCell(pos) != null;
    }
    /*
    public Optional<PImage> getBackgroundImage(Point pos) {
        if (this.withinBounds(pos)) {
            return Optional.of(getCurrentImage(this.getBackgroundCell(pos)));
        }
        else {
            return Optional.empty();
        }
    }
    */

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPos();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.setPos(pos);
        }
    }
    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.getY()][pos.getX()];
    }
    public void setOccupancyCell(Point pos, Entity entity) {
        this.occupancy[pos.getY()][pos.getX()] = entity;
    }

    public void setBackgroundCell(Point pos, Background background) {
        this.background[pos.getY()][pos.getX()] = background;
    }
    public Background getBackgroundCell(Point pos) {
        return this.background[pos.getY()][pos.getX()];
    }
    public Set<Entity> getEntities() {
        return this.entities;
    }
    public void setEntities(Set<Entity> entities) {
        this.entities = entities;
    }
    public void addEntity(Entity entity) {
        if (this.withinBounds(entity.getPos())) {
            this.setOccupancyCell(entity.getPos(), entity);
            Set<Entity> entities = this.getEntities();
            entities.add(entity);
            this.setEntities(entities);
        }
    }

    public void tryAddEntity(Entity entity) {
        if (this.isOccupied(entity.getPos())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }
        this.addEntity(entity);
    }
    public Optional<Entity> getOccupant(Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }
    /*
    public boolean transformNotFull( Entity entity, EventScheduler scheduler, ImageStore imageStore) {
        if (entity.getResourceCount() >= entity.getResourceLimit()) {
            Entity miner = Functions.createDudeFull(entity.getID(),
                    entity.getPos(), entity.getActionPeriod(),
                    entity.getAnimationPeriod(),
                    entity.getResourceLimit(),
                    entity.getImages());

            this.removeEntity(entity);
            scheduler.unscheduleAllEvents(entity);

            this.addEntity(miner);
            scheduler.scheduleActions(miner, this, imageStore);

            return true;
        }

        return false;
    }
    public void transformFull(Entity entity, EventScheduler scheduler, ImageStore imageStore) {
        Entity miner = Functions.createDudeNotFull(entity.getID(),
                entity.getPos(), entity.getActionPeriod(),
                entity.getAnimationPeriod(),
                entity.getResourceLimit(),
                entity.getImages());

        this.removeEntity(entity);
        scheduler.unscheduleAllEvents(entity);

        this.addEntity(miner);
        scheduler.scheduleActions(miner, this, imageStore);
    }
    public boolean transformPlant( Entity entity, EventScheduler scheduler, ImageStore imageStore) {
        if (entity.getEntityKind() == EntityKind.TREE)
        {
            return transformTree(entity, this, scheduler, imageStore);
        }
        else if (entity.getEntityKind() == EntityKind.SAPLING)
        {
            return transformSapling(entity, this, scheduler, imageStore);
        }
        else
        {
            throw new UnsupportedOperationException(
                    String.format("transformPlant not supported for %s", entity));
        }
    }
    public static boolean transformTree(Entity entity, EventScheduler scheduler, ImageStore imageStore) {
        if (entity.getHealth() <= 0) {
            Entity stump = createStump(entity.getID(),
                    entity.getPos(),
                    getImageList(imageStore, STUMP_KEY));

            world.removeEntity(entity);
            scheduler.unscheduleAllEvents(entity);

            world.addEntity(stump);

            return true;
        }

        return false;
    }
     */
}
