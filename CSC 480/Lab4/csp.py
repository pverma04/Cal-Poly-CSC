import matplotlib.colors as mcolors

# CSP code from professor
class CSP:
    def __init__(self, variables, domains, constraints):
        self.variables = variables
        self.domains = domains
        self.constraints = constraints
        self.graph = None

    def is_consistent(self, variable, value, assignment):
        """Check if the current assignment is consistent."""
        all_neighbors = []
        for constraint in self.constraints:
            if variable in get_neighbors(variable, self.graph):
                for neighbor_variable in get_neighbors(variable, self.graph):
                    neighbor_value = assignment.get(neighbor_variable)
                    if neighbor_value is not None and not constraint(variable, value, neighbor_variable, neighbor_value):
                        return False
        return True

    def backtrack(self, assignment):
        """Backtrack search to find all solutions."""
        if len(assignment) == len(self.variables):
            return [assignment.copy()]  #return all solutions in a list

        unassigned = [v for v in self.variables if v not in assignment]
        first = unassigned[0]
        solutions = []
        for value in self.domains[first]:
            local_assignment = assignment.copy()
            local_assignment[first] = value
            if self.is_consistent(first, value, local_assignment):
                result = self.backtrack(local_assignment)
                if result:
                    solutions.extend(result)  #extend solutions list
        return solutions

    def solve(self):
        """Solve the CSP."""
        return self.backtrack({})

def color_constraints(reg1, col1, reg2, col2):
    return col1 != col2  

def get_neighbors(region, graph):
    return graph.get(region, [])

def generate_color_combo():
    graph = {
        'a': ['b', 'c'],
        'b': ['a', 'c', 'd'],
        'c': ['a', 'b', 'd', 'e'],
        'd': ['b', 'c', 'e', 'f'],
        'e': ['c', 'd', 'f', 'g'],
        'f': ['d', 'e', 'g'],
        'g': ['e', 'f']
    }

    regions = list(graph.keys())
    colors = [mcolors.CSS4_COLORS['red'], mcolors.CSS4_COLORS['green'], mcolors.CSS4_COLORS['blue']]
    domains = {region: colors for region in regions}

    cspMap = CSP(regions, domains, [color_constraints])
    sols = cspMap.solve()
    if sols:
        print("Map Color Combos: ")
        for solution in sols:
            for region, color in solution.items():
                print(f"{region}: {color}")
            print()  #print blank line between solutions
    else:
        print("No solutions found")

generate_color_combo()