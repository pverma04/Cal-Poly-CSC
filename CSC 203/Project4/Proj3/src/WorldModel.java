import java.util.*;
import processing.core.PImage;


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
//    private static final int BGND_NUM_PROPERTIES = 4;
//    private static final int BGND_ID = 1;
//    private static final int BGND_COL = 2;
//    private static final int BGND_ROW = 3;
//    public final String SAPLING_KEY = "sapling";
//    public static final int SAPLING_HEALTH_LIMIT = 5;
//    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
//    private static final int SAPLING_NUM_PROPERTIES = 4;
//    private static final int SAPLING_ID = 1;
//    private static final int SAPLING_COL = 2;
//    private static final int SAPLING_ROW = 3;
//    private static final int SAPLING_HEALTH = 4;
//    private static final String OBSTACLE_KEY = "obstacle";
//    private static final int OBSTACLE_NUM_PROPERTIES = 5;
//    private static final int OBSTACLE_ID = 1;
//    private static final int OBSTACLE_COL = 2;
//    private static final int OBSTACLE_ROW = 3;
//    private static final int OBSTACLE_ANIMATION_PERIOD = 4;
    private static final String DUDE_KEY = "dude";
    private static final int DUDE_NUM_PROPERTIES = 7;
    private static final int DUDE_ID = 1;
    private static final int DUDE_COL = 2;
    private static final int DUDE_ROW = 3;
    private static final int DUDE_LIMIT = 4;
    private static final int DUDE_ACTION_PERIOD = 5;
    private static final int DUDE_ANIMATION_PERIOD = 6;
//    private static final String HOUSE_KEY = "house";
//    private static final int HOUSE_NUM_PROPERTIES = 4;
//    private static final int HOUSE_ID = 1;
//    private static final int HOUSE_COL = 2;
//    private static final int HOUSE_ROW = 3;
//    private static final String FAIRY_KEY = "fairy";
//    private static final int FAIRY_NUM_PROPERTIES = 6;
//    private static final int FAIRY_ID = 1;
//    private static final int FAIRY_COL = 2;
//    private static final int FAIRY_ROW = 3;
//    private static final int FAIRY_ANIMATION_PERIOD = 4;
//    private static final int FAIRY_ACTION_PERIOD = 5;


//    public final String TREE_KEY = "tree";
//    private static final int TREE_NUM_PROPERTIES = 7;
//    private static final int TREE_ID = 1;
//    private static final int TREE_COL = 2;
//    private static final int TREE_ROW = 3;
//    private static final int TREE_ANIMATION_PERIOD = 4;
//    private static final int TREE_ACTION_PERIOD = 5;
//    private static final int TREE_HEALTH = 6;
    private static final int PROPERTY_KEY = 0;
