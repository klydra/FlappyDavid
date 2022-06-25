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

    @Override
    public void die() {
        //controller.communications.session.unready();
        super.die();
    }
}
