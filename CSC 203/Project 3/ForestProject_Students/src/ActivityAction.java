public class ActivityAction extends Action{

    public ActivityAction(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore, repeatCount);
    }

    public void executeActivityAction(EventScheduler scheduler) {
        switch (this.getEntity().getEntityKind()) {
            case SAPLING:
                this.getEntity().executeSaplingActivity(this.getWorldModel(), this.getImageStore(), scheduler);
                break;

            case TREE:
                this.getEntity().executeTreeActivity(this.getWorldModel(),
                        this.getImageStore(), scheduler);
                break;

            case FAIRY:
                this.getEntity().executeFairyActivity(this.getWorldModel(),
                        this.getImageStore(), scheduler);
                break;

            case DUDE_NOT_FULL:
                this.getEntity().executeDudeNotFullActivity(this.getWorldModel(),
                        this.getImageStore(), scheduler);
                break;

            case DUDE_FULL:
                this.getEntity().executeDudeFullActivity(this.getWorldModel(),
                        this.getImageStore(), scheduler);
                break;

            default:
                throw new UnsupportedOperationException(String.format(
                        "executeActivityAction not supported for %s",
                        this.getEntity().getEntityKind()));
        }
    }
}
