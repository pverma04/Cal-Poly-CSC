import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        LinkedList<Point> path = new LinkedList<>();
        //List<Node> openList = new LinkedList<>();
        HashMap<Point, Node> openMap = new HashMap<>();
        Node startNode = new Node(start, 0, start.manhattan(end), start.manhattan(end),null);
        //openList.add(startNode);
        openMap.put(startNode.point, startNode);
        List<Node> closedList = new ArrayList<>();
        //List<Node> openMapToList = new ArrayList<Node>(openMap.values());

        Node pathNode = computePathHelp(start, end, canPassThrough, withinReach,
                potentialNeighbors, openMap, closedList, start, null);


        if(pathNode != null) {
            while (!pathNode.equals(startNode)) {
                path.addFirst(pathNode.point);
                pathNode = pathNode.prior;
            }
        }

        return path;
    }

    private Node computePathHelp(Point start, Point end,
                                 Predicate<Point> canPassThrough,
                                 BiPredicate<Point, Point> withinReach,
                                 Function<Point, Stream<Point>> potentialNeighbors,
                                 //List<Node> openList,
                                 HashMap<Point, Node> openMap,
                                 List<Node> closedList,
                                 Point curr,
                                 Node prior){
        double g = curr.distance(start);

        int h = curr.manhattan(end);
        Node currNode = new Node(curr, g, h, g+h, prior);

        List<Node> neighbors = potentialNeighbors.apply(curr)
                .filter(canPassThrough)
                .map(n -> new Node(n, currNode.g + n.distance(curr), n.manhattan(end),
                        currNode.g + n.distance(curr) + n.manhattan(end), currNode))
                .filter(n -> !closedList.contains(n))
//                .filter(n -> !openList.contains(n))
                .collect(Collectors.toList());


        for (Node neighbor : neighbors) {

            if(withinReach.test(neighbor.point, end)){
                return neighbor;
            }
            //if(openList.contains(neighbor)){
            if(openMap.containsValue(neighbor)){
                //int idx = openList.indexOf(neighbor);
//                if(neighbor.g > openList.get(idx).g){
//                    neighbor.g = openList.get(idx).g;
//                    neighbor.f = neighbor.g + neighbor.h;
//                    neighbor.prior = openList.get(idx).prior;
//                }
                if(neighbor.g > openMap.get(neighbor.point).g){
                    neighbor.g = openMap.get(neighbor.point).g;
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.prior = openMap.get(neighbor.point).prior;
                }
                openMap.remove(neighbor.point);
            }
            openMap.put(neighbor.point, neighbor);
        }

        openMap.remove(currNode.point);
        closedList.add(currNode);

        if(openMap.isEmpty()){
            return null;
        }

//        List<Integer> keys = openList.keySet().stream().collect(Collectors.toList());
//        int min_key = keys.get(0);
//        double min = openList.get(min_key).f;
//        for(int i = 1; i < keys.size(); i++){
//            if(openList.get(keys.get(i)).f < min){
//                min = openList.get(keys.get(i)).f;
//                min_key = keys.get(i);
//            }
//        }

        List<Node> nodeList = openMap.values().stream().toList();
        double min = nodeList.get(0).f;
        int min_idx = 0;
        for(int i = 1; i < nodeList.size(); i++){
            if(nodeList.get(i).f < min){
                min = nodeList.get(i).f;
                min_idx = i;
            }
        }

        return computePathHelp(start, end, canPassThrough, withinReach, potentialNeighbors,
                openMap, closedList, nodeList.get(min_idx).point,
                nodeList.get(min_idx).prior);
    }
}

