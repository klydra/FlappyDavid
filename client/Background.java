import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Djungle.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Background extends World implements Communication
{
    public Controller controller; 
    int delay = 150;
    int actTime = 0;
    
    /**
     * Konstruktor für Objekte der Klasse Djungle
     * 
     */
    public Background()
    {    
        super(1000, 750, 1);
        //controller = new Controller (this, "");
        Frame f = new Frame();
        addObject(f,500,375);
        David d = new David(controller);
        addObject(d,250,375);
        
    }
    public void act()
    {
        actTime++;
        if(delay < actTime)
        {
            int position = Greenfoot.getRandomNumber(450);
            position = position + 150;
            int winkel = 180;
            int speed = 2;
            if(position >= 525)
            {
                int posY1 = position - 350;
                int posX1 = 1000;
                int posY2 = position + 149;
                int posX2 = 1000;
                addObject(new TubeLong(winkel,speed),posX1,posY1);
                addObject(new TubeShort(winkel,speed),posX2,posY2);
            }
            if(400 <= position && position < 525)
            {
                int posY1 = position - 350;
                int posX1 = 1000;
                int posY2 = position + 225;
                int posX2 = 1000;
                addObject(new TubeLong(winkel,speed),posX1,posY1);
                addObject(new TubeMedium(winkel,speed),posX2,posY2);
            }
            if(350 < position && position < 400)
            {
                int posY1 = position - 349;
                int posX1 = 1000;
                int posY2 = position + 349;
                int posX2 = 1000;
                addObject(new TubeLong(winkel,speed),posX1,posY1);
                addObject(new TubeLong(winkel,speed),posX2,posY2);
            }
            if(225 < position && position <= 350)
            {
                int posY1 = position - 225;
                int posX1 = 1000;
                int posY2 = position + 350;
                int posX2 = 1000;
                addObject(new TubeMedium(winkel,speed),posX1,posY1);
                addObject(new TubeLong(winkel,speed),posX2,posY2);
            }
            if(position <= 225)
            {
                int posY1 = position - 149; 
                int posX1 = 1000;
                int posY2 = position + 350;
                int posX2 = 1000;
                addObject(new TubeShort(winkel,speed),posX1,posY1);
                addObject(new TubeLong(winkel,speed),posX2,posY2);
            }
            actTime = 0;
        }
    }
    @Override
    public void onAuxiliaryMessage(String message) {

    }

    @Override
    public void onAuthenticationRegistered() {

    }

    @Override
    public void onAuthenticationUnregistered() {

    }

    @Override
    public void onSessionStart() {

    }

    @Override
    public void onSessionUserJoined(byte[] account, String username) {

    }

    @Override
    public void onSessionPositionUpdate(byte[] account, int positionY) {

    }

    @Override
    public void onSessionAvatarUpdate(byte[] account, Byte avatar) {

    }
//150-600
    @Override
    public void onSessionObstacle(int position) 
    {
        actTime++;
        if(delay < actTime)
        {
            Greenfoot.getRandomNumber(450);
            int winkel = 180;
            int speed = 2;
            if(position > 400)
            {
                int posY1 = position - 200;
                int posX1 = 1000;
                int posY2 = position + 300;
                int posX2 = 1000;
                addObject(new TubeLong(winkel,speed),posX1,posY1);
                addObject(new TubeShort(winkel,speed),posX2,posY2);
            }
            if(250< position && position < 400)
            {
                int posY1 = position - 250;
                int posX1 = 1000;
                int posY2 = position + 350;
                int posX2 = 1000;
                addObject(new TubeLong(winkel,speed),posX1,posY1);
                addObject(new TubeMedium(winkel,speed),posX2,posY2);
            }
            if(200 < position && position < 250)
            {
                int posY1 = position - 200;
                int posX1 = 1000;
                int posY2 = position + 500;
                int posX2 = 1000;
                addObject(new TubeLong(winkel,speed),posX1,posY1);
                addObject(new TubeLong(winkel,speed),posX2,posY2);
            }
            if(50 < position && position < 200)
            {
                int posY1 = position - 50;
                int posX1 = 1000;
                int posY2 = position + 550;
                int posX2 = 1000;
                addObject(new TubeMedium(winkel,speed),posX1,posY1);
                addObject(new TubeLong(winkel,speed),posX2,posY2);
            }
            if(50 > position)
            {
                int posY1 = position;
                int posX1 = 1000;
                int posY2 = position + 500;
                int posX2 = 1000;
                addObject(new TubeShort(winkel,speed),posX1,posY1);
                addObject(new TubeLong(winkel,speed),posX2,posY2);
            }
            actTime = 0;
        }
    }
}
