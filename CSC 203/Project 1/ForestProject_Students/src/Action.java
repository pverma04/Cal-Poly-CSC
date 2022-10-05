/**
 * An action that can be taken by an entity
 */
public final class Action
{
    private ActionKind kind;
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Action(
            ActionKind kind,
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount) {
        this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }
    public ActionKind getActionKind() {
        return this.kind;
    }
    public Entity getEntity() {
        return this.entity;
    }
    public WorldModel getWorldModel() {
        return this.world;
    }
    public ImageStore getImageStore() {
        return this.imageStore;
    }
    public int getRepeatCount() {
        return this.repeatCount;
    }
}
