import processing.core.PImage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

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


    public static final String STUMP_KEY = "stump";

    public static final int PROPERTY_KEY = 0;


    public static final String TREE_KEY = "tree";
    public static final int TREE_NUM_PROPERTIES = 7;
    public static final int TREE_ID = 1;
    public static final int TREE_COL = 2;
    public static final int TREE_ROW = 3;
    public static final int TREE_ANIMATION_PERIOD = 4;
    public static final int TREE_ACTION_PERIOD = 5;
    public static final int TREE_HEALTH = 6;
    public static final int TREE_ANIMATION_MAX = 600;
    public static final int TREE_ANIMATION_MIN = 50;
    public static final int TREE_ACTION_MAX = 1400;
    public static final int TREE_ACTION_MIN = 1000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;

    public static final String DUDE_KEY = "dude";
    public static final int DUDE_NUM_PROPERTIES = 7;
    public static final int DUDE_ID = 1;
    public static final int DUDE_COL = 2;
    public static final int DUDE_ROW = 3;
    public static final int DUDE_LIMIT = 4;
    public static final int DUDE_ACTION_PERIOD = 5;
    public static final int DUDE_ANIMATION_PERIOD = 6;

    public static final String HOUSE_KEY = "house";
    public static final int HOUSE_NUM_PROPERTIES = 4;
    public static final int HOUSE_ID = 1;
    public static final int HOUSE_COL = 2;
    public static final int HOUSE_ROW = 3;

    public static final String FAIRY_KEY = "fairy";
    public static final int FAIRY_NUM_PROPERTIES = 6;
    public static final int FAIRY_ID = 1;
    public static final int FAIRY_COL = 2;
    public static final int FAIRY_ROW = 3;
    public static final int FAIRY_ANIMATION_PERIOD = 4;
    public static final int FAIRY_ACTION_PERIOD = 5;

    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_NUM_PROPERTIES = 4;
    public static final int SAPLING_ID = 1;
    public static final int SAPLING_COL = 2;
    public static final int SAPLING_ROW = 3;
    public static final int SAPLING_HEALTH = 4;

    public static final String BGND_KEY = "background";
    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_NUM_PROPERTIES = 5;
    public static final int OBSTACLE_ID = 1;
    public static final int OBSTACLE_COL = 2;
    public static final int OBSTACLE_ROW = 3;
    public static final int OBSTACLE_ANIMATION_PERIOD = 4;


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

    public void setBackgroundCell(Point pos, Background background) { this.background[pos.getY()][pos.getX()] = background; }
    public Background getBackgroundCell(Point pos) {
        return this.background[pos.getY()][pos.getX()];
    }
    public Set<Entity> getEntities() { return this.entities; }
    public void setEntities(Set<Entity> entities) { this.entities = entities; }
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

    public boolean transformNotFull(Entity entity, EventScheduler scheduler, ImageStore imageStore) {
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
    public boolean transformPlant(Entity entity, EventScheduler scheduler, ImageStore imageStore) {
        if (entity.getEntityKind() == EntityKind.TREE) {
            return transformTree(entity, scheduler, imageStore);
        }
        else if (entity.getEntityKind() == EntityKind.SAPLING) {
            return transformSapling(entity, scheduler, imageStore);
        }
        else  {
            throw new UnsupportedOperationException(
                    String.format("transformPlant not supported for %s", entity));
        }
    }
    public boolean transformSapling(Entity entity, EventScheduler scheduler, ImageStore imageStore) {
        if (entity.getHealth() <= 0) {
            Entity stump = Functions.createStump(entity.getID(),
                    entity.getPos(),
                    imageStore.getImageList(STUMP_KEY));

            this.removeEntity(entity);
            scheduler.unscheduleAllEvents(entity);

            this.addEntity(stump);

            return true;
        }
        else if (entity.getHealth() >= entity.getHealthLimit()) {
            Entity tree = Functions.createTree("tree_" + entity.getID(),
                    entity.getPos(),
                    Functions.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
                    Functions.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
                    Functions.getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
                    imageStore.getImageList(TREE_KEY));

            this.removeEntity(entity);
            scheduler.unscheduleAllEvents(entity);

            this.addEntity(tree);
            scheduler.scheduleActions(tree, this, imageStore);

            return true;
        }

        return false;
    }
    public boolean transformTree(Entity entity, EventScheduler scheduler, ImageStore imageStore) {
        if (entity.getHealth() <= 0) {
            Entity stump = Functions.createStump(entity.getID(),
                    entity.getPos(),
                    imageStore.getImageList(STUMP_KEY));

            this.removeEntity(entity);
            scheduler.unscheduleAllEvents(entity);

            this.addEntity(stump);

            return true;
        }

        return false;
    }
    public boolean moveToFairy( Entity fairy, Entity target, EventScheduler scheduler) {
        if (Functions.adjacent(fairy.getPos(), target.getPos())) {
            this.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = fairy.nextPositionFairy(this, target.getPos());

            if (!fairy.getPos().equals(nextPos)) {
                Optional<Entity> occupant = this.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                this.moveEntity(fairy, nextPos);
            }
            return false;
        }
    }

    public boolean processLine(String line, ImageStore imageStore) {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case BGND_KEY:
                    return this.parseBackground(properties, imageStore);
                case DUDE_KEY:
                    return this.parseDude(properties, imageStore);
                case OBSTACLE_KEY:
                    return this.parseObstacle(properties, imageStore);
                case FAIRY_KEY:
                    return this.parseFairy(properties, imageStore);
                case HOUSE_KEY:
                    return this.parseHouse(properties, imageStore);
                case TREE_KEY:
                    return this.parseTree(properties, imageStore);
                case SAPLING_KEY:
                    return this.parseSapling(properties, imageStore);
            }
        }
        return false;
    }
    public boolean parseBackground(String[] properties, ImageStore imageStore) {
        if (properties.length == BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            this.setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }
        return properties.length == BGND_NUM_PROPERTIES;
    }
    public boolean parseSapling(String[] properties, ImageStore imageStore) {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SAPLING_COL]),
                    Integer.parseInt(properties[SAPLING_ROW]));
            String id = properties[SAPLING_ID];
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            Entity entity = new Entity(EntityKind.SAPLING, id, pt, imageStore.getImageList(SAPLING_KEY), 0, 0,
                    SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, health, SAPLING_HEALTH_LIMIT);
            this.tryAddEntity(entity);
        }

        return properties.length == SAPLING_NUM_PROPERTIES;
    }
    public boolean parseDude(String[] properties, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[DUDE_COL]),
                    Integer.parseInt(properties[DUDE_ROW]));
            Entity entity = Functions.createDudeNotFull(properties[DUDE_ID],
                    pt,
                    Integer.parseInt(properties[DUDE_ACTION_PERIOD]),
                    Integer.parseInt(properties[DUDE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[DUDE_LIMIT]),
                    imageStore.getImageList(DUDE_KEY));
            this.tryAddEntity(entity);
        }

        return properties.length == DUDE_NUM_PROPERTIES;
    }
    public boolean parseFairy(String[] properties, ImageStore imageStore) {
        if (properties.length == FAIRY_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[FAIRY_COL]),
                    Integer.parseInt(properties[FAIRY_ROW]));
            Entity entity = Functions.createFairy(properties[FAIRY_ID],
                    pt,
                    Integer.parseInt(properties[FAIRY_ACTION_PERIOD]),
                    Integer.parseInt(properties[FAIRY_ANIMATION_PERIOD]),
                    imageStore.getImageList(FAIRY_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == FAIRY_NUM_PROPERTIES;
    }
    public boolean parseTree(String[] properties, ImageStore imageStore) {
        if (properties.length == TREE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[TREE_COL]),
                    Integer.parseInt(properties[TREE_ROW]));
            Entity entity = Functions.createTree(properties[TREE_ID],
                    pt,
                    Integer.parseInt(properties[TREE_ACTION_PERIOD]),
                    Integer.parseInt(properties[TREE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[TREE_HEALTH]),
                    imageStore.getImageList(TREE_KEY));
            this.tryAddEntity(entity);
        }

        return properties.length == TREE_NUM_PROPERTIES;
    }
    public boolean parseObstacle(String[] properties, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            Entity entity = Functions.createObstacle(properties[OBSTACLE_ID], pt,
                    Integer.parseInt(properties[OBSTACLE_ANIMATION_PERIOD]),
                    imageStore.getImageList(OBSTACLE_KEY));
            this.tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }
    public boolean parseHouse(String[] properties, ImageStore imageStore)
    {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[HOUSE_COL]),
                    Integer.parseInt(properties[HOUSE_ROW]));
            Entity entity = Functions.createHouse(properties[HOUSE_ID], pt,
                    imageStore.getImageList(HOUSE_KEY));
            this.tryAddEntity(entity);
        }

        return properties.length == HOUSE_NUM_PROPERTIES;
    }
    public void load(Scanner in, ImageStore imageStore) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!this.processLine(in.nextLine(), imageStore)) {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e) {
                System.err.println(
                        String.format("invalid entry on line %d", lineNumber));
            }
            catch (IllegalArgumentException e) {
                System.err.println(
                        String.format("issue on line %d: %s", lineNumber,
                                e.getMessage()));
            }
            lineNumber++;
        }
    }
    public Optional<PImage> getBackgroundImage(Point pos) {
        if (this.withinBounds(pos)) {
            return Optional.of(Functions.getCurrentImage(this.getBackgroundCell(pos)));
        }
        else {
            return Optional.empty();
        }
    }
    public Optional<Entity> findNearest(Point pos, List<EntityKind> kinds) {
        List<Entity> ofType = new LinkedList<>();
        for (EntityKind kind: kinds) {
            for (Entity entity : this.getEntities()) {
                if (entity.getEntityKind() == kind) {
                    ofType.add(entity);
                }
            }
        }
        return Functions.nearestEntity(ofType, pos);
    }
}
