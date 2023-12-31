package Model.Classes;

import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import Model.Enums.Direction;
import Model.Enums.Status;
import Model.Structs.Location;

public class Population 
{
    private Individual[] individuals;
    private LinkedList<Individual> individualsThatMoved;
    private Maze maze;
    private Stack<Location> path;

    public Population(int sizeParameter, Maze mazeParameter)
    {
        this.maze = mazeParameter;
        this.individuals =  new Individual[sizeParameter];
        this.individualsThatMoved = new LinkedList<Individual>();
        for(int i = 0; i < this.individuals.length; i++)
        {
            this.individuals[i] = new Individual(mazeParameter);
            this.individualsThatMoved.add(this.individuals[i]);
        }
    }

    public Population(Population populationParameter)
    {
        for (int i = 0; i < populationParameter.individuals.length - 1; i++) 
        {
            for (int j = 0; j < populationParameter.individuals.length - i - 1; j++) 
            {
                if (populationParameter.individuals[j].getFitness() > populationParameter.individuals[j + 1].getFitness()) 
                {
                    Individual temp = populationParameter.individuals[j];
                    populationParameter.individuals[j] = populationParameter.individuals[j + 1];
                    populationParameter.individuals[j + 1] = temp;
                }
            }
        }
       
        int numberOfIndividualsToBeReplaced = populationParameter.individuals.length / 2;
        for(int i = 0; i < numberOfIndividualsToBeReplaced; i++)
        {
            int index = populationParameter.individuals.length - numberOfIndividualsToBeReplaced + i;
            populationParameter.individuals[index].getDirections().clear();
            @SuppressWarnings("unchecked")
            Stack<Direction> directions = (Stack<Direction>) populationParameter.individuals[i].getDirections().clone();
            populationParameter.individuals[index].getDirections().addAll(directions);
        }
      
        this.maze = populationParameter.maze;
        this.individuals = new Individual[populationParameter.individuals.length];
        this.individualsThatMoved = new LinkedList<Individual>();
        for(int i = 0; i < this.individuals.length; i++)
        {
            Stack<Direction> directions = populationParameter.individuals[i].getDirections();
            int numberOfRemovedDirections = ThreadLocalRandom.current().nextInt(1, directions.size() - 1);
            for(int j = 0; j < numberOfRemovedDirections; j++)
            {
                directions.pop();
            }
            this.individuals[i] = new Individual(this.maze, directions);
            this.individualsThatMoved.add(this.individuals[i]);
        }
    }

    public Status moveIndividuals()
    {
        boolean moved = false;
        boolean pathFound = false;
        for(int i = 0; i < this.individualsThatMoved.size(); i++)
        {
            switch(this.individualsThatMoved.get(i).move())
            {
                case PATH_FOUND:
                    this.path = this.individualsThatMoved.get(i).getPath();
                    this.individualsThatMoved.remove(i);
                    i--;
                    pathFound = true;
                    break;
                case NO_MORE_MOVES:
                    this.individualsThatMoved.remove(i);
                    i--;
                    break;
                case MOVED:
                    moved = true;
                    break;
            }
        }
        if(pathFound)
        {
            return Status.PATH_FOUND;
        }
        else if(moved)
        {
            return Status.MOVED;
        }
        else 
        {
            return Status.NO_MORE_MOVES;
        }
    }

    public Location[] getIndividualsLocations()
    {
        Location[] individualsLocations = new Location[this.individuals.length];
        for(int i = 0; i < individualsLocations.length; i++)
        {
            individualsLocations[i] = this.individuals[i].getLocation();
        }
        return individualsLocations;
    }

    public Stack<Location> getPath()
    {
        return this.path;
    }
}
