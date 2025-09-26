import java.util.*;

import processing.core.PImage;
import processing.core.PApplet;
//
///**
// * This class contains many functions written in a procedural style.
// * You will reduce the size of this class over the next several weeks
// * by refactoring this codebase to follow an OOP style.
// */
public final class Functions {
//    public static final Random rand = new Random();
//
//    public static final int COLOR_MASK = 0xffffff;
//    public static final int KEYED_IMAGE_MIN = 5;
//    private static final int KEYED_RED_IDX = 2;
//    private static final int KEYED_GREEN_IDX = 3;
//    private static final int KEYED_BLUE_IDX = 4;
//
//    public static final int PROPERTY_KEY = 0;
//
//    public static final List<String> PATH_KEYS = new ArrayList<>(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right",
//            "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));
//
//    public static final String SAPLING_KEY = "sapling";
//    public static final int SAPLING_HEALTH_LIMIT = 5;
//    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time
//    public static final int SAPLING_NUM_PROPERTIES = 4;
//    public static final int SAPLING_ID = 1;
//    public static final int SAPLING_COL = 2;
//    public static final int SAPLING_ROW = 3;
//    public static final int SAPLING_HEALTH = 4;
//
//    public static final String BGND_KEY = "background";
//    public static final int BGND_NUM_PROPERTIES = 4;
//    public static final int BGND_ID = 1;
//    public static final int BGND_COL = 2;
//    public static final int BGND_ROW = 3;
//
//    public static final String OBSTACLE_KEY = "obstacle";
//    public static final int OBSTACLE_NUM_PROPERTIES = 5;
//    public static final int OBSTACLE_ID = 1;
//    public static final int OBSTACLE_COL = 2;
//    public static final int OBSTACLE_ROW = 3;
//    public static final int OBSTACLE_ANIMATION_PERIOD = 4;
//
//    public static final String DUDE_KEY = "dude";
//    public static final int DUDE_NUM_PROPERTIES = 7;
//    public static final int DUDE_ID = 1;
//    public static final int DUDE_COL = 2;
//    public static final int DUDE_ROW = 3;
//    public static final int DUDE_LIMIT = 4;
//    public static final int DUDE_ACTION_PERIOD = 5;
//    public static final int DUDE_ANIMATION_PERIOD = 6;
//
//    public static final String HOUSE_KEY = "house";
//    public static final int HOUSE_NUM_PROPERTIES = 4;
//    public static final int HOUSE_ID = 1;
//    public static final int HOUSE_COL = 2;
//    public static final int HOUSE_ROW = 3;
//
//    public static final String FAIRY_KEY = "fairy";
//    public static final int FAIRY_NUM_PROPERTIES = 6;
//    public static final int FAIRY_ID = 1;
//    public static final int FAIRY_COL = 2;
//    public static final int FAIRY_ROW = 3;
//    public static final int FAIRY_ANIMATION_PERIOD = 4;
//    public static final int FAIRY_ACTION_PERIOD = 5;
//
//    public static final String STUMP_KEY = "stump";
//
//    public static final String TREE_KEY = "tree";
//    public static final int TREE_NUM_PROPERTIES = 7;
//    public static final int TREE_ID = 1;
//    public static final int TREE_COL = 2;
//    public static final int TREE_ROW = 3;
//    public static final int TREE_ANIMATION_PERIOD = 4;
//    public static final int TREE_ACTION_PERIOD = 5;
//    public static final int TREE_HEALTH = 6;
//
//    public static final int TREE_ANIMATION_MAX = 600;
//    public static final int TREE_ANIMATION_MIN = 50;
//    public static final int TREE_ACTION_MAX = 1400;
//    public static final int TREE_ACTION_MIN = 1000;
//    public static final int TREE_HEALTH_MAX = 3;
//    public static final int TREE_HEALTH_MIN = 1;
//
//
//    public static PImage getCurrentImage(Object entity) {
//        if (entity instanceof Background) {
//            return ((Background) entity).getImages().get(
//                    ((Background) entity).getImageIndex());
//        }
//        else if (entity instanceof Entity) {
//            return ((Entity) entity).getImages().get(((Entity) entity).getImageIndex());
//        }
//        else {
//            throw new UnsupportedOperationException(
//                    String.format("getCurrentImage not supported for %s",
//                            entity));
//        }
//    }
//
//    public static int getAnimationPeriod(Entity entity) {
//        if (DudeFull.class.equals(entity.getClass()) ||
//                Obstacle.class.equals(entity.getClass()) || Fairy.class.equals(entity.getClass()) ||
//                Sapling.class.equals(entity.getClass()) || Tree.class.equals(entity.getClass())) {
//            return ((Animated) entity).getAnimationPeriod();
//        }
//        throw new UnsupportedOperationException(
//                String.format("getAnimationPeriod not supported for %s",
//                        entity.getClass()));
//    }
//
//    public static void nextImage(Entity entity) {
//        entity.setImageIndex((entity.getImageIndex() + 1) % entity.getImages().size());
//    }
//
//    public static void executeAction(Action action, EventScheduler scheduler) {
//        if (ActivityAction.class.equals(action.getClass())) {
//            executeActivityAction(action, scheduler);
//        } else if (AnimationAction.class.equals(action.getClass())) {
//            executeAnimationAction(action, scheduler);
//        }
//    }
//
//    public static void executeAnimationAction(
//            Action action, EventScheduler scheduler)
//    {
//        nextImage(action.getEntity());
//
//        if (((AnimationAction) action).getRepeatCount() != 1) {
//            scheduleEvent(scheduler, action.getEntity(),
//                    createAnimationAction(action.getEntity(),
//                            Math.max(((AnimationAction) action).getRepeatCount() - 1,
//                                    0)),
//                    getAnimationPeriod(action.getEntity()));
//        }
//    }
//
//    public static void executeActivityAction(
//            Action action, EventScheduler scheduler)
//    {
//        if (Sapling.class.equals(action.getEntity().getClass())) {
//            executeSaplingActivity(action.getEntity(), ((ActivityAction) action).getWorld(),
//                    ((ActivityAction) action).getImageStore(), scheduler);
//        } else if (Tree.class.equals(action.getEntity().getClass())) {
//            executeTreeActivity(action.getEntity(), ((ActivityAction) action).getWorld(),
//                    ((ActivityAction) action).getImageStore(), scheduler);
//        } else if (Fairy.class.equals(action.getEntity().getClass())) {
//            executeFairyActivity((Fairy)action.getEntity(), ((ActivityAction) action).getWorld(),
//                    ((ActivityAction) action).getImageStore(), scheduler);
//        }
////        else if (DudeNotFull.class.equals(action.getEntity().getClass())) {
////            executeDudeNotFullActivity(action.getEntity(), ((ActivityAction) action).getWorld(),
////                    ((ActivityAction) action).getImageStore(), scheduler);
////        }
//        else if (DudeFull.class.equals(action.getEntity().getClass())) {
//            executeDudeFullActivity(action.getEntity(), ((ActivityAction) action).getWorld(),
//                    ((ActivityAction) action).getImageStore(), scheduler);
//        } else {
//            throw new UnsupportedOperationException(String.format(
//                    "executeActivityAction not supported for %s",
//                    action.getEntity().getClass()));
//        }
//    }
//
//    public static void executeSaplingActivity(
//            Entity entity,
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        HasHealth healthEnt = (HasHealth)entity;
//        healthEnt.setHealth(1);
//        if (!transformPlant(healthEnt, world, scheduler, imageStore))
//        {
//            scheduleEvent(scheduler, healthEnt,
//                    createActivityAction(healthEnt, world, imageStore),
//                    healthEnt.getActionPeriod());
//        }
//    }
//
//    public static void executeTreeActivity(
//            Entity entity,
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//
//        if (!transformPlant(entity, world, scheduler, imageStore)) {
//
//            scheduleEvent(scheduler, entity,
//                    createActivityAction(entity, world, imageStore),
//                    ((Active) entity).getActionPeriod());
//        }
//    }
//
//    public static void executeFairyActivity(
//            Fairy entity,
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> fairyTarget =
//                findNearest(world, entity.getPosition(), new ArrayList<>(Arrays.asList(Stump.class)));
//
//        if (fairyTarget.isPresent()) {
//            Point tgtPos = fairyTarget.get().getPosition();
//
//            if (entity.moveTo(world, fairyTarget.get(), scheduler)) {
//                Entity sapling = createSapling("sapling_" + entity.getId(), tgtPos,
//                        getImageList(imageStore, SAPLING_KEY));
//
//                addEntity(world, sapling);
//                scheduleActions(sapling, scheduler, world, imageStore);
//            }
//        }
//
//        scheduleEvent(scheduler, entity,
//                createActivityAction(entity, world, imageStore),
//                ((Active) entity).getActionPeriod());
//    }
//
//    public static void executeDudeNotFullActivity(
//            Entity entity,
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> target =
//                findNearest(world, entity.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));
//
//        if (!target.isPresent()
//                || !moveToNotFull((Resourceful)entity, world,
//                target.get(),
//                scheduler)
//                || !transformNotFull(entity, world, scheduler, imageStore)
//        )
//        {
//            scheduleEvent(scheduler, entity,
//                    createActivityAction(entity, world, imageStore),
//                    ((Active) entity).getActionPeriod());
//        }
//    }
//
//    public static void executeDudeFullActivity(
//            Entity entity,
//            WorldModel world,
//            ImageStore imageStore,
//            EventScheduler scheduler)
//    {
//        Optional<Entity> fullTarget =
//                findNearest(world, entity.getPosition(), new ArrayList<>(Arrays.asList(House.class)));
//
//        if (fullTarget.isPresent() && moveToFull((Resourceful)entity, world,
//                fullTarget.get(), scheduler))
//        {
//            transformFull(entity, world, scheduler, imageStore);
//        }
//        else {
//            scheduleEvent(scheduler, entity,
//                    createActivityAction(entity, world, imageStore),
//                    ((Active) entity).getActionPeriod());
//        }
//    }
//
//
//    public static void scheduleActions(
//            Entity entity,
//            EventScheduler scheduler,
//            WorldModel world,
//            ImageStore imageStore)
//    {
//        if (DudeFull.class.equals(entity.getClass())) {
//            scheduleEvent(scheduler, entity,
//                    createActivityAction(entity, world, imageStore),
//                    ((Active) entity).getActionPeriod());
//            scheduleEvent(scheduler, entity,
//                    createAnimationAction(entity, 0),
//                    getAnimationPeriod(entity));
//        }
////        else if (DudeNotFull.class.equals(entity.getClass())) {
////            scheduleEvent(scheduler, entity,
////                    createActivityAction(entity, world, imageStore),
////                    ((Active) entity).getActionPeriod());
////            scheduleEvent(scheduler, entity,
////                    createAnimationAction(entity, 0),
////                    getAnimationPeriod(entity));
////        }
//        else if (Obstacle.class.equals(entity.getClass())) {
//            scheduleEvent(scheduler, entity,
//                    createAnimationAction(entity, 0),
//                    getAnimationPeriod(entity));
//        } else if (Fairy.class.equals(entity.getClass())) {
//            scheduleEvent(scheduler, entity,
//                    createActivityAction(entity, world, imageStore),
//                    ((Active) entity).getActionPeriod());
//            scheduleEvent(scheduler, entity,
//                    createAnimationAction(entity, 0),
//                    getAnimationPeriod(entity));
//        } else if (Sapling.class.equals(entity.getClass())) {
//            scheduleEvent(scheduler, entity,
//                    createActivityAction(entity, world, imageStore),
//                    ((Active) entity).getActionPeriod());
//            scheduleEvent(scheduler, entity,
//                    createAnimationAction(entity, 0),
//                    getAnimationPeriod(entity));
//        } else if (Tree.class.equals(entity.getClass())) {
//            scheduleEvent(scheduler, entity,
//                    createActivityAction(entity, world, imageStore),
//                    ((Active) entity).getActionPeriod());
//            scheduleEvent(scheduler, entity,
//                    createAnimationAction(entity, 0),
//                    getAnimationPeriod(entity));
//        }
//    }
//
//    public static boolean transformNotFull(
//            Entity entity,
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        Resourceful resourceEnt = (Resourceful)entity;
//        if (resourceEnt.getResourceCount() >= resourceEnt.getResourceLimit()) {
//            Entity miner = createDudeFull(entity.getId(),
//                    entity.getPosition(), resourceEnt.getActionPeriod(),
//                    resourceEnt.getAnimationPeriod(),
//                    resourceEnt.getResourceLimit(),
//                    entity.getImages());
//
//            removeEntity(world, entity);
//            unscheduleAllEvents(scheduler, entity);
//
//            addEntity(world, miner);
//            scheduleActions(miner, scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }
//
//    public static void transformFull(
//            Entity entity,
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        Entity miner = createDudeNotFull(entity.getId(),
//                entity.getPosition(), ((Resourceful) entity).getActionPeriod(),
//                ((Resourceful)entity).getAnimationPeriod(),
//                ((Resourceful) entity).getResourceLimit(),
//                entity.getImages());
//
//        removeEntity(world, entity);
//        unscheduleAllEvents(scheduler, entity);
//
//        addEntity(world, miner);
//        scheduleActions(miner, scheduler, world, imageStore);
//    }
//
//
//    public static boolean transformPlant( Entity entity,
//                                          WorldModel world,
//                                          EventScheduler scheduler,
//                                          ImageStore imageStore)
//    {
//        if (entity instanceof Tree)
//        {
//            return transformTree(entity, world, scheduler, imageStore);
//        }
//        else if (entity instanceof Sapling)
//        {
//            return transformSapling(entity, world, scheduler, imageStore);
//        }
//        else
//        {
//            throw new UnsupportedOperationException(
//                    String.format("transformPlant not supported for %s", entity));
//        }
//    }
//
//    public static boolean transformTree(
//            Entity entity,
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        if (((HasHealth) entity).getHealth() <= 0) {
//            Entity stump = createStump(entity.getId(),
//                    entity.getPosition(),
//                    getImageList(imageStore, STUMP_KEY));
//
//            removeEntity(world, entity);
//            unscheduleAllEvents(scheduler, entity);
//
//            addEntity(world, stump);
//
//            return true;
//        }
//
//        return false;
//    }
//
//    public static boolean transformSapling(
//            Entity entity,
//            WorldModel world,
//            EventScheduler scheduler,
//            ImageStore imageStore)
//    {
//        if (((HasHealth) entity).getHealth() <= 0) {
//            Entity stump = createStump(entity.getId(),
//                    entity.getPosition(),
//                    getImageList(imageStore, STUMP_KEY));
//
//            removeEntity(world, entity);
//            unscheduleAllEvents(scheduler, entity);
//
//            addEntity(world, stump);
//
//            return true;
//        }
//        else if (((HasHealth) entity).getHealth() >= ((Sapling) entity).getHealthLimit())
//        {
//            Entity tree = createTree("tree_" + entity.getId(),
//                    entity.getPosition(),
//                    getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN),
//                    getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN),
//                    getNumFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN),
//                    getImageList(imageStore, TREE_KEY));
//
//            removeEntity(world, entity);
//            unscheduleAllEvents(scheduler, entity);
//
//            addEntity(world, tree);
//            scheduleActions(tree, scheduler, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }
//
//    public static boolean moveToFairy(
//            Fairy fairy,
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (adjacent(fairy.getPosition(), target.getPosition())) {
//            removeEntity(world, target);
//            unscheduleAllEvents(scheduler, target);
//            return true;
//        }
//        else {
//            Point nextPos = fairy.nextPosition(world, target.getPosition());
//
//            if (!fairy.getPosition().equals(nextPos)) {
//                Optional<Entity> occupant = getOccupant(world, nextPos);
//                if (occupant.isPresent()) {
//                    unscheduleAllEvents(scheduler, occupant.get());
//                }
//
//                moveEntity(world, fairy, nextPos);
//            }
//            return false;
//        }
//    }
//
//    public static boolean moveToNotFull(
//            Resourceful dude,
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (adjacent(dude.getPosition(), target.getPosition())) {
//            ((Resourceful)dude).setResourceCount(1);
//            ((HasHealth)target).setHealth(-1);
//            return true;
//        }
//        else {
//            Point nextPos = dude.nextPosition(world, target.getPosition());
//
//            if (!dude.getPosition().equals(nextPos)) {
//                Optional<Entity> occupant = getOccupant(world, nextPos);
//                if (occupant.isPresent()) {
//                    unscheduleAllEvents(scheduler, occupant.get());
//                }
//
//                moveEntity(world, dude, nextPos);
//            }
//            return false;
//        }
//    }
//
//    public static boolean moveToFull(
//            Resourceful dude,
//            WorldModel world,
//            Entity target,
//            EventScheduler scheduler)
//    {
//        if (adjacent(dude.getPosition(), target.getPosition())) {
//            return true;
//        }
//        else {
//            Point nextPos = dude.nextPosition(world, target.getPosition());
//
//            if (!dude.getPosition().equals(nextPos)) {
//                Optional<Entity> occupant = getOccupant(world, nextPos);
//                if (occupant.isPresent()) {
//                    unscheduleAllEvents(scheduler, occupant.get());
//                }
//
//                moveEntity(world, dude, nextPos);
//            }
//            return false;
//        }
//    }
//
////    public static Point nextPositionFairy(
////            Entity entity, WorldModel world, Point destPos)
////    {
////        int horiz = Integer.signum(destPos.getX() - entity.getPosition().getX());
////        Point newPos = new Point(entity.getPosition().getX() + horiz, entity.getPosition().getY());
////
////        if (horiz == 0 || isOccupied(world, newPos)) {
////            int vert = Integer.signum(destPos.getY() - entity.getPosition().getY());
////            newPos = new Point(entity.getPosition().getX(), entity.getPosition().getY() + vert);
////
////            if (vert == 0 || isOccupied(world, newPos)) {
////                newPos = entity.getPosition();
////            }
////        }
////
////        return newPos;
////    }
//
////    public static Point nextPositionDude(
////            Entity entity, WorldModel world, Point destPos)
////    {
////        int horiz = Integer.signum(destPos.getX() - entity.getPosition().getX());
////        Point newPos = new Point(entity.getPosition().getX() + horiz, entity.getPosition().getY());
////
////        if (horiz == 0 || isOccupied(world, newPos) && !(getOccupancyCell(world, newPos) instanceof Stump)) {
////            int vert = Integer.signum(destPos.getY() - entity.getPosition().getY());
////            newPos = new Point(entity.getPosition().getX(), entity.getPosition().getY() + vert);
////
////            if (vert == 0 || isOccupied(world, newPos) &&  !(getOccupancyCell(world, newPos) instanceof Stump)) {
////                newPos = entity.getPosition();
////            }
////        }
////
////        return newPos;
////    }
//
//
//    public static boolean adjacent(Point p1, Point p2) {
//        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY()
//                && Math.abs(p1.getX() - p2.getX()) == 1);
//    }
//
    public static int getNumFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);
    }
//
//
//    public static void scheduleEvent(
//            EventScheduler scheduler,
//            Entity entity,
//            Action action,
//            long afterPeriod)
//    {
//        long time = System.currentTimeMillis() + (long)(afterPeriod
//                * scheduler.getTimeScale());
//        Event event = new Event(action, time, entity);
//
//        scheduler.getEventQueue().add(event);
//
//        // update list of pending events for the given entity
//        List<Event> pending = scheduler.getPendingEvents().getOrDefault(entity,
//                new LinkedList<>());
//        pending.add(event);
//        scheduler.getPendingEvents().put(entity, pending);
//    }
//
//    public static void unscheduleAllEvents(
//            EventScheduler scheduler, Entity entity)
//    {
//        List<Event> pending = scheduler.getPendingEvents().remove(entity);
//
//        if (pending != null) {
//            for (Event event : pending) {
//                scheduler.getEventQueue().remove(event);
//            }
//        }
//    }
//
//    public static void removePendingEvent(
//            EventScheduler scheduler, Event event)
//    {
//        List<Event> pending = scheduler.getPendingEvents().get(event.getEntity());
//
//        if (pending != null) {
//            pending.remove(event);
//        }
//    }
//
//    public static void updateOnTime(EventScheduler scheduler, long time) {
//        while (!scheduler.getEventQueue().isEmpty()
//                && scheduler.getEventQueue().peek().getTime() < time) {
//            Event next = scheduler.getEventQueue().poll();
//
//            removePendingEvent(scheduler, next);
//
//            executeAction(next.getAction(), scheduler);
//        }
//    }
//
//    public static List<PImage> getImageList(ImageStore imageStore, String key) {
//        return imageStore.getImages().getOrDefault(key, imageStore.getDefaultImages());
//    }
//
//    public static void loadImages(
//            Scanner in, ImageStore imageStore, PApplet screen)
//    {
//        int lineNumber = 0;
//        while (in.hasNextLine()) {
//            try {
//                processImageLine(imageStore.getImages(), in.nextLine(), screen);
//            }
//            catch (NumberFormatException e) {
//                System.out.println(
//                        String.format("Image format error on line %d",
//                                lineNumber));
//            }
//            lineNumber++;
//        }
//    }
//
//    public static void processImageLine(
//            Map<String, List<PImage>> images, String line, PApplet screen)
//    {
//        String[] attrs = line.split("\\s");
//        if (attrs.length >= 2) {
//            String key = attrs[0];
//            PImage img = screen.loadImage(attrs[1]);
//            if (img != null && img.width != -1) {
//                List<PImage> imgs = getImages(images, key);
//                imgs.add(img);
//
//                if (attrs.length >= KEYED_IMAGE_MIN) {
//                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
//                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
//                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
//                    setAlpha(img, screen.color(r, g, b), 0);
//                }
//            }
//        }
//    }
//
//    public static List<PImage> getImages(
//            Map<String, List<PImage>> images, String key)
//    {
//        List<PImage> imgs = images.get(key);
//        if (imgs == null) {
//            imgs = new LinkedList<>();
//            images.put(key, imgs);
//        }
//        return imgs;
//    }
//
//    /*
//      Called with color for which alpha should be set and alpha value.
//      setAlpha(img, color(255, 255, 255), 0));
//    */
//    public static void setAlpha(PImage img, int maskColor, int alpha) {
//        int alphaValue = alpha << 24;
//        int nonAlpha = maskColor & COLOR_MASK;
//        img.format = PApplet.ARGB;
//        img.loadPixels();
//        for (int i = 0; i < img.pixels.length; i++) {
//            if ((img.pixels[i] & COLOR_MASK) == nonAlpha) {
//                img.pixels[i] = alphaValue | nonAlpha;
//            }
//        }
//        img.updatePixels();
//    }
//
//    public static void shift(Viewport viewport, int col, int row) {
//        viewport.setCol(col);
//        viewport.setRow(row);
//    }
//
//    public static boolean contains(Viewport viewport, Point p) {
//        return p.getY() >= viewport.getRow() && p.getY() < viewport.getRow() + viewport.getNumRows()
//                && p.getX() >= viewport.getCol() && p.getX() < viewport.getCol() + viewport.getNumCols();
//    }
//
//    public static void load(
//            Scanner in, WorldModel world, ImageStore imageStore)
//    {
//        int lineNumber = 0;
//        while (in.hasNextLine()) {
//            try {
//                if (!processLine(in.nextLine(), world, imageStore)) {
//                    System.err.println(String.format("invalid entry on line %d",
//                            lineNumber));
//                }
//            }
//            catch (NumberFormatException e) {
//                System.err.println(
//                        String.format("invalid entry on line %d", lineNumber));
//            }
//            catch (IllegalArgumentException e) {
//                System.err.println(
//                        String.format("issue on line %d: %s", lineNumber,
//                                e.getMessage()));
//            }
//            lineNumber++;
//        }
//    }
//
//    public static boolean processLine(
//            String line, WorldModel world, ImageStore imageStore)
//    {
//        String[] properties = line.split("\\s");
//        if (properties.length > 0) {
//            switch (properties[PROPERTY_KEY]) {
//                case BGND_KEY:
//                    return parseBackground(properties, world, imageStore);
//                case DUDE_KEY:
//                    return parseDude(properties, world, imageStore);
//                case OBSTACLE_KEY:
//                    return parseObstacle(properties, world, imageStore);
//                case FAIRY_KEY:
//                    return parseFairy(properties, world, imageStore);
//                case HOUSE_KEY:
//                    return parseHouse(properties, world, imageStore);
//                case TREE_KEY:
//                    return parseTree(properties, world, imageStore);
//                case SAPLING_KEY:
//                    return parseSapling(properties, world, imageStore);
//            }
//        }
//
//        return false;
//    }
//
//    public static boolean parseBackground(
//            String[] properties, WorldModel world, ImageStore imageStore)
//    {
//        if (properties.length == BGND_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
//                    Integer.parseInt(properties[BGND_ROW]));
//            String id = properties[BGND_ID];
//            setBackground(world, pt,
//                    new Background(id, getImageList(imageStore, id)));
//        }
//
//        return properties.length == BGND_NUM_PROPERTIES;
//    }
//
//    public static boolean parseSapling(
//            String[] properties, WorldModel world, ImageStore imageStore)
//    {
//        if (properties.length == SAPLING_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[SAPLING_COL]),
//                    Integer.parseInt(properties[SAPLING_ROW]));
//            String id = properties[SAPLING_ID];
//            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
//            Entity entity = new Sapling(id, pt, getImageList(imageStore, SAPLING_KEY), health);
//            tryAddEntity(world, entity);
//        }
//
//        return properties.length == SAPLING_NUM_PROPERTIES;
//    }
//
//    public static boolean parseDude(
//            String[] properties, WorldModel world, ImageStore imageStore)
//    {
//        if (properties.length == DUDE_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[DUDE_COL]),
//                    Integer.parseInt(properties[DUDE_ROW]));
//            Entity entity = createDudeNotFull(properties[DUDE_ID],
//                    pt,
//                    Integer.parseInt(properties[DUDE_ACTION_PERIOD]),
//                    Integer.parseInt(properties[DUDE_ANIMATION_PERIOD]),
//                    Integer.parseInt(properties[DUDE_LIMIT]),
//                    getImageList(imageStore, DUDE_KEY));
//            tryAddEntity(world, entity);
//        }
//
//        return properties.length == DUDE_NUM_PROPERTIES;
//    }
//
//    public static boolean parseFairy(
//            String[] properties, WorldModel world, ImageStore imageStore)
//    {
//        if (properties.length == FAIRY_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[FAIRY_COL]),
//                    Integer.parseInt(properties[FAIRY_ROW]));
//            Entity entity = createFairy(properties[FAIRY_ID],
//                    pt,
//                    Integer.parseInt(properties[FAIRY_ACTION_PERIOD]),
//                    Integer.parseInt(properties[FAIRY_ANIMATION_PERIOD]),
//                    getImageList(imageStore, FAIRY_KEY));
//            tryAddEntity(world, entity);
//        }
//
//        return properties.length == FAIRY_NUM_PROPERTIES;
//    }
//
//    public static boolean parseTree(
//            String[] properties, WorldModel world, ImageStore imageStore)
//    {
//        if (properties.length == TREE_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[TREE_COL]),
//                    Integer.parseInt(properties[TREE_ROW]));
//            Entity entity = createTree(properties[TREE_ID],
//                    pt,
//                    Integer.parseInt(properties[TREE_ACTION_PERIOD]),
//                    Integer.parseInt(properties[TREE_ANIMATION_PERIOD]),
//                    Integer.parseInt(properties[TREE_HEALTH]),
//                    getImageList(imageStore, TREE_KEY));
//            tryAddEntity(world, entity);
//        }
//
//        return properties.length == TREE_NUM_PROPERTIES;
//    }
//
//    public static boolean parseObstacle(
//            String[] properties, WorldModel world, ImageStore imageStore)
//    {
//        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]),
//                    Integer.parseInt(properties[OBSTACLE_ROW]));
//            Entity entity = createObstacle(properties[OBSTACLE_ID], pt,
//                    Integer.parseInt(properties[OBSTACLE_ANIMATION_PERIOD]),
//                    getImageList(imageStore,
//                            OBSTACLE_KEY));
//            tryAddEntity(world, entity);
//        }
//
//        return properties.length == OBSTACLE_NUM_PROPERTIES;
//    }
//
//    public static boolean parseHouse(
//            String[] properties, WorldModel world, ImageStore imageStore)
//    {
//        if (properties.length == HOUSE_NUM_PROPERTIES) {
//            Point pt = new Point(Integer.parseInt(properties[HOUSE_COL]),
//                    Integer.parseInt(properties[HOUSE_ROW]));
//            Entity entity = createHouse(properties[HOUSE_ID], pt,
//                    getImageList(imageStore,
//                            HOUSE_KEY));
//            tryAddEntity(world, entity);
//        }
//
//        return properties.length == HOUSE_NUM_PROPERTIES;
//    }
//
//    public static void tryAddEntity(WorldModel world, Entity entity) {
//        if (isOccupied(world, entity.getPosition())) {
//            // arguably the wrong type of exception, but we are not
//            // defining our own exceptions yet
//            throw new IllegalArgumentException("position occupied");
//        }
//
//        addEntity(world, entity);
//    }
//
//    public static boolean withinBounds(WorldModel world, Point pos) {
//        return pos.getY() >= 0 && pos.getY() < world.getNumRows() && pos.getX() >= 0
//                && pos.getX() < world.getNumCols();
//    }
//
//    public static boolean isOccupied(WorldModel world, Point pos) {
//        return withinBounds(world, pos) && getOccupancyCell(world, pos) != null;
//    }
//
//    public static Optional<Entity> nearestEntity(
//            List<Entity> entities, Point pos)
//    {
//        if (entities.isEmpty()) {
//            return Optional.empty();
//        }
//        else {
//            Entity nearest = entities.get(0);
//            int nearestDistance = distanceSquared(nearest.getPosition(), pos);
//
//            for (Entity other : entities) {
//                int otherDistance = distanceSquared(other.getPosition(), pos);
//
//                if (otherDistance < nearestDistance) {
//                    nearest = other;
//                    nearestDistance = otherDistance;
//                }
//            }
//
//            return Optional.of(nearest);
//        }
//    }
//
//    public static int distanceSquared(Point p1, Point p2) {
//        int deltaX = p1.getX() - p2.getX();
//        int deltaY = p1.getY() - p2.getY();
//
//        return deltaX * deltaX + deltaY * deltaY;
//    }
//
//    public static Optional<Entity> findNearest(
//            WorldModel world, Point pos, List<Class> kinds)
//    {
//        List<Entity> ofType = new LinkedList<>();
//        for (Class kind: kinds)
//        {
//            for (Entity entity : world.getEntities()) {
//                if (kind.isInstance(entity)) {
//                    ofType.add(entity);
//                }
//            }
//        }
//
//        return nearestEntity(ofType, pos);
//    }
//
//    /*
//       Assumes that there is no entity currently occupying the
//       intended destination cell.
//    */
//    public static void addEntity(WorldModel world, Entity entity) {
//        if (withinBounds(world, entity.getPosition())) {
//            setOccupancyCell(world, entity.getPosition(), entity);
//            world.getEntities().add(entity);
//        }
//    }
//
//    public static void moveEntity(WorldModel world, Entity entity, Point pos) {
//        Point oldPos = entity.getPosition();
//        if (withinBounds(world, pos) && !pos.equals(oldPos)) {
//            setOccupancyCell(world, oldPos, null);
//            removeEntityAt(world, pos);
//            setOccupancyCell(world, pos, entity);
//            entity.setPosition(pos);
//        }
//    }
//
//    public static void removeEntity(WorldModel world, Entity entity) {
//        removeEntityAt(world, entity.getPosition());
//    }
//
//    public static void removeEntityAt(WorldModel world, Point pos) {
//        if (withinBounds(world, pos) && getOccupancyCell(world, pos) != null) {
//            Entity entity = getOccupancyCell(world, pos);
//
//            /* This moves the entity just outside of the grid for
//             * debugging purposes. */
//            entity.setPosition(new Point(-1, -1));
//            world.getEntities().remove(entity);
//            setOccupancyCell(world, pos, null);
//        }
//    }
//
//    public static Optional<PImage> getBackgroundImage(
//            WorldModel world, Point pos)
//    {
//        if (withinBounds(world, pos)) {
//            return Optional.of(getCurrentImage(getBackgroundCell(world, pos)));
//        }
//        else {
//            return Optional.empty();
//        }
//    }
//
//    public static void setBackground(
//            WorldModel world, Point pos, Background background)
//    {
//        if (withinBounds(world, pos)) {
//            setBackgroundCell(world, pos, background);
//        }
//    }
//
//    public static Optional<Entity> getOccupant(WorldModel world, Point pos) {
//        if (isOccupied(world, pos)) {
//            return Optional.of(getOccupancyCell(world, pos));
//        }
//        else {
//            return Optional.empty();
//        }
//    }
//
//    public static Entity getOccupancyCell(WorldModel world, Point pos) {
//        return world.getOccupancy()[pos.getY()][pos.getX()];
//    }
//
//    public static void setOccupancyCell(
//            WorldModel world, Point pos, Entity entity)
//    {
//        world.getOccupancy()[pos.getY()][pos.getX()] = entity;
//    }
//
//    public static Background getBackgroundCell(WorldModel world, Point pos) {
//        return world.getBackground()[pos.getY()][pos.getX()];
//    }
//
//    public static void setBackgroundCell(
//            WorldModel world, Point pos, Background background)
//    {
//        world.getBackground()[pos.getY()][pos.getX()] = background;
//    }
//
//    public static Point viewportToWorld(Viewport viewport, int col, int row) {
//        return new Point(col + viewport.getCol(), row + viewport.getRow());
//    }
//
//    public static Point worldToViewport(Viewport viewport, int col, int row) {
//        return new Point(col - viewport.getCol(), row - viewport.getRow());
//    }
//
//    public static int clamp(int value, int low, int high) {
//        return Math.min(high, Math.max(value, low));
//    }
//
//    public static void shiftView(WorldView view, int colDelta, int rowDelta) {
//        int newCol = clamp(view.getViewport().getCol() + colDelta, 0,
//                view.getWorld().getNumCols() - view.getViewport().getNumCols());
//        int newRow = clamp(view.getViewport().getRow() + rowDelta, 0,
//                view.getWorld().getNumRows() - view.getViewport().getNumRows());
//
//        shift(view.getViewport(), newCol, newRow);
//    }
//
//    public static void drawBackground(WorldView view) {
//        for (int row = 0; row < view.getViewport().getNumRows(); row++) {
//            for (int col = 0; col < view.getViewport().getNumCols(); col++) {
//                Point worldPoint = viewportToWorld(view.getViewport(), col, row);
//                Optional<PImage> image =
//                        getBackgroundImage(view.getWorld(), worldPoint);
//                if (image.isPresent()) {
//                    view.getScreen().image(image.get(), col * view.getTileWidth(),
//                            row * view.getTileHeight());
//                }
//            }
//        }
//    }
//
//    public static void drawEntities(WorldView view) {
//        for (Entity entity : view.getWorld().getEntities()) {
//            Point pos = entity.getPosition();
//
//            if (contains(view.getViewport(), pos)) {
//                Point viewPoint = worldToViewport(view.getViewport(), pos.getX(), pos.getY());
//                view.getScreen().image(getCurrentImage(entity),
//                        viewPoint.getX() * view.getTileWidth(),
//                        viewPoint.getY() * view.getTileHeight());
//            }
//        }
//    }
//
//    public static void drawViewport(WorldView view) {
//        drawBackground(view);
//        drawEntities(view);
//    }
//
//    public static Action createAnimationAction(Entity entity, int repeatCount) {
//        return new AnimationAction(entity,
//                repeatCount);
//    }
//
//    public static Action createActivityAction(
//            Entity entity, WorldModel world, ImageStore imageStore)
//    {
//        return new ActivityAction(entity, world, imageStore);
//    }
//
//    public static Entity createHouse(
//            String id, Point position, List<PImage> images)
//    {
//        return new House(id, position, images);
//    }
//
//    public static Entity createObstacle(
//            String id, Point position, int animationPeriod, List<PImage> images)
//    {
//        return new Obstacle(id, position, images,
//                animationPeriod);
//    }
//
//    public static Entity createTree(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int health,
//            List<PImage> images)
//    {
//        return new Tree(id, position, images, animationPeriod,
//                actionPeriod, health);
//    }
//
//    public static Entity createStump(
//            String id,
//            Point position,
//            List<PImage> images)
//    {
//        return new Stump(id, position, images);
//    }
//
//    // health starts at 0 and builds up until ready to convert to Tree
//    public static Entity createSapling(
//            String id,
//            Point position,
//            List<PImage> images)
//    {
//        return new Sapling(id, position, images, 0);
//    }
//
//    public static Entity createFairy(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            List<PImage> images)
//    {
//        return new Fairy(id, position, images, animationPeriod,
//                actionPeriod);
//    }
//
//    // need resource count, though it always starts at 0
//    public static Entity createDudeNotFull(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int resourceLimit,
//            List<PImage> images)
//    {
//        return new DudeFull(id, position, images, animationPeriod, actionPeriod, 0,
//                  resourceLimit);
//    }
//
//    // don't technically need resource count ... full
//    public static Entity createDudeFull(
//            String id,
//            Point position,
//            int actionPeriod,
//            int animationPeriod,
//            int resourceLimit,
//            List<PImage> images) {
//        return new DudeFull(id, position, images, animationPeriod, actionPeriod, resourceLimit, 0);
//    }
}
