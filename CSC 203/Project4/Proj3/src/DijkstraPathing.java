import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DijkstraPathing implements PathingStrategy{
    @Override
    public List<Point> computePath(Point start,
                                   Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {
        List<Vertex> unvisited = new ArrayList<>();
        List<Vertex> visited = new ArrayList<>();
        Vertex startVtx = new Vertex(start, 0, null);
        unvisited.add(startVtx);
        boolean found = false;
        Vertex pathVtx = startVtx;
        while(!found){
            if(!unvisited.isEmpty()){
                double min = unvisited.get(0).weight;
                int min_idx = 0;
                for(int i = 1; i < unvisited.size(); i++){
                    if(unvisited.get(i).weight < min){
                        min = unvisited.get(i).weight;
                        min_idx = i;
                    }
                }
                Vertex curr = unvisited.get(min_idx);
                List<Vertex> neighbors = potentialNeighbors.apply(curr.point)
                        .filter(canPassThrough)
                        .map(p -> new Vertex(p, curr.weight + p.distance(curr.point), curr))
                        .collect(Collectors.toList());
                for(Vertex neighbor: neighbors){
                    if(withinReach.test(neighbor.point, end)) {
                        found = true;
                        pathVtx = neighbor;
                        break;
                    }
                    if(unvisited.contains(neighbor) &&
                            neighbor.weight < unvisited.get(unvisited.indexOf(neighbor)).weight){
                        unvisited.remove(neighbor);
                        unvisited.add(neighbor);
                    }
                    else if(!unvisited.contains(neighbor))
                        unvisited.add(neighbor);
                }
                unvisited.remove(curr);
                visited.add(curr);
            }
            else return null;

        }
        LinkedList<Point> path = new LinkedList<>();
        while(pathVtx.prior != null){
            path.addFirst(pathVtx.point);
            pathVtx = pathVtx.prior;
        }
        return path;
    }
}
