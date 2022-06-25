import greenfoot.*;

import java.util.HashMap;

public class Ghost extends Character {
    int gdelay = 2;
    int actTime = 0;
    int gravity = 0;
    boolean rising = false;

    public Ghost(String account, Controller controller, HashMap<String, Integer> scoreboard, HashMap<String, Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }

    @Override
    public void act() {
        actTime++;

        if (gravity > -7)
        {
            setRotation(-30);
        }

        else if (getRotation() != 90)
        {
            turn(5);
        }

        if (gravity > -7 && gdelay < actTime)
        {
            gravity--;
        }

        super.act();
    }

    public void updatePosition(int positionY) {
        if (getY() > positionY && !rising) {
            gravity = 10;
            rising = true;
        } else if (getY() < positionY && rising) {
            rising = false;
        }

        setLocation(getX(), positionY);
    }

    public void avatar(Byte avatar) {
        switch (avatar) {
            case 1:
                setImage("characters/ghost/roman.png");
            case 2:
                setImage("characters/ghost/alex.png");
            case 3:
                setImage("characters/ghost/david.png");
            case 4:
                setImage("characters/ghost/dino.png");
            case 5:
                setImage("characters/ghost/ely.png");
            case 6:
                setImage("characters/ghost/justin.png");
            case 7:
                setImage("characters/ghost/marcus.png");
            case 8:
                setImage("characters/ghost/kilian.png");
            case 9:
                setImage("characters/ghost/kilian.png");
            case 0:
            default:
                setImage("characters/ghost/findus.png");
        }
    }

    @Override
    public void die() {
        super.die();
        ghosts.remove(account);
    }
}
