import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.LinkedList;


/**
 * Keeps track of events that have been scheduled.
 */
public final class EventScheduler
{
    public PriorityQueue<Event> eventQueue;
    public Map<Entity, List<Event>> pendingEvents;
    public double timeScale;

    public EventScheduler(double timeScale) {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.timeScale = timeScale;
    }

    public void scheduleEvent(Entity entity, Action action, long afterPeriod) {
        long time = System.currentTimeMillis() + (long)(afterPeriod
                * this.timeScale);
        Event event = new Event(action, time, entity);
        this.eventQueue.add(event);

        // update list of pending events for the given entity
        List<Event> pending = this.pendingEvents.getOrDefault(entity,
                new LinkedList<>());
        pending.add(event);
        this.pendingEvents.put(entity, pending);
    }
    public void unscheduleAllEvents(Entity entity) {
        List<Event> pending = this.pendingEvents.remove(entity);

        if (pending != null) {
            for (Event event : pending) {
                this.eventQueue.remove(event);
            }
        }
    }

    public void removePendingEvent(Event event) {
        List<Event> pending = this.pendingEvents.get(event.getEntity());

        if (pending != null) {
            pending.remove(event);
        }
    }
    public void updateOnTime(long time) {
        while (!this.eventQueue.isEmpty()
                && this.eventQueue.peek().getTime() < time) {
            Event next = this.eventQueue.poll();

            this.removePendingEvent(next);

            next.getAction().executeAction(this);
        }
    }
    public void scheduleActions(Entity entity, WorldModel world, ImageStore imageStore) {
        switch (entity.getEntityKind()) {
            case DUDE_FULL:
                this.scheduleEvent(entity, Functions.createActivityAction(entity, world, imageStore), entity.getActionPeriod());
                this.scheduleEvent(entity, Functions.createAnimationAction(entity, 0), entity.getAnimationPeriod());
                break;

            case DUDE_NOT_FULL:
                this.scheduleEvent(entity, Functions.createActivityAction(entity, world, imageStore), entity.getActionPeriod());
                this.scheduleEvent(entity, Functions.createAnimationAction(entity, 0), entity.getAnimationPeriod());
                break;

            case OBSTACLE:
                this.scheduleEvent(entity, Functions.createAnimationAction(entity, 0), entity.getAnimationPeriod());
                break;

            case FAIRY:
                this.scheduleEvent(entity, Functions.createActivityAction(entity, world, imageStore), entity.getActionPeriod());
                this.scheduleEvent(entity, Functions.createAnimationAction(entity, 0), entity.getAnimationPeriod());
                break;

            case SAPLING:
                this.scheduleEvent(entity, Functions.createActivityAction(entity, world, imageStore), entity.getActionPeriod());
                this.scheduleEvent(entity, Functions.createAnimationAction(entity, 0), entity.getAnimationPeriod());
                break;

            case TREE:
                this.scheduleEvent(entity, Functions.createActivityAction(entity, world, imageStore), entity.getActionPeriod());
                this.scheduleEvent(entity, Functions.createAnimationAction(entity, 0), entity.getAnimationPeriod());
                break;

            default:
        }
    }
}
