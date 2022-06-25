import greenfoot.*;

public class Tube extends Actor {
    int speed = 2;
    int winkel = 180;
    boolean tracking;
    int score = 250;

    public Tube (boolean tracking)
    {
        this.tracking = tracking;
        setRotation(winkel);
    }

    public void act()
    {
        move(speed);
        if (tracking && getX() <= score) {
            ((Instance) getWorld()).addScore();
            tracking = false;
        }
        if(isAtEdge())
        {
            getWorld().removeObject(this);
        }
    }
}
