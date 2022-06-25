import greenfoot.*;

import java.util.HashMap;

public class Character extends Actor
{
    String account;

    Controller controller;
    HashMap<String, Integer> scoreboard;
    HashMap<String, Ghost> ghosts;

    public Character(String account, Controller controller, HashMap<String, Integer> scoreboard, HashMap<String, Ghost> ghosts)
    {
        this.account = account;
        this.controller = controller;
        this.scoreboard = scoreboard;
        this.ghosts = ghosts;
    }

    public void destroy() {
        getWorld().removeObject(this);
    }
}