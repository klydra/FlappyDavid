import greenfoot.*;

import java.util.HashMap;

public class Player extends Character {
    int gdelay = 2;
    int delay = 10;
    int actTime = 0;
    int gravity = 0;

    public Player(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }

    @Override
    public void act() {
        actTime++;

        if (Greenfoot.isKeyDown("space")) {
            if (actTime > delay) {
                gravity = 8;
                actTime = 0;
            }
        }

        setLocation(getX(), getY() - gravity);

        if (gravity > -3) {
            setRotation(-30);
        } else if (getRotation() != 90) {
            turn(5);
        }

        if (gravity > -3 && gdelay < actTime) {
            gravity--;
        }

        super.act();
    }

    @Override
    public void die() {
        controller.communications.session.unready();
        super.die();
    }
}
