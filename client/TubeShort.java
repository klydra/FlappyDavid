import greenfoot.*;

public class TubeShort extends Actor
{
    int speed = 0;

    public TubeShort (int winkel, int speed)
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
