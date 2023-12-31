package Model.Structs;

import Model.Enums.Direction;

public class Location 
{
    private int x;
    private int y;

    public Location(int xParameter, int yParameter)
    {
        this.x = xParameter;
        this.y = yParameter;
    }

    public Location getMovedCopy(Direction directionParameter)
    {
        int newX = this.x;
        int newY = this.y;
        switch(directionParameter)
        {
            case RIGHT:
                newX++;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;   
                break;
            case UP:
                newY--;
                break;
        }
        return new Location(newX, newY);
    }

    public double getDistanceToLocation(Location locationParameter)
    {
        return Math.hypot(this.x - locationParameter.x, this.y - locationParameter.y);
    }

    public boolean isEqualToLocation(Location locationParameter)
    {
        return ((this.x == locationParameter.x) && (this.y == locationParameter.y));
    }

    public boolean isOutOfBounds(int sizeXParameter, int sizeYParameter)
    {
        if((this.x < 0) || (this.y < 0) || (this.x == sizeXParameter) || (this.y == sizeYParameter))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public Location getCopy()
    {
        return new Location(this.x, this.y);
    }
}
