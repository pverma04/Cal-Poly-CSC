/**
 * An action that can be taken by an entity
 */
public abstract class Action
{
//    public ActionKind kind;
    private Entity entity;
//    public WorldModel world;
//    public ImageStore imageStore;
//    public int repeatCount;

    public Action(
//            ActionKind kind,
            Entity entity
//            WorldModel world,
//            ImageStore imageStore,
//            int repeatCount
            )
    {
//        this.kind = kind;
        this.entity = entity;
//        this.world = world;
//        this.imageStore = imageStore;
//        this.repeatCount = repeatCount;
    }

    public Entity getEntity(){
        return entity;
    }

    public abstract void executeAction(EventScheduler scheduler);

//    public void executeAction(EventScheduler scheduler) {
//        switch (this.kind) {
//            case ACTIVITY:
//                executeActivityAction(scheduler);
//                break;
//
//            case ANIMATION:
//                executeAnimationAction(scheduler);
//                break;
//        }
//    }

//    public void executeAnimationAction(EventScheduler scheduler)
//    {
//        this.entity.nextImage();
//
//        if (this.repeatCount != 1) {
//            scheduler.scheduleEvent(this.entity,
//                    this.entity.createAnimationAction(Math.max(this.repeatCount - 1,
//                                    0)),
//                    ((Animated)this.entity).getAnimationPeriod());
//        }
//    }

//    public void executeActivityAction(EventScheduler scheduler)
//    {
//        if (Sapling.class.equals(this.entity.getClass())) {
//            ((Sapling) this.entity).execute(this.world,
//                    this.imageStore, scheduler);
//        } else if (Tree.class.equals(this.entity.getClass())) {
//            ((Tree) this.entity).execute(this.world,
//                    this.imageStore, scheduler);
//        } else if (Fairy.class.equals(this.entity.getClass())) {
//            ((Fairy) this.entity).execute(this.world,
//                    this.imageStore, scheduler);
//        } else if (DudeNotFull.class.equals(this.entity.getClass())) {
//            ((DudeNotFull) this.entity).execute(this.world,
//                    this.imageStore, scheduler);
//        } else if (DudeFull.class.equals(this.entity.getClass())) {
//            ((DudeFull) this.entity).execute(this.world,
//                    this.imageStore, scheduler);
//        } else {
//            throw new UnsupportedOperationException(String.format(
//                    "executeActivityAction not supported for %s",
//                    this.entity.getClass()));
//        }
//    }
}
