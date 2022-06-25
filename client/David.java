import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse David.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class David extends Actor
{
    Controller controller;
    int gdelay = 2;
    int delay = 10;
    int actTime = 0;
    int gravity = 0;
    /**
     * Act - tut, was auch immer David tun will. Diese Methode wird aufgerufen, 
     * sobald der 'Act' oder 'Run' Button in der Umgebung angeklickt werden. 
     */
    public David (Controller controller)
    {
        this.controller = controller;
    }
    public void act() 
    {
        actTime++;
        if(Greenfoot.isKeyDown("space"))
        {
            if(actTime>delay)
            {
                gravity = 10;
                actTime = 0;
            }

        }
        setLocation(getX(),getY() - gravity);
        if(gravity > -7)
        {
            setRotation(-30);
        }
        else if(getRotation()!=90)
        {
            turn(5);
        }
        if(gravity > -7 && gdelay < actTime)
        {
            gravity--;
        }
        TubeLong tubeLong = (TubeLong)getOneIntersectingObject(TubeLong.class);
        Frame frame = (Frame)getOneIntersectingObject(Frame.class);
        if(tubeLong != null)
        {
 
        }
        TubeShort tubeShort = (TubeShort)getOneIntersectingObject(TubeShort.class);
        if(tubeShort != null)
        {
            
        }
        TubeMedium tubeMedium = (TubeMedium)getOneIntersectingObject(TubeMedium.class);
        if(tubeMedium != null)
        {
            
        }
    }    
    
}