import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Keeps track of events that have been scheduled.
 */
public final class EventScheduler
{
    private PriorityQueue<Event> eventQueue;
    private Map<Entity, List<Event>> pendingEvents;
    private double timeScale;

    public EventScheduler(double timeScale) {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.timeScale = timeScale;
        
    }
    public PriorityQueue<Event> getEventQueue()
    {
        return this.eventQueue;
    }
    public Map<Entity, List<Event>> getPendingEvents()
    {
        return this.pendingEvents;
    }
    public double getTimeScale()
    {
        return this.timeScale;
    }
    private  void removePendingEvent(Event event) {
        List<Event> pending = this.getPendingEvents().get(event.getEntity());

        if (pending != null) {
            pending.remove(event);
        }
    }

    public void updateOnTime( long time) {
        while (!this.getEventQueue().isEmpty()
                && this.getEventQueue().peek().getTime() < time) {
            Event next = this.getEventQueue().poll();

            removePendingEvent(next);

            next.getAction().executeAction(this);
        }
    }

    public void scheduleEvent(Entity entity,Action action,long afterPeriod) {
        long time = System.currentTimeMillis() + (long)(afterPeriod * this.getTimeScale());
        Event event = new Event(action, time, entity);

        this.getEventQueue().add(event);

        // update list of pending events for the given entity
        List<Event> pending = this.getPendingEvents().getOrDefault(entity,
                new LinkedList<>());
        pending.add(event);
        this.getPendingEvents().put(entity, pending);
    }


    public  void unscheduleAllEvents(Entity entity) {
        List<Event> pending = this.getPendingEvents().remove(entity);
        if (pending != null) {
            for (Event event : pending) {
                this.getEventQueue().remove(event);
            }
        }
    }


    /*
    public  void scheduleActions(Entity entity, WorldModel world, ImageStore imageStore) {
        switch (entity.getEntityKind()) {
            case DUDE_FULL:
                this.scheduleEvent(entity,entity.createActivityAction(world, imageStore),entity.getActionPeriod());
                this.scheduleEvent(entity,entity.createAnimationAction(0),entity.getAnimationPeriod());
                break;

            case DUDE_NOT_FULL:
                this.scheduleEvent(entity,entity.createActivityAction(world, imageStore),entity.getActionPeriod());
                this.scheduleEvent(entity,entity.createAnimationAction(0),entity.getAnimationPeriod());
                break;

            case OBSTACLE:
                this.scheduleEvent(entity,entity.createAnimationAction(0),entity.getAnimationPeriod());
                break;

            case FAIRY:
                this.scheduleEvent(entity,entity.createActivityAction(world, imageStore),entity.getActionPeriod());
                this.scheduleEvent(entity,entity.createAnimationAction(0),entity.getAnimationPeriod());
                break;

            case SAPLING:
                this.scheduleEvent(entity,entity.createActivityAction(world, imageStore),entity.getActionPeriod());
                this.scheduleEvent(entity,entity.createAnimationAction(0),entity.getAnimationPeriod());
                break;

            case TREE:
                this.scheduleEvent(entity,entity.createActivityAction(world, imageStore),entity.getActionPeriod());
                this.scheduleEvent(entity,entity.createAnimationAction(0),entity.getAnimationPeriod());
                break;
            default:
        }
    }
     */
}
