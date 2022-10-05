import java.util.List;
import java.util.Objects;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Entity
{
    private EntityKind kind;
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    private int health;
    private int healthLimit;

    public Entity(
            EntityKind kind,
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod,
            int health,
            int healthLimit) {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;
    }
    public Point getPos() {
        return this.position;
    }
    public void setPos(Point p) {
        this.position = p;
    }
    public EntityKind getEntityKind() {
        return this.kind;
    }
    public String getID() {
        return this.id;
    }
    public int getImageIndex() {
        return this.imageIndex;
    }
    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
    public List<PImage> getImages() {
        return this.images;
    }
    public int getResourceLimit() {
        return this.resourceLimit;
    }
    public int getResourceCount() {
        return this.resourceCount;
    }
    public void incrementResourceCount() {
        this.resourceCount++;
    }
    public int getActionPeriod() {
        return this.actionPeriod;
    }
    //public int getAnimationPeriod() {
    //return this.animationPeriod;
    //}
    public int getHealth() {
        return this.health;
    }
    public void incrementHealth() {
        this.health++;
    }
    public void decrementHealth() {
        this.health--;
    }
    public int getHealthLimit() {
        return this.healthLimit;
    }

    public int getAnimationPeriod() {
        switch (this.getEntityKind()) {
            case DUDE_FULL:
            case DUDE_NOT_FULL:
            case OBSTACLE:
            case FAIRY:
            case SAPLING:
            case TREE:
                return this.getAnimationPeriod();
            default:
                throw new UnsupportedOperationException(
                        String.format("getAnimationPeriod not supported for %s",
                                this.getEntityKind()));
        }
    }
    public void nextImage() {
        this.setImageIndex((this.getImageIndex() + 1) % this.getImages().size());
    }
}
