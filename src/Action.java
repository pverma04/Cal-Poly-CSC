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

    public  void executeAction(EventScheduler scheduler) {
        switch (this.getActionKind()) {
            case ACTIVITY:
                executeActivityAction(this, scheduler);
                break;

            case ANIMATION:
                executeAnimationAction(this, scheduler);
                break;
        }
    }
    private  void executeAnimationAction(Action action, EventScheduler scheduler)
    {
        action.getEntity().nextImage();

        if (action.getRepeatCount() != 1) {
            scheduler.scheduleEvent(action.getEntity(),
                    action.getEntity().createAnimationAction(Math.max(action.getRepeatCount() - 1,
                                    0)),
                                    action.getEntity().getAnimationPeriod());
        }
    }

    public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore)
    {
        return new Action(ActionKind.ACTIVITY, entity, world, imageStore, 0);
    }


    private  void executeActivityAction(
            Action action, EventScheduler scheduler)
    {
        switch (action.getEntity().getEntityKind()) {
            case SAPLING:
            action.getEntity().executeSaplingActivity(action.getWorldModel(),action.getImageStore(), scheduler);
                break;

            case TREE:
            action.getEntity().executeTreeActivity(action.getWorldModel(),
                        action.getImageStore(), scheduler);
                break;

            case FAIRY:
            action.getEntity().executeFairyActivity(action.getWorldModel(),
                        action.getImageStore(), scheduler);
                break;

            case DUDE_NOT_FULL:
            action.getEntity().executeDudeNotFullActivity( action.getWorldModel(),
                        action.getImageStore(), scheduler);
                break;

            case DUDE_FULL:
            action.getEntity().executeDudeFullActivity( action.getWorldModel(),
                        action.getImageStore(), scheduler);
                break;

            default:
                throw new UnsupportedOperationException(String.format(
                        "executeActivityAction not supported for %s",
                        action.getEntity().getEntityKind()));
        }
    }

}
