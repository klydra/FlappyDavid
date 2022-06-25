import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;

import javax.swing.*;

public class Lobby extends World {
    String uri;
    Instance instance;
    Controller controller;

    public Lobby() {
        super(1000, 750, 1);
    }

    @Override
    public void started() {
        super.started();

        connect();
    }

    @Override
    public void act() {
        super.act();
    }

    public void connect() {
        uri = JOptionPane.showInputDialog("Enter Server URI");
        instance = new Instance(this, uri);
        controller = instance.controller;
    }

    public void start() {
        Greenfoot.setWorld(instance);
    }
}
