import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dijkstra implements PathingStrategy{
    @Override
    public List<Point> computePath(Point start,
                                   Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Vertex> visited = new ArrayList<>();
        List<Vertex> unvisited = new ArrayList<>();
        Vertex startVtx = new Vertex(start, 0, null);
        unvisited.add(startVtx);
        List<Vertex> vtxList = potentialNeighbors.apply(start)
                .filter(canPassThrough)
                .map(p -> new Vertex(p, Double.POSITIVE_INFINITY, startVtx)).toList();
        unvisited.addAll(vtxList);

        System.out.println("INITIAL UNVISITED:");
        System.out.print("[");
        for(int j = 0; j < unvisited.size(); j++)
            System.out.print(unvisited.get(j).point + " ");
        System.out.println("]\n");

        LinkedList<Point> path = new LinkedList<>();
        Vertex pathVtx = computePathHelp(start,
                end,
                canPassThrough,
                withinReach,
                potentialNeighbors,
                startVtx,
                visited,
                unvisited);
        while(pathVtx.prior != null){
            path.addFirst(pathVtx.point);
            pathVtx = pathVtx.prior;
        }
        return path;
    }

    private Vertex computePathHelp(Point start,
                                   Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors,
                                   Vertex curr,
                                   List<Vertex> visited,
                                   List<Vertex> unvisited){
        //list of all neighboring vertices that haven't been visited
        List<Vertex> vtxList = potentialNeighbors.apply(curr.point)
                .filter(canPassThrough)
                .map(p -> new Vertex(p, p.distance(curr.point) + curr.weight, curr))
                .toList();


        for(Vertex vtx: vtxList){
            if(withinReach.test(vtx.point, end))
                return vtx;
            if(unvisited.contains(vtx)){
                int idx = unvisited.indexOf(vtx);
                Vertex unvisitedVtx = unvisited.get(idx);
                if(vtx.weight < unvisitedVtx.weight){
                    unvisited.remove(idx);
                    unvisited.add(idx, vtx);
                }
            }
            else {
                unvisited.add(vtx);
//                System.out.println("UPDATED UNVISITED:");
//                System.out.print("[");
//                for(int j = 0; j < unvisited.size(); j++)
//                    System.out.print(unvisited.get(j).point + " ");
//                System.out.println("]\n");
            }

        }
        System.out.println("CURRENT VERTEX: " + curr.point);
        unvisited.remove(curr);
        visited.add(curr);

        double min = unvisited.get(0).weight;
        int min_idx = 0;
        for(int i = 1; i < unvisited.size(); i++){
            if(unvisited.get(i).weight < min){
                min = unvisited.get(i).weight;
                min_idx = i;
            }
        }

        return computePathHelp(start,
                end,
                canPassThrough,
                withinReach,
                potentialNeighbors,
                unvisited.get(min_idx),
                visited,
                unvisited);


    }
}


