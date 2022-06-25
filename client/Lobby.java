import greenfoot.Greenfoot;
import greenfoot.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby extends World {
    String uri;
    Instance instance;
    Controller controller;

    Button readyStatus;
    Button avatarStatus;

    public HashMap<String, Button> entries = new HashMap<String, Button>();
    public ArrayList<String> order = new ArrayList<String>();

    public Lobby() {
        super(1000, 750, 1);
    }

    @Override
    public void started() {
        super.started();

        connect();

        ready(false);
    }

    @Override
    public void act() {
        if (readyStatus != null && Greenfoot.isKeyDown("space")) {
            ready(readyStatus.getClass().getSimpleName().equals("ButtonUnready"));
        }

        if (Greenfoot.isKeyDown("escape")) {
            connect();
        }

        if (Greenfoot.isKeyDown("up")) {

        }

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

    void ready(boolean ready) {
        if (readyStatus != null) {
            readyStatus.destroy();
        }


        if (ready) {
            readyStatus = new ButtonReady();
        } else {
            readyStatus = new ButtonUnready();
        }
    }

    void entry(String account) {
        if (!order.contains(account)) {
            order.add(account);
        }

        int index = order.indexOf(account);

        int x = 300;
        int y = 100 + (index * 50);

        if (entries.containsKey(account)) {
            entries.get(account).destroy();
        }

        Button avatar = avatar(instance.avatars.get(account));

        entries.put(account, avatar);
        addObject(avatar, x, y);


    }

    Button avatar(Byte avatar) {
        switch (avatar) {
            case 1:
                return new ButtonRoman();
            case 2:
                return new ButtonAlex();
            case 3:
                return new ButtonDavid();
            case 4:
                return new ButtonDino();
            case 5:
                return new ButtonEly();
            case 6:
                return new ButtonJustin();
            case 7:
                return new ButtonMarcus();
            case 8:
                return new ButtonKilian();
            case 9:
                return new ButtonSimon();
            case 0:
            default:
                return new ButtonFindus();
        }
    }
}