//    private static final String BGND_KEY = "background";



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
    public int getNumRows(){
        return numRows;
    }
    public int getNumCols(){
        return numCols;
    }
    public Background[][] getBackground(){
        return background;
    }
    public Entity[][] getOccupancy(){
        return occupancy;
    }
    public Set<Entity> getEntities(){
        return entities;
    }

    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    public void tryAddEntity(Entity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < this.numRows && pos.getX() >= 0
                && pos.getX() < this.numCols;
    }

    public Optional<Entity> findNearest(Point pos, List<Class> kinds)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Class kind: kinds)
        {
            for (Entity entity : this.entities) {
                if (kind.isInstance(entity)) {
                    ofType.add(entity);
                }
            }
        }

        return pos.nearestEntity(ofType);
    }

    public void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            this.entities.add(entity);
        }
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && withinBounds(oldPos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPosition());
    }

    public void removeEntityAt(Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }


    public boolean parseBackground(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == Background.getBgndNumProperties()) {
            Point pt = new Point(Integer.parseInt(properties[Background.getBgndCol()]),
                    Integer.parseInt(properties[Background.getBgndRow()]));
            String id = properties[Background.getBgndId()];
            Background bg = new Background(id, imageStore.getImageList(id));
            bg.setBackground(this, pt);
        }

        return properties.length == Background.getBgndNumProperties();
    }

    public boolean parseSapling(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == Sapling.SAPLING_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Sapling.SAPLING_COL]),
                    Integer.parseInt(properties[Sapling.SAPLING_ROW]));
            String id = properties[Sapling.SAPLING_ID];
            int health = Integer.parseInt(properties[Sapling.SAPLING_HEALTH]);
            Entity entity = new Sapling(id, pt, imageStore.getImageList(Sapling.SAPLING_KEY), health);
            tryAddEntity(entity);
        }

        return properties.length == Sapling.SAPLING_NUM_PROPERTIES;
    }

    public boolean parseDude(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == Resourceful.DUDE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Resourceful.DUDE_COL]),
                    Integer.parseInt(properties[Resourceful.DUDE_ROW]));
            Entity entity = new DudeNotFull(properties[Resourceful.DUDE_ID],
                    pt,
                    imageStore.getImageList(Resourceful.DUDE_KEY),
                    Integer.parseInt(properties[Resourceful.DUDE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Resourceful.DUDE_ACTION_PERIOD]),
                    Integer.parseInt(properties[Resourceful.DUDE_LIMIT]),
                    0);
            tryAddEntity(entity);
        }

        return properties.length == Resourceful.DUDE_NUM_PROPERTIES;
    }

    public boolean parseFairy(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == Fairy.FAIRY_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Fairy.FAIRY_COL]),
                    Integer.parseInt(properties[Fairy.FAIRY_ROW]));
            Entity entity = new Fairy(properties[Fairy.FAIRY_ID],
                    pt,
                    imageStore.getImageList(Fairy.FAIRY_KEY),
                    Integer.parseInt(properties[Fairy.FAIRY_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Fairy.FAIRY_ACTION_PERIOD]));
            tryAddEntity(entity);
        }

        return properties.length == Fairy.FAIRY_NUM_PROPERTIES;
    }

    public boolean parseTree(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == Tree.TREE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Tree.TREE_COL]),
                    Integer.parseInt(properties[Tree.TREE_ROW]));
            Entity entity = new Tree(properties[Tree.TREE_ID],
                    pt,
                    imageStore.getImageList(Tree.TREE_KEY),
                    Integer.parseInt(properties[Tree.TREE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[Tree.TREE_ACTION_PERIOD]),
                    Integer.parseInt(properties[Tree.TREE_HEALTH]));
            tryAddEntity(entity);
        }

        return properties.length == Tree.TREE_NUM_PROPERTIES;
    }

    public boolean parseObstacle(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Obstacle.OBSTACLE_COL]),
                    Integer.parseInt(properties[Obstacle.OBSTACLE_ROW]));
            Entity entity = new Obstacle(properties[Obstacle.OBSTACLE_ID], pt,
                    imageStore.getImageList(
                            Obstacle.OBSTACLE_KEY),
                    Integer.parseInt(properties[Obstacle.OBSTACLE_ANIMATION_PERIOD]));
            tryAddEntity(entity);
        }

        return properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES;
    }

    public boolean parseHouse(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == House.HOUSE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[House.HOUSE_COL]),
                    Integer.parseInt(properties[House.HOUSE_ROW]));
            Entity entity = new House(properties[House.HOUSE_ID], pt,
                    imageStore.getImageList(
                            House.HOUSE_KEY));
            tryAddEntity(entity);
        }

        return properties.length == House.HOUSE_NUM_PROPERTIES;
    }

    public Optional<PImage> getBackgroundImage(Point pos)
    {
        if (withinBounds(pos)) {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }


    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }

    public Entity getOccupancyCell(Point pos) {
        return this.occupancy[pos.getY()][pos.getX()];
    }

    public void setOccupancyCell(Point pos, Entity entity)
    {
        this.occupancy[pos.getY()][pos.getX()] = entity;
    }

    public Background getBackgroundCell(Point pos) {
            return this.background[pos.getY()][pos.getX()];
    }

    public void setBackgroundCell(Point pos, Background background)
    {
        this.background[pos.getY()][pos.getX()] = background;
    }

    public void load(
            Scanner in, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                if (!processLine(in.nextLine(), imageStore)) {
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

    public boolean processLine(
            String line, ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[PROPERTY_KEY]) {
                case Background.BGND_KEY:
                    return parseBackground(properties, imageStore);
                case Resourceful.DUDE_KEY:
                    return parseDude(properties, imageStore);
                case Obstacle.OBSTACLE_KEY:
                    return parseObstacle(properties, imageStore);
                case Fairy.FAIRY_KEY:
                    return parseFairy(properties, imageStore);
                case House.HOUSE_KEY:
                    return parseHouse(properties, imageStore);
                case Tree.TREE_KEY:
                    return parseTree(properties, imageStore);
                case Sapling.SAPLING_KEY:
                    return parseSapling(properties, imageStore);
            }
        }

        return false;
    }

}
