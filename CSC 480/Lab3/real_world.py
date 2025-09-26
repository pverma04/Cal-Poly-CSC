from collections import defaultdict
from queue import PriorityQueue

import DFS
from ucs1 import ucs
import random
import time

def generate_city_graph(num_nodes):
    city_graph = defaultdict(list)
    node_names = [f"{i}" for i in range(num_nodes)]

    for i in range(num_nodes):
        num_connections = random.randint(1, min(num_nodes - 1, 5))
        if num_connections == 0:
            num_connections = 1

        # Ensure that each city has at least one neighbor
        connections = random.sample(node_names[:i] + node_names[i+1:], num_connections)

        for connection in connections:
            distance = random.randint(1, 10)
            city_graph[node_names[i]].append((connection, distance))

    return city_graph

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

def dfs(graph, start, goal):
    visited = set() #is a set as to not hold repeats
    path = [] #holds order of travel

    def dfs_recursive(v): #recursive 
        visited.add(v) #mark current as visited
        path.append(v) #put current in path FOR NOW

        if v == goal: #reached, final
            return True
        
        for neighbor in graph[v]:
            if neighbor not in visited: #if closest neighbor not visited, run again recursively
                if dfs_recursive(neighbor): #base case
                    return True
        
        #this node didnt get us anywhere, pop and go back to neighbor and try again
        path.pop()
        return False

    if dfs_recursive(start): #begin search from given start
        return path #found
    else:
        return None #was not in graph
    
if __name__ == '__main__':
    graph = generate_city_graph(10)

    print("City Graph:")
    for node, connections in graph.items():
        print(f"{node}: {connections}")

    start_node = "1"
    goal_nodes = ["3"]

    print("Start node:", start_node)
    print("Goal nodes:", goal_nodes)

    ucs_cost, ucs_path = ucs(graph, start_node, goal_nodes)
    if ucs_path is None:
        print("No ucs path found")
    else:
        print("UCS path:", ucs_path)
        print("UCS cost:", ucs_cost)
   
    dfs_path = dfs(graph, start_node, goal_nodes)
    if dfs_path is None:
        print("No dfs path found")
    else:
        print("DFS path:", ucs_path)
    
