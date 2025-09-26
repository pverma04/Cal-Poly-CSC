public class AnimationAction extends Action{
    private int repeatCount;

    public AnimationAction(Entity entity, int repeatCount) {
        super(entity);
        this.repeatCount = repeatCount;
    }

    public int getRepeatCount(){
        return repeatCount;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.getEntity().nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.getEntity(),
                    this.getEntity().createAnimationAction(Math.max(this.repeatCount - 1,
                            0)),
                    ((Animated) this.getEntity()).getAnimationPeriod());
        }
    }
}
