import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FairyEntity extends Excecutable{
    public static final String FAIRY_KEY = "fairy";
    public static final int FAIRY_NUM_PROPERTIES = 6;
    public static final int FAIRY_ID = 1;
    public static final int FAIRY_COL = 2;
    public static final int FAIRY_ROW = 3;
    public static final int FAIRY_ANIMATION_PERIOD = 4;
    public static final int FAIRY_ACTION_PERIOD = 5;
    public FairyEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        this.executeActivity(world, imageStore, scheduler);
    }

    public void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler) {
        Optional<Entity> fairyTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(StumpEntity))); //not sure what to do here
        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
                Entity sapling = new SaplingEntity("sapling_" + this.getId(), tgtPos,
                        imageStore.getImageList(SaplingEntity.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),this.getActionPeriod());
    }

    @Override
    public void executeAction(EventScheduler scheduler) {

    }
}
