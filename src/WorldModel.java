import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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


    

    public int getNumRows() {
        return this.numRows;
    }
    public int getNumCols() {
        return this.numCols;
    }

    public Background[][] getBackground() {
        return this.background;
    }

    
    public Entity[][] getoccupancy() {
        return this.occupancy;
    }
    
    public Set<Entity> getEntities() {
        return this.entities;
    }


    public  Entity getOccupancyCell(Point pos) {
        return this.getoccupancy()[pos.getY()][pos.getX()];
    }

    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < this.numRows && pos.getX() >= 0
                && pos.getX() < this.numCols;
    }
    public  boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    public  void tryAddEntity(Entity entity) {
        if (this.isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    
    /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */
    public  void addEntity(Entity entity) {
        if (withinBounds(entity.position)) {
            setOccupancyCell(entity.position, entity);
            entities.add(entity);
        }
    }
    public  void setOccupancyCell(Point pos, Entity entity)
    {
        occupancy[pos.getY()][pos.getX()] = entity;
    }

    public  Optional<PImage> getBackgroundImage(Point pos)
    {
        if (withinBounds(pos)) {
            return Optional.of(Entity.getCurrentImage(getBackgroundCell(pos)));
        }
        else {
            return Optional.empty();
        }
    }

    public  Background getBackgroundCell(Point pos) {
        return background[pos.getY()][pos.getX()];
    }


    public  void setBackground(Point pos, Background background)
    {
        if (this.withinBounds(pos)) {
            setBackgroundCell(pos, background);
        }
    }

    private  void setBackgroundCell(Point pos, Background background)
{
    this.background[pos.getY()][pos.getX()] = background;
}


    public  void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.position = pos;
        }
    }

    public  void removeEntity(Entity entity) {
        removeEntityAt(entity.position);
    }

    private  void removeEntityAt( Point pos) {
    if (this.withinBounds(pos) && this.getOccupancyCell(pos) != null) {
        Entity entity = this.getOccupancyCell(pos);

        /* This moves the entity just outside of the grid for
            * debugging purposes. */
        entity.position = new Point(-1, -1);
        this.entities.remove(entity);
        this.setOccupancyCell(pos, null);
    }
}

    public  Optional<Entity> findNearest(Point pos, List<EntityKind> kinds)
{
    List<Entity> ofType = new LinkedList<>();
    for (EntityKind kind: kinds)
    {
        for (Entity entity : this.entities) {
            if (entity.getEntityKind() == kind) {
                ofType.add(entity);
            }
        }
    }
return nearestEntity(ofType, pos);
}

    public  Optional<Entity> getOccupant( Point pos) {
    if (this.isOccupied(pos)) {
        return Optional.of(this.getOccupancyCell(pos));
    }
    else {
        return Optional.empty();
    }
}

    private  int distanceSquared(Point p1, Point p2) {
    int deltaX = p1.getX() - p2.getX();
    int deltaY = p1.getY() - p2.getY();

    return deltaX * deltaX + deltaY * deltaY;
}

    public  Optional<Entity> nearestEntity(List<Entity> entities, Point pos)
{
    if (entities.isEmpty()) {
        return Optional.empty();
    }
    else {
        Entity nearest = entities.get(0);
        int nearestDistance = distanceSquared(nearest.position, pos);

        for (Entity other : entities) {
            int otherDistance = distanceSquared(other.position, pos);

        if (otherDistance < nearestDistance) {
            nearest = other;
            nearestDistance = otherDistance;
        }
    }

    return Optional.of(nearest);
}
}
    
    public  boolean parseBackground(
            String[] properties,  ImageStore imageStore)
    {
        if (properties.length == Background.BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Background.BGND_COL]),Integer.parseInt(properties[Background.BGND_ROW]));
            String id = properties[Background.BGND_ID];
            this.setBackground(pt,new Background(id, imageStore.getImageList(id)));
        }
        return properties.length == Background.BGND_NUM_PROPERTIES;
    }

    public  boolean parseHouse(
            String[] properties, ImageStore imageStore)
    {
        if (properties.length == Entity.HOUSE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Entity.HOUSE_COL]),Integer.parseInt(properties[Entity.HOUSE_ROW]));
            Entity entity = Entity.createHouse(properties[Entity.HOUSE_ID], pt,imageStore.getImageList(Entity.HOUSE_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == Entity.HOUSE_NUM_PROPERTIES;
    }

    public  boolean parseSapling(String[] properties, ImageStore imageStore)
    {
        if (properties.length == Entity.SAPLING_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Entity.SAPLING_COL]),Integer.parseInt(properties[Entity.SAPLING_ROW]));
            String id = properties[Entity.SAPLING_ID];
            int health = Integer.parseInt(properties[Entity.SAPLING_HEALTH]);
            Entity entity = new Entity(EntityKind.SAPLING, 
                                id, 
                                pt, 
                                imageStore.getImageList( Entity.SAPLING_KEY), 
                                0, 0,
                                Entity.SAPLING_ACTION_ANIMATION_PERIOD, 
                                Entity.SAPLING_ACTION_ANIMATION_PERIOD,
                                health, Entity.SAPLING_HEALTH_LIMIT);
            this.tryAddEntity(entity);
        }
        return properties.length == Entity.SAPLING_NUM_PROPERTIES;
    }

    public  boolean parseDude(String[] properties, ImageStore imageStore)
    {
        if (properties.length == Entity.DUDE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Entity.DUDE_COL]),Integer.parseInt(properties[Entity.DUDE_ROW]));
            Entity entity = Entity.createDudeNotFull(properties[Entity.DUDE_ID],
                                                pt,
                                                Integer.parseInt(properties[Entity.DUDE_ACTION_PERIOD]),
                                                Integer.parseInt(properties[Entity.DUDE_ANIMATION_PERIOD]),
            Integer.parseInt(properties[Entity.DUDE_LIMIT]),
            imageStore.getImageList( Entity.DUDE_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == Entity.DUDE_NUM_PROPERTIES;
    }

    public  boolean parseFairy(String[] properties, ImageStore imageStore)
    {
        if (properties.length == Entity.FAIRY_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Entity.FAIRY_COL]),Integer.parseInt(properties[Entity.FAIRY_ROW]));
            Entity entity = Entity.createFairy(properties[Entity.FAIRY_ID],pt,
                                            Integer.parseInt(properties[Entity.FAIRY_ACTION_PERIOD]),
                                            Integer.parseInt(properties[Entity.FAIRY_ANIMATION_PERIOD]),
                                            imageStore.getImageList( Entity.FAIRY_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == Entity.FAIRY_NUM_PROPERTIES;
    }

    public  boolean parseTree(String[] properties, ImageStore imageStore)
    {
        if (properties.length == Entity.TREE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Entity.TREE_COL]),Integer.parseInt(properties[Entity.TREE_ROW]));
            Entity entity = Entity.createTree(properties[Entity.TREE_ID],
                                        pt,
                                        Integer.parseInt(properties[Entity.TREE_ACTION_PERIOD]),
                                        Integer.parseInt(properties[Entity.TREE_ANIMATION_PERIOD]),
                                        Integer.parseInt(properties[Entity.TREE_HEALTH]),
                                        imageStore.getImageList( Entity.TREE_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == Entity.TREE_NUM_PROPERTIES;
    }

    public  boolean parseObstacle(String[] properties, ImageStore imageStore)
    {
        if (properties.length == Entity.OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Entity.OBSTACLE_COL]),Integer.parseInt(properties[Entity.OBSTACLE_ROW]));
            Entity entity = Entity.createObstacle(properties[Entity.OBSTACLE_ID], 
                                            pt,
                                            Integer.parseInt(properties[Entity.OBSTACLE_ANIMATION_PERIOD]),
                                            imageStore.getImageList(Entity.OBSTACLE_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == Entity.OBSTACLE_NUM_PROPERTIES;
    }
}
