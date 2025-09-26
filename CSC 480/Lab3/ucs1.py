from queue import PriorityQueue

def ucs(graph, start, destination):
    pq = PriorityQueue()
    pq.put((0, start, [start])) #initial
    visited = set()

    while not pq.empty():
        cost, curr, path = pq.get()
        if curr == destination:
            return cost, path
        
        if curr not in visited:
            visited.add(curr)
            for neighbor, distance in graph.get(curr, []):
                if neighbor not in visited:
                    new_cost = cost + distance
                    new_path = path + [neighbor]
                    pq.put((new_cost, neighbor, new_path))
    return float('inf'), None #no path found
def print_path(graph, path):
    len = len(path)
    dist = path[-1]
    print("Best path/n")
    for i in (range(len) - 2):
        d = curr_to_neighbor(graph, path[i], path[i + 1]) 
        print('%s -> %s %s' %(path[i], path[i + 1], d))
        

def curr_to_neighbor(graph, curr, neighbor):
    indx = [i[0] for i in graph[curr]].index(neighbor)
    return graph[curr][indx][1]
