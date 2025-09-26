from collections import defaultdict

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

if __name__ == "__main__":
    graph = defaultdict(list)
    graph[0].extend([1, 2])
    graph[1].append(2)
    graph[2].extend([0, 3])
    graph[3].append(3)

    start_node = 2
    goal_node = 3
    path = dfs(graph, start_node, goal_node)
    if path:
        print("Path from", start_node, "to", goal_node, ":", path)
    else:
        print("No path found from", start_node, "to", goal_node)




'''class Graph:
    def __init__(self):
        self.graph = defaultdict(list)

    def addEdge(self, curr, new_pos):
        self.graph[curr].append(new_pos)

    def dfsRecursive (self, vert, visited):
        visited.add(vert) #mark current vert as vistied
        print(vert, end=' ')
        for neighborVert in self.graph[vert]:
            if neighborVert not in visited: #if not vistied, run the alg on the neighboring node
                self.dfsRecursive(neighborVert, visited)
    
    def dfs(self, vert):
        visited = set() #use a set so as to never have a duplicate
        self.dfsRecursive(vert, visited) #call for traversal from the original vert


if __name__ == "__main__":
    g = Graph()
    g.addEdge(0, 1)
    g.addEdge(0, 2)
    g.addEdge(1, 2)
    g.addEdge(2, 0)
    g.addEdge(2, 3)
    g.addEdge(3, 3)

    g.dfs(2)

'''