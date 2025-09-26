from mesa import Agent, Model, visualization
from mesa.time import RandomActivation
from mesa.space import MultiGrid
import random


PLACEHOLDER_POS = (0, 0)

class Prey(Agent):
    def __init__(self, unique_id, model):
        super().__init__(unique_id, model)
        self.energy = 100

    def move(self):
        possible_steps = self.model.grid.get_neighborhood(
            self.pos, moore=True, include_center=False
        )
        new_position = random.choice(possible_steps)
        self.model.grid.move_agent(self, new_position)

    def eat(self):
        self.energy += 10

    def breed(self):
        if self.energy >= 200:
            self.energy -= 100
            new_prey = Prey(self.model.next_id(), self.model)
            self.model.grid.place_agent(new_prey, PLACEHOLDER_POS)
            self.model.grid.move_to_empty(new_prey)
            self.model.schedule.add(new_prey)

    def step(self):
        # if this agent was removed from the grid, don't step. this is
        # important because if an agent is removed from the RandomActivation
        # scheduler before it has stepped, it will still step one last time
        if self.pos is None:
            return
        self.move()
        self.breed()
        self.energy -= 1

class Hunter(Agent): #meant to kill anything it sees
    def __init__(self, unique_id, model):
        super().__init__(unique_id, model)
        self.energy = 100

    def move(self):
        possible_steps = self.model.grid.get_neighborhood(
            self.pos, moore=True, include_center=False
        )
        new_position = random.choice(possible_steps)
        self.model.grid.move_agent(self, new_position)

    def kill(self):
        prey_neighbors = self.model.grid.get_cell_list_contents(
            [self.pos]
        )
        predator_neighbors = self.model.grid.get_cell_list_contents(
            [self.pos]
        )
        prey_agents = [agent for agent in prey_neighbors if
                        isinstance(agent, Prey)]
        predator_agents = [agent for agent in predator_neighbors if
                        isinstance(agent, Predator)]
        if prey_agents:
            prey_to_kill = random.choice(prey_agents)
            self.model.schedule.remove(prey_to_kill)
            self.model.grid.remove_agent(prey_to_kill)
            self.energy += 100
        if predator_agents:
            predator_to_eat = random.choice(predator_agents)
            self.model.schedule.remove(predator_to_eat)
            self.model.grid.remove_agent(predator_to_eat)
            self.energy += 100

    def step(self):
        self.move()
        self.eat()
        self.breed()
        self.energy -= 1

class Predator(Agent):
    def __init__(self, unique_id, model):
        super().__init__(unique_id, model)
        self.energy = 100

    def move(self):
        possible_steps = self.model.grid.get_neighborhood(
            self.pos, moore=True, include_center=False
        )
        new_position = random.choice(possible_steps)
        self.model.grid.move_agent(self, new_position)

    def eat(self):
        prey_neighbors = self.model.grid.get_cell_list_contents(
            [self.pos]
        )
        prey_agents = [agent for agent in prey_neighbors if
                       isinstance(agent, Prey)]
        if prey_agents:
            prey_to_eat = random.choice(prey_agents)
            self.model.schedule.remove(prey_to_eat)
            self.model.grid.remove_agent(prey_to_eat)
            self.energy += 100

    def breed(self):
        if self.energy >= 200:
            self.energy -= 100
            new_predator = Predator(self.model.next_id(), self.model)
            self.model.grid.place_agent(new_predator, PLACEHOLDER_POS)
            self.model.grid.move_to_empty(new_predator)
            self.model.schedule.add(new_predator)

    def step(self):
        self.move()
        self.eat()
        self.breed()
        self.energy -= 1


class PreyPredatorModel(Model):
    def __init__(self, height, width, prey_count, predator_count):
        super().__init__()
        self.current_id = 0
        self.height = height
        self.width = width
        self.grid = MultiGrid(height, width, torus=True)
        self.schedule = RandomActivation(self)
        self.running = True

        for i in range(prey_count):
            prey = Prey(self.next_id(), self)
            self.grid.place_agent(prey, PLACEHOLDER_POS)
            self.grid.move_to_empty(prey)
            self.schedule.add(prey)

        for i in range(predator_count):
            predator = Predator(self.next_id(), self)
            self.grid.place_agent(predator, PLACEHOLDER_POS)
            self.grid.move_to_empty(predator)
            self.schedule.add(predator)

    def step(self):
        self.schedule.step()


def main():
    model = PreyPredatorModel(height=10, width=10, prey_count=50,
                              predator_count=10)

    for i in range(100):
        model.step()
        # Print population counts
        prey_count = sum(
            isinstance(agent, Prey) for agent in model.schedule.agents)
        predator_count = sum(
            isinstance(agent, Predator) for agent in model.schedule.agents)
        print(f"After step {i}: Prey={prey_count}, Predators={predator_count}")


if __name__ == '__main__':
    main()
