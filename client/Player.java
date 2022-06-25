import greenfoot.*;

import java.util.HashMap;

public class Player extends Character {
    int gdelay = 2;
    int delay = 10;
    int actTime = 0;
    int gravity = 0;

    public Player(String account, Controller controller, HashMap<String, Integer> scoreboard, HashMap<String, Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
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
            destroy();
        }
    }

    public void avatar(int avatar) {
        switch (avatar) {
            case 1:
                setImage(new GreenfootImage("images/characters/player/roman.png"));
                break;
            case 2:
                setImage(new GreenfootImage("images/characters/player/alex.png"));
                break;
            case 3:
                setImage(new GreenfootImage("images/characters/player/david.png"));
                break;
            case 4:
                setImage(new GreenfootImage("images/characters/player/dino.png"));
                break;
            case 5:
                setImage(new GreenfootImage("images/characters/player/ely.png"));
                break;
            case 6:
                setImage(new GreenfootImage("images/characters/player/justin.png"));
                break;
            case 7:
                setImage(new GreenfootImage("images/characters/player/marcus.png"));
                break;
            case 8:
                setImage(new GreenfootImage("images/characters/player/kilian.png"));
                break;
            case 9:
                setImage(new GreenfootImage("images/characters/player/simon.png"));
                break;
            case 0:
            default:
                setImage(new GreenfootImage("images/characters/player/findus.png"));
                break;
        }
    }

    @Override
    public void destroy() {
        controller.communications.session.unready();
        super.destroy();
    }
}
