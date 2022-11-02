public class AnimationAction extends Action{

    private int repeatCount;
    public AnimationAction(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(entity, world, imageStore);
        this.repeatCount = repeatCount;
    }
    public int getRepeatCount() {return this.repeatCount;}

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.getEntity().nextImage();

        if (this.getRepeatCount() != 1) {
            scheduler.scheduleEvent(this.getEntity(),
                    new AnimationAction(this.getEntity(), this.getWorldModel(), this.getImageStore(), Math.max(this.getRepeatCount() - 1, 0)),
                    this.getEntity().getAnimationPeriod());
        }
    }
}
