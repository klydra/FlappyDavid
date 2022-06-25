import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TubeMedium here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TubeMedium extends Actor
{
    /**
     * Act - do whatever the TubeMedium wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
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
