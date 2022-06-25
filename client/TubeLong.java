import greenfoot.*;

public class TubeLong extends Actor
{
    int speed = 0;

    public TubeLong (int winkel, int speed)
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
