import greenfoot.*;

public class TubeMedium extends Actor
{
    int speed = 0;

    public TubeMedium (int winkel, int speed)
    {
        this.speed=speed;
        setRotation(winkel);
    }

    public void act() 
    {
        move(speed);
        if(isAtEdge())
        {
            getWorld().removeObject(this);
        }
    }
}
