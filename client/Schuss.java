import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Schuss.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Schuss extends Actor
{
    /**
     * Act - tut, was auch immer Schuss tun will. Diese Methode wird aufgerufen, 
     * sobald der 'Act' oder 'Run' Button in der Umgebung angeklickt werden. 
     */
    public Schuss(int r)
    {
        setRotation(r);
    }
    public void act() 
    {
        move(6);
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
        Tube1 b = (Tube1)getOneIntersectingObject(Tube1.class);
        if(b != null)
        {
            getWorld().removeObject(b);
            getWorld().removeObject(this);
        }
    }
}