public class ActivityAction extends Action{
    private ActionEntity actionEntity;

    public ActivityAction(ActionEntity actionEntity, WorldModel world, ImageStore imageStore) {
        super(world, imageStore);
        this.actionEntity = actionEntity;
    }
//    public void executeActivityAction(EventScheduler scheduler) {
//        this.actionEntity.executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
//    }
    @Override
    public void executeAction(EventScheduler scheduler) {
        System.out.println("ActvityAction execute");
        this.actionEntity.executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
    }
}
