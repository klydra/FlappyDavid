import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Boden.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Tube1 extends Actor
{
    /**
     * Act - tut, was auch immer Boden tun will. Diese Methode wird aufgerufen, 
     * sobald der 'Act' oder 'Run' Button in der Umgebung angeklickt werden. 
    */
    int speed = 0;
    public Tube1(int rotation, int speed)
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