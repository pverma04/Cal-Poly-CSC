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
        //HashMap<Point, Node> openMap = new HashMap<>();
        HashMap<Point, Node> closedMap = new HashMap<>();
        PriorityQueue<Node> openList = new PriorityQueue<>(); //only to get the smallest f value Comparator.comparingInt(Node::getF)
        Node currentNode = new Node(null, start, end);
        //openMap.put(start, currentNode);
        //checkingFValue.add(currentNode);
        openList.add(currentNode);
        while (!withinReach.test(currentNode.getP(), end)) {
            List<Point> neighbors = potentialNeighbors.apply(currentNode.getP()).filter(canPassThrough).collect(Collectors.toList());
            for (int i = 0; i < neighbors.size(); i++) {
                if (!closedMap.containsKey(neighbors.get(i))){
                    Node currentNeighbor = new Node(currentNode, neighbors.get(i), end);
                    if (!openList.contains(neighbors.get(i))) {
                        //openMap.put(neighbors.get(i), currentNeighbor);
                        openList.add(currentNeighbor);
                    }
//                    else { //already in openList
//                        if (openMap.get(neighbors.get(i)).getG() > currentNeighbor.getG()) {
//                            openMap.replace(neighbors.get(i), currentNeighbor);
//                        }
//                    }
                }
            }
            //checkingFValue.remove(currentNode);
            closedMap.put(currentNode.getP(), currentNode);
            openList.remove(currentNode);
            currentNode = openList.peek();
        }
        List<Point> pointsPath = new ArrayList<>();
        Node p = currentNode;
        while (p.getPrev() != null) {
            pointsPath.add(0, p.getP());
            p = p.getPrev();
        }
        return pointsPath;
    }
}

    //Node currentNode = new Node(null, start, end);
//        //List<Node> openList = new ArrayList<>();
//        //List<Node> closedList = new ArrayList<>();
//
//        HashMap<Point, Node> openMap = new HashMap<>();
//        PriorityQueue<Node> checkingGValue = new PriorityQueue<>(Comparator.comparingInt(Node::getF)); //only to get the smallest g value
//        HashMap<Point, Node> closedMap = new HashMap<>();
//
//        openMap.put(start, currentNode);
//        checkingGValue.add(currentNode);
//
//        Node currentNeighbor = null;
//        boolean contained = false;
//        Node a = null; //will be set to the node already contained
//        while (!openMap.isEmpty()) { //while (openMap is not empty)
//            if (!currentNode.getP().equals(end)){
////                openMap.put(currentNode.getP(), currentNode); //step 2
////                checkingGValue.add(currentNode);
//                List<Point> neighbors = potentialNeighbors.apply(currentNode.getP()).filter(canPassThrough).collect(Collectors.toList());
//                System.out.println(neighbors.toString());
//                for (int i = 0; i < neighbors.size(); i++) {
//                    currentNeighbor = new Node(currentNode, neighbors.get(i), end); //3b handled here
//                    if (openMap.containsKey(neighbors.get(i))) {
//                        contained = true;
//                        a = openMap.get(neighbors.get(i));
//                    }
//                    if (!contained) { //3a
//                        openMap.put(currentNeighbor.getP(), currentNeighbor);
//                        checkingGValue.add(currentNeighbor);
//                    } else {
//                        if (currentNeighbor.getG() < a.getG()) { //3c
//                            openMap.remove(a.getP());
//                            openMap.put(currentNeighbor.getP(), currentNeighbor);
//                            checkingGValue.remove(a);
//                            checkingGValue.add(currentNeighbor);
//                        }
//                    }
//                }
//                closedMap.put(currentNode.getP(), currentNode); //4
//                openMap.remove(currentNode.getP());
//                checkingGValue.remove(currentNode);
//                currentNode = checkingGValue.peek(); //5
//                System.out.println(currentNode.toString());
//            }
//        } //6 repeat
//        List<Point> pathPoints = new LinkedList<>();
//        Node p = currentNode;
//        while (p.getPrev() != null) {
//            pathPoints.add(0, p.getP());
//            p = p.getPrev();
//        }
//        return pathPoints;






















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