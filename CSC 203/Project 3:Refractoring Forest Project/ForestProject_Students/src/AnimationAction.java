public class AnimationAction extends Action{

    private int repeatCount;
    private Entity entity;
    public AnimationAction(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(world, imageStore);
        this.entity = entity;
        this.repeatCount = repeatCount;
    }
    public int getRepeatCount() {return this.repeatCount;}

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (this.getRepeatCount() != 1) {
            scheduler.scheduleEvent(this.entity,
                    new AnimationAction(this.entity, this.getWorldModel(), this.getImageStore(), Math.max(this.getRepeatCount() - 1, 0)),
                    this.entity.getAnimationPeriod());
        }
    }
}
