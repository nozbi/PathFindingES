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
        this.sort(populationParameter.individuals, 0, populationParameter.individuals.length - 1);
       
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

    private void sort(Individual individualsParameter[], int firstIndexParameter, int lastIndexParameter)
    {
        if (firstIndexParameter < lastIndexParameter) 
        {
            int middleIndex = firstIndexParameter + (lastIndexParameter - firstIndexParameter) / 2;
            sort(individualsParameter, firstIndexParameter, middleIndex);
            sort(individualsParameter, middleIndex + 1, lastIndexParameter);
            Individual leftIndividuals[] = new Individual[middleIndex - firstIndexParameter + 1];
            Individual rightIndividuals[] = new Individual[lastIndexParameter - middleIndex];
            for (int i = 0; i < leftIndividuals.length; i++)
            {
                leftIndividuals[i] = individualsParameter[firstIndexParameter + i];
            }
            for (int i = 0; i < rightIndividuals.length; i++)
            {
                rightIndividuals[i] = individualsParameter[middleIndex + 1 + i];
            } 
            int leftIndividualsIndex = 0;
            int rightIndividualsIndex = 0;
            int mergedIndividualsIndex = firstIndexParameter;
            while ((leftIndividualsIndex < leftIndividuals.length) && (rightIndividualsIndex < rightIndividuals.length)) 
            {
                if (leftIndividuals[leftIndividualsIndex].getFitness() <= rightIndividuals[rightIndividualsIndex].getFitness()) 
                {
                    individualsParameter[mergedIndividualsIndex] = leftIndividuals[leftIndividualsIndex];
                    leftIndividualsIndex++;
                }
                else 
                {
                    individualsParameter[mergedIndividualsIndex] = rightIndividuals[rightIndividualsIndex];
                    rightIndividualsIndex++;
                }
                mergedIndividualsIndex++;
            }
            while (leftIndividualsIndex < leftIndividuals.length) 
            {
                individualsParameter[mergedIndividualsIndex] = leftIndividuals[leftIndividualsIndex];
                leftIndividualsIndex++;
                mergedIndividualsIndex++;
            }
            while (rightIndividualsIndex < rightIndividuals.length) 
            {
                individualsParameter[mergedIndividualsIndex] = rightIndividuals[rightIndividualsIndex];
                rightIndividualsIndex++;
                mergedIndividualsIndex++;
            }
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
