package Model.Classes;

import Model.Enums.LocationState;
import Model.Structs.Location;

public class Maze 
{
    private LocationState[][] mazeMatrix;
    private Location startLocation;
    private Location endLocation;

    public Maze(LocationState[][] mazeMatrixParameter, Location startLocationParameter, Location endLocationParameter)
    {
        mazeMatrix = mazeMatrixParameter;
        this.startLocation = startLocationParameter;
        this.endLocation = endLocationParameter;
    }

    public boolean isLocationNotBlocked(Location locationParameter)
    {
        if(locationParameter.isOutOfBounds(this.getSizeX(), this.getSizeY()))
        {
            return false;
        }
        else
        {
            return this.mazeMatrix[locationParameter.getY()][locationParameter.getX()] == LocationState.NOT_BLOCKED;
        }
    }

    public boolean isLocationTheTarget(Location locationParameter)
    {
        if(locationParameter.isEqualToLocation(this.getEndLocationCopy()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Location getStartLocationCopy()
    {
        return this.startLocation.getCopy();
    }

    public Location getEndLocationCopy()
    {
        return this.endLocation.getCopy();
    }

    public int getSizeX()
    {
        return this.mazeMatrix[0].length;
    }

    public int getSizeY()
    {
        return this.mazeMatrix.length;
    }
}
