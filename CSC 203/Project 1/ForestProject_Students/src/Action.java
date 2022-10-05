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
    public void executeAction(EventScheduler scheduler) {
        switch (this.getActionKind()) {
            case ACTIVITY:
                this.executeActivityAction(scheduler);
                break;

            case ANIMATION:
                this.executeAnimationAction(scheduler);
                break;
        }
    }
    public void executeAnimationAction(EventScheduler scheduler) {
        this.getEntity().nextImage();

        if (this.getRepeatCount() != 1) {
            scheduler.scheduleEvent(this.getEntity(),
                    this.getEntity().createAnimationAction(Math.max(this.getRepeatCount() - 1,
                                    0)),
                    this.getEntity().getAnimationPeriod());
        }
    }
    public void executeActivityAction(EventScheduler scheduler) {
        switch (this.getEntity().getEntityKind()) {
            case SAPLING:
                Functions.executeSaplingActivity(this.getEntity(), this.getWorldModel(),
                        this.getImageStore(), scheduler);
                break;

            case TREE:
                Functions.executeTreeActivity(this.getEntity(), this.getWorldModel(),
                        this.getImageStore(), scheduler);
                break;

            case FAIRY:
                Functions.executeFairyActivity(this.getEntity(), this.getWorldModel(),
                        this.getImageStore(), scheduler);
                break;

            case DUDE_NOT_FULL:
                Functions.executeDudeNotFullActivity(this.getEntity(), this.getWorldModel(),
                        this.getImageStore(), scheduler);
                break;

            case DUDE_FULL:
                Functions.executeDudeFullActivity(this.getEntity(), this.getWorldModel(),
                        this.getImageStore(), scheduler);
                break;

            default:
                throw new UnsupportedOperationException(String.format(
                        "executeActivityAction not supported for %s",
                        this.getEntity().getEntityKind()));
        }
    }
}
