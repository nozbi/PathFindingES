package Model.Classes;

import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;
import Model.Enums.Direction;
import Model.Enums.Status;
import Model.Structs.Location;

public class Individual 
{
    private Maze maze;
    private Location location;
    private Stack<Direction> directions;
    private Stack<Location> visitedLocations;
    private int moveIndex;

    public Individual(Maze mazeParameter)
    {
        this.maze = mazeParameter;
        this.location = mazeParameter.getStartLocationCopy();
        this.directions = new Stack<Direction>();
        this.visitedLocations = new Stack<Location>();
        this.visitedLocations.add(this.location);
        this.moveIndex = -1;
    }

    public Individual(Maze mazeParameter, Stack<Direction> directionsParameter)
    {
        this(mazeParameter);

        this.directions = directionsParameter;
    }

    public Status move()
    {
        this.moveIndex++;
        if(this.moveIndex < this.directions.size())
        {
            Location newLocation = this.location.getMovedCopy(this.directions.get(moveIndex));
            this.location = newLocation;
            this.visitedLocations.add(this.location.getCopy());
            return Status.MOVED;
        }
        else
        {
            LinkedList<Direction> possibleDirections = new LinkedList<Direction>();
            possibleDirections.add(Direction.RIGHT);
            possibleDirections.add(Direction.DOWN);
            possibleDirections.add(Direction.LEFT);
            possibleDirections.add(Direction.UP);
            while(possibleDirections.isEmpty() == false)
            {
                int directionIndex = ThreadLocalRandom.current().nextInt(0, possibleDirections.size());
                Direction direction = possibleDirections.get(directionIndex);
                Location newLocation = this.location.getMovedCopy(direction);
                if((this.maze.isLocationNotBlocked(newLocation)) 
                && ((this.directions.isEmpty()) || (direction.isDirectionNotOpposite(this.directions.peek())))
                && ((this.directions.size() < 2) || (direction.isDirectionNotOpposite(this.directions.get(this.directions.size() - 2)))))
                {
                    for(int i = 0; i < this.visitedLocations.size(); i++)
                    {
                        if(newLocation.isEqualToLocation(this.visitedLocations.get(i)))
                        {
                            return Status.NO_MORE_MOVES;
                        }
                    }

                    this.directions.add(direction);
                    this.location = newLocation;
                    this.visitedLocations.add(this.location.getCopy());

                    if(this.maze.isLocationTheTarget(newLocation))
                    {
                        return Status.PATH_FOUND;
                    }
                    else
                    {
                        return Status.MOVED;
                    }
                }
                else
                {
                    possibleDirections.remove(directionIndex);
                }
            }
            return Status.NO_MORE_MOVES;
        }
    }

    public Location getLocation()
    {
        return this.location;
    }

    public Stack<Location> getPath()
    {
        return this.visitedLocations;
    }

    public Stack<Direction> getDirections()
    {
        return this.directions;
    }

    public double getFitness()
    {
        return this.location.getDistanceToLocation(this.maze.getEndLocationCopy()) - this.visitedLocations.size();
    }
}
