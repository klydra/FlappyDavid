import greenfoot.*;

import java.util.HashMap;

public class Player extends Character {
    int gdelay = 2;
    int delay = 10;
    int actTime = 0;
    int gravity = 0;

    public Player(String account, Controller controller, HashMap<String, Integer> scoreboard, HashMap<String, Ghost> players) {
        super(account, controller, scoreboard, players);
    }

    @Override
    public void act() {
        actTime++;
        if (Greenfoot.isKeyDown("space"))
        {
            if(actTime>delay)
            {
                gravity = 10;
                actTime = 0;
            }
        }

        setLocation(getX(),getY() - gravity);

        if (gravity > -7)
        {
            setRotation(-30);
        }

        else if (getRotation()!=90)
        {
            turn(5);
        }

        if (gravity > -7 && gdelay < actTime)
        {
            gravity--;
        }

        controller.communications.session.publish(getY());

        checkCollision();

        super.act();
    }

    public void checkCollision() {
        TubeLong tubeLong = (TubeLong)getOneIntersectingObject(TubeLong.class);
        TubeMedium tubeMedium = (TubeMedium)getOneIntersectingObject(TubeMedium.class);
        TubeShort tubeShort = (TubeShort)getOneIntersectingObject(TubeShort.class);
        boolean atFrame = getY() < 50 || getY() > 700;


        if (tubeLong != null || tubeMedium != null || tubeShort != null || atFrame)
        {
            die();
        }
    }

    public void avatar(Byte avatar) {
        switch (avatar) {
            case 1:
                setImage("characters/player/roman.png");
            case 2:
                setImage("characters/player/alex.png");
            case 3:
                setImage("characters/player/david.png");
            case 4:
                setImage("characters/player/dino.png");
            case 5:
                setImage("characters/player/ely.png");
            case 6:
                setImage("characters/player/justin.png");
            case 7:
                setImage("characters/player/marcus.png");
            case 8:
                setImage("characters/player/kilian.png");
            case 9:
                setImage("characters/player/kilian.png");
            case 0:
            default:
                setImage("characters/player/findus.png");
        }
    }

    @Override
    public void die() {
        controller.communications.session.unready();
        super.die();
    }
}
