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


    public Entity getOccupancyCell(Point pos) {
        return this.getoccupancy()[pos.getY()][pos.getX()];
    }

    public boolean withinBounds(Point pos) {
        return pos.getY() >= 0 && pos.getY() < this.numRows && pos.getX() >= 0
                && pos.getX() < this.numCols;
    }
    public boolean adjacent(Point p1, Point p2) {
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY()
                && Math.abs(p1.getX() - p2.getX()) == 1);
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
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
    }
    public  void setOccupancyCell(Point pos, Entity entity)
    {
        occupancy[pos.getY()][pos.getX()] = entity;
    }

    public  Optional<PImage> getBackgroundImage(Point pos) {
        if (withinBounds(pos)) {
            return Optional.of(this.getBackgroundCell(pos).getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }

    public Background getBackgroundCell(Point pos) {
        return background[pos.getY()][pos.getX()];
    }

    public  void setBackground(Point pos, Background background) {
        if (this.withinBounds(pos)){
            setBackgroundCell(pos, background);
        }
    }

    private  void setBackgroundCell(Point pos, Background background) { this.background[pos.getY()][pos.getX()] = background; }


    public  void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (this.withinBounds(pos) && !pos.equals(oldPos)) {
            this.setOccupancyCell(oldPos, null);
            this.removeEntityAt(pos);
            this.setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPosition());
    }

    private  void removeEntityAt( Point pos) {
        if (this.withinBounds(pos) && this.getOccupancyCell(pos) != null) {
            Entity entity = this.getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
                * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            this.setOccupancyCell(pos, null);
        }
    }

    public  Optional<Entity> findNearest(Point pos, List<Class> kinds) {
        List<Entity> ofType = new LinkedList<>();
        for (Class kind : kinds) {
            for (Entity entity : this.entities) {
                if (kind.isInstance(entity)) {
                    ofType.add(entity);
                }
            }
        }
        return nearestEntity(ofType, pos);
    }

    public  Optional<Entity> getOccupant( Point pos) {
        if (this.isOccupied(pos)) {
            return Optional.of(this.getOccupancyCell(pos));
        } else {
            return Optional.empty();
        }
    }

    private  int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();
        return deltaX * deltaX + deltaY * deltaY;
    }

    public  Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }
            return Optional.of(nearest);
        }
    }
    
    public  boolean parseBackground(String[] properties,  ImageStore imageStore) {
        if (properties.length == Background.BGND_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[Background.BGND_COL]),Integer.parseInt(properties[Background.BGND_ROW]));
            String id = properties[Background.BGND_ID];
            this.setBackground(pt,new Background(id, imageStore.getImageList(id)));
        }
        return properties.length == Background.BGND_NUM_PROPERTIES;
    }

    public boolean parseHouse(String[] properties, ImageStore imageStore) {
        if (properties.length == HouseEntity.HOUSE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[HouseEntity.HOUSE_COL]),Integer.parseInt(properties[HouseEntity.HOUSE_ROW]));
            Entity entity = Entity.createHouse(properties[HouseEntity.HOUSE_ID], pt,imageStore.getImageList(HouseEntity.HOUSE_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == HouseEntity.HOUSE_NUM_PROPERTIES;
    }

    public boolean parseSapling(String[] properties, ImageStore imageStore) {
        if (properties.length == SaplingEntity.SAPLING_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[SaplingEntity.SAPLING_COL]),Integer.parseInt(properties[SaplingEntity.SAPLING_ROW]));
            String id = properties[SaplingEntity.SAPLING_ID];
            int health = Integer.parseInt(properties[SaplingEntity.SAPLING_HEALTH]);
            Entity entity = Entity.createSapling(id, pt,
                    imageStore.getImageList(SaplingEntity.SAPLING_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == SaplingEntity.SAPLING_NUM_PROPERTIES;
    }

    public boolean parseDude(String[] properties, ImageStore imageStore) {
        if (properties.length == DudeEntity.DUDE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[DudeEntity.DUDE_COL]),Integer.parseInt(properties[DudeEntity.DUDE_ROW]));
            Entity entity = Entity.createDudeNotFull(properties[DudeEntity.DUDE_ID], pt,
                    Integer.parseInt(properties[DudeEntity.DUDE_ACTION_PERIOD]),
                    Integer.parseInt(properties[DudeEntity.DUDE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[DudeEntity.DUDE_LIMIT]),
                    imageStore.getImageList( DudeEntity.DUDE_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == DudeEntity.DUDE_NUM_PROPERTIES;
    }

    public boolean parseFairy(String[] properties, ImageStore imageStore) {
        if (properties.length == FairyEntity.FAIRY_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[FairyEntity.FAIRY_COL]),Integer.parseInt(properties[FairyEntity.FAIRY_ROW]));
            Entity entity = Entity.createFairy(properties[FairyEntity.FAIRY_ID],pt,
                    Integer.parseInt(properties[FairyEntity.FAIRY_ACTION_PERIOD]),
                    Integer.parseInt(properties[FairyEntity.FAIRY_ANIMATION_PERIOD]),
                    imageStore.getImageList( FairyEntity.FAIRY_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == FairyEntity.FAIRY_NUM_PROPERTIES;
    }

    public boolean parseTree(String[] properties, ImageStore imageStore)
    {
        if (properties.length == TreeEntity.TREE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[TreeEntity.TREE_COL]),Integer.parseInt(properties[TreeEntity.TREE_ROW]));
            Entity entity = Entity.createTree(properties[TreeEntity.TREE_ID], pt,
                    Integer.parseInt(properties[TreeEntity.TREE_ACTION_PERIOD]),
                    Integer.parseInt(properties[TreeEntity.TREE_ANIMATION_PERIOD]),
                    Integer.parseInt(properties[TreeEntity.TREE_HEALTH]),
                    imageStore.getImageList(TreeEntity.TREE_KEY));
            this.tryAddEntity(entity);
        }
        return properties.length == TreeEntity.TREE_NUM_PROPERTIES;
    }

    public boolean parseObstacle(String[] properties, ImageStore imageStore) {
        if (properties.length == ObstacleEntity.OBSTACLE_NUM_PROPERTIES) {
            Point pt = new Point(Integer.parseInt(properties[ObstacleEntity.OBSTACLE_COL]),Integer.parseInt(properties[ObstacleEntity.OBSTACLE_ROW]));
            Entity entity = new ObstacleEntity(properties[ObstacleEntity.OBSTACLE_ID], pt,
                                            imageStore.getImageList(ObstacleEntity.OBSTACLE_KEY), 0);
            this.tryAddEntity(entity);
        }
        return properties.length == ObstacleEntity.OBSTACLE_NUM_PROPERTIES;
    }
}
