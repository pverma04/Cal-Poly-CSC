/**
 * An action that can be taken by an entity
 */
public class Action //NO LONGER FINAL
{
    //private ActionKind kind; //SHOULD NOT BE USED
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Action(
            //ActionKind kind,
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount) {
        //this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

//    public ActionKind getActionKind() {
//        return this.kind;
//    }
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
        if (this instanceof ActivityAction) {
            //case ActivityAction.class:
            ((ActivityAction) this).executeActivityAction(scheduler);
            //break;
        }
        if (this instanceof AnimationAction){
            //case AnimationAction.class:
                ((AnimationAction)this).executeAnimationAction(scheduler);
                //break;
        }
    }
//    private  void executeAnimationAction(EventScheduler scheduler) {
//        this.getEntity().nextImage();
//
//        if (this.getRepeatCount() != 1) {
//            scheduler.scheduleEvent(this.getEntity(),
//                    this.getEntity().createAnimationAction(Math.max(this.getRepeatCount() - 1, 0)),
//                         this.getEntity().getAnimationPeriod());
//        }
//    }




//    private void executeActivityAction(EventScheduler scheduler) {
//        switch (this.getEntity().getEntityKind()) {
//            case SAPLING:
//                this.getEntity().executeSaplingActivity(this.getWorldModel(), this.getImageStore(), scheduler);
//                break;
//
//            case TREE:
//                this.getEntity().executeTreeActivity(this.getWorldModel(),
//                        this.getImageStore(), scheduler);
//                break;
//
//            case FAIRY:
//                this.getEntity().executeFairyActivity(this.getWorldModel(),
//                        this.getImageStore(), scheduler);
//                break;
//
//            case DUDE_NOT_FULL:
//                this.getEntity().executeDudeNotFullActivity(this.getWorldModel(),
//                        this.getImageStore(), scheduler);
//                break;
//
//            case DUDE_FULL:
//                this.getEntity().executeDudeFullActivity(this.getWorldModel(),
//                        this.getImageStore(), scheduler);
//                break;
//
//            default:
//                throw new UnsupportedOperationException(String.format(
//                        "executeActivityAction not supported for %s",
//                        this.getEntity().getEntityKind()));
//        }
//    }

}
