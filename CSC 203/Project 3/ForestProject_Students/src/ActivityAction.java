public class ActivityAction extends Action{

    public ActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        super(entity, world, imageStore);
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        if (this.getEntity() instanceof SaplingEntity){
            ((SaplingEntity)this.getEntity()).executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof TreeEntity){
            ((TreeEntity)this.getEntity()).executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
        }
//        else if (this.getEntity() instanceof FairyEntity){
//            ((FairyEntity)this.getEntity()).executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
//        }
//        else if (this.getEntity() instanceof DudeNotFullEntity){
//            ((DudeNotFullEntity)this.getEntity()).executeActivity(this.getWorldModel(),
//                    this.getImageStore(), scheduler);
//        }
//        else if (this.getEntity() instanceof DudeFullEntity){
//            ((DudeFullEntity)this.getEntity()).executeActivity(this.getWorldModel(),
//                    this.getImageStore(), scheduler);
//        }
        else {
            throw new UnsupportedOperationException(String.format(
                    "executeActivityAction not supported for %s",
                    this.getEntity().getEntityKind()));
        }
    }

    public void executeActivityAction(EventScheduler scheduler) {
        if (this.getEntity() instanceof SaplingEntity){
            ((SaplingEntity)this.getEntity()).executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof TreeEntity){
            ((TreeEntity)this.getEntity()).executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof FairyEntity){
            ((FairyEntity)this.getEntity()).executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof DudeNotFullEntity){
            ((DudeNotFullEntity)this.getEntity()).executeActivity(this.getWorldModel(),
                    this.getImageStore(), scheduler);
        }
        else if (this.getEntity() instanceof DudeFullEntity){
            ((DudeFullEntity)this.getEntity()).executeActivity(this.getWorldModel(),
                    this.getImageStore(), scheduler);
        }
        else {
            throw new UnsupportedOperationException(String.format(
                    "executeActivityAction not supported for %s",
                    this.getEntity().getEntityKind()));
        }

//        switch (this.getEntity()) {
//            case SaplingEntity.class:
//                ((SaplingEntity)this.getEntity()).executeActivity(this.getWorldModel(), this.getImageStore(), scheduler);
//                break;
//
//            case TreeEntity.class:
//                ((TreeEntity)this.getEntity()).executeActivity(this.getWorldModel(),
//                        this.getImageStore(), scheduler);
//                break;
//
//            case FairyEntity.class:
//                ((FairyEntity)this.getEntity()).executeActivity(this.getWorldModel(),
//                        this.getImageStore(), scheduler);
//                break;
//
//            case DudeNotFullEntity.class:
//                ((DudeNotFullEntity)this.getEntity()).executeActivity(this.getWorldModel(),
//                        this.getImageStore(), scheduler);
//                break;
//
//            case DudeFullEntity.class:
//                ((DudeFullEntity)this.getEntity()).executeActivity(this.getWorldModel(),
//                        this.getImageStore(), scheduler);
//                break;
//
//            default:
//                throw new UnsupportedOperationException(String.format(
//                        "executeActivityAction not supported for %s",
//                        this.getEntity().getEntityKind()));
//        }
    }
}
