public interface Transformer {
    boolean transform(WorldModel world,
                             EventScheduler scheduler,
                             ImageStore imageStore);
}
