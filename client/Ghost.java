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

    public void avatar(int avatar) {
        switch (avatar) {
            case 1:
                setImage(new GreenfootImage("images/characters/ghost/roman.png"));
                break;
            case 2:
                setImage(new GreenfootImage("images/characters/ghost/alex.png"));
                break;
            case 3:
                setImage(new GreenfootImage("images/characters/ghost/david.png"));
                break;
            case 4:
                setImage(new GreenfootImage("images/characters/ghost/dino.png"));
                break;
            case 5:
                setImage(new GreenfootImage("images/characters/ghost/ely.png"));
                break;
            case 6:
                setImage(new GreenfootImage("images/characters/ghost/justin.png"));
                break;
            case 7:
                setImage(new GreenfootImage("images/characters/ghost/marcus.png"));
                break;
            case 8:
                setImage(new GreenfootImage("images/characters/ghost/kilian.png"));
                break;
            case 9:
                setImage(new GreenfootImage("images/characters/ghost/simon.png"));
                break;
            case 0:
            default:
                setImage(new GreenfootImage("images/characters/ghost/findus.png"));
                break;
        }
    }
}
