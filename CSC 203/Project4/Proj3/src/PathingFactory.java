public class PathingFactory {
    public static PathingStrategy getStrategy(String type){
        if("AStar".equalsIgnoreCase(type)) return new AStarPathingStrategy();
        else if("Dijkstra".equalsIgnoreCase(type)) return new DijkstraPathing();
        else if("Single Step".equalsIgnoreCase(type)) return new SingleStepPathingStrategy();
        return null;
    }
}
