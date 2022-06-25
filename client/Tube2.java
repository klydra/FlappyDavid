import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tube2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tube2 extends Actor
{
    /**
     * Act - tut, was auch immer Boden tun will. Diese Methode wird aufgerufen, 
     * sobald der 'Act' oder 'Run' Button in der Umgebung angeklickt werden. 
    */
    int speed = 0;
    public Tube2(int rotation, int speed)
    {
        this.speed=speed;
        setRotation(rotation);
    }
    public void act() 
    {
        move(speed);
        if(isAtEdge())
        {
            getWorld().removeObject(this);
        }
        else
        {
            kollisionen();
        }
    }    
    public void kollisionen()
    {
        David d = (David)getOneIntersectingObject(David.class);
        if(d != null)
        {
            getWorld().removeObject(d);
            getWorld().removeObject(this);
        }
    }
}
