public class ActivityAction extends Action{
    private WorldModel world;
    private ImageStore imageStore;
    public ActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        super(entity);
        this.world = world;
        this.imageStore = imageStore;
    }

    public WorldModel getWorld(){
        return world;
    }

    public ImageStore getImageStore(){
        return imageStore;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        if (Sapling.class.equals(this.getEntity().getClass())) {
            ((Sapling) this.getEntity()).execute(this.world,
                    this.imageStore, scheduler);
        } else if (Tree.class.equals(this.getEntity().getClass())) {
            ((Tree) this.getEntity()).execute(this.world,
                    this.imageStore, scheduler);
        } else if (Fairy.class.equals(this.getEntity().getClass())) {
            ((Fairy) this.getEntity()).execute(this.world,
                    this.imageStore, scheduler);
        }
        else if (DudeNotFull.class.equals(this.getEntity().getClass())) {
            ((DudeNotFull) this.getEntity()).execute(this.world,
                    this.imageStore, scheduler);
        }
        else if (DudeFull.class.equals(this.getEntity().getClass())) {
            ((DudeFull) this.getEntity()).execute(this.world,
                    this.imageStore, scheduler);
        } else {
            throw new UnsupportedOperationException(String.format(
                    "executeActivityAction not supported for %s",
                    this.getEntity().getClass()));
        }
    }
}
