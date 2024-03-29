package Model.Enums;

public enum Direction 
{
    RIGHT,
    DOWN,
    LEFT,
    UP;

    public boolean isDirectionNotOpposite(Direction directionParameter)
    {
        switch(this)
        {
            case RIGHT:
                if(directionParameter == Direction.LEFT)
                {
                    return false;
                }
                break;
            case DOWN:
                if(directionParameter == Direction.UP)
                {
                    return false;
                }
                break;
            case LEFT:
                if(directionParameter == Direction.RIGHT)
                {
                    return false;
                }
                break;
            case UP:
                if(directionParameter == Direction.DOWN)
                {
                    return false;
                }
                break;
        }
        return true;
    }
}
