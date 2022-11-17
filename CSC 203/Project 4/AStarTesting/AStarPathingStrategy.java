import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy {
    public List<Point> computePath(Point start, Point end, //step 1
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {

        Node currentNode = new Node(null, start, end);
        List<Node> openList = new ArrayList<>();
        List<Node> closedList = new ArrayList<>();
        Node currentNeighbor = null;
        boolean contained = false;
        Node a = null; //will be set to the node already contained
        while (!currentNode.getP().equals(end)) {
            openList.add(currentNode); //step 2
            List<Point> neighbors = potentialNeighbors.apply(start).filter(canPassThrough).collect(Collectors.toList());
            for (int i = 0; i < neighbors.size(); i++) {
                currentNeighbor = new Node(currentNode, neighbors.get(i), end); //3b handled here
                for (int j = 0; j < openList.size(); i++) {
                    if (currentNeighbor.equals(openList.get(j).getP())) { //if any Node in openList has the same point
                        contained = true;
                        a = openList.get(j);
                        break;
                    }
                }
                if (!contained) { openList.add(currentNeighbor); } //3a
                else {
                    if (currentNeighbor.getG() < a.getG()) { //3c
                        openList.remove(a);
                        openList.add(currentNeighbor);
                    }
                }
            }
            closedList.add(currentNode); //4
            openList.remove(currentNode);
            Collections.sort(openList, new Comparator<Node>(){
                public int compare(Node o1, Node o2){
                    if(o1.getF() == o2.getF()){ return 0; }
                    return o1.getF() < o2.getF() ? -1 : 1;
                }
            });
            currentNode = openList.get(0); //5
        } //6 repeat
        List<Point> pathPoints = new LinkedList<>();
        Node p = currentNode;
        while (p.getPrev() != null) {
            pathPoints.add(0, p.getP());
            p = p.getPrev();
        }
        return pathPoints;


//        List<Node> pathNodes = new LinkedList<>();
//        List<Point> neighbors = potentialNeighbors.apply(start)
//                .filter(canPassThrough).collect(Collectors.toList());
//        Node currentNeighbor = null;
//        Node nextNode = new Node(neighbors.get(0), end);
//        Comparator<Node> byF = (Node o1, Node o2)-> o1.getF().compareTo(o2.getF());
//        PriorityQueue<Node> pQueue = new PriorityQueue<>();
//        //List<Node> openList = new ArrayList<>();
//        List<Node> closedList = new ArrayList<>();
//
//        for (int i = 0; i < neighbors.size(); i++) {
//            currentNeighbor = new Node(neighbors.get(i), end);
//            openList.add(currentNeighbor);
//            pQueue.add(currentNeighbor);
//        }
    }
}
