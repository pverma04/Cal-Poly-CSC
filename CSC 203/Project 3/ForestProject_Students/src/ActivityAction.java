public class ActivityAction extends Action{
    private ActionEntity actionEntity;

    public ActivityAction(ActionEntity actionEntity, WorldModel world, ImageStore imageStore) {
        super(world, imageStore);
        this.actionEntity = actionEntity;
    }
    @Override
    public void executeAction(EventScheduler scheduler) {
        System.out.println("executeActivity");
        this.actionEntity.executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
    }
}
