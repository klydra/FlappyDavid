import greenfoot.*;

import java.util.HashMap;

public class Character extends Actor
{
    byte[] account;

    Controller controller;
    HashMap<byte[], Integer> scoreboard;
    HashMap<byte[], Ghost> ghosts;

    public Character(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts)
    {
        this.account = account;
        this.controller = controller;
        this.scoreboard = scoreboard;
        this.ghosts = ghosts;
    }
    public void act() 
    {
        checkCollision();
    }

    public void checkCollision() {
        TubeLong tubeLong = (TubeLong)getOneIntersectingObject(TubeLong.class);
        TubeMedium tubeMedium = (TubeMedium)getOneIntersectingObject(TubeMedium.class);
        TubeShort tubeShort = (TubeShort)getOneIntersectingObject(TubeShort.class);
        //Frame frame = (Frame) getOneIntersectingObject(Frame.class);

        if (tubeLong != null || tubeMedium != null || tubeShort != null/* ||  frame != null */)
        {
            die();
        }
    }

    public void die() {
        getWorld().removeObject(this);
    }
}