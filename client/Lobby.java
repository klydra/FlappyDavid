import greenfoot.Greenfoot;
import greenfoot.World;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Lobby extends World {
    String uri;
    Instance instance;
    Controller controller;

    ButtonReady ready;

    public HashMap<String, Button> entries = new HashMap<String, Button>();
    public ArrayList<String> order = new ArrayList<String>();

    int avatarCount = 10;

    public Lobby() {
        super(1000, 750, 1);
    }

    @Override
    public void started() {
        super.started();

        connect();

        ready = new ButtonReady();
        ready.ready(false);
        addObject(ready, 500, 650);

        entry(instance.account);
    }

    @Override
    public void act() {
        if (Greenfoot.isKeyDown("space")) {
            if (instance.readies.get(instance.account)) {
                controller.communications.session.unready();
                ready.ready(false);
            } else {
                controller.communications.session.ready();
                ready.ready(true);
            }
        }

        if (Greenfoot.isKeyDown("escape")) {
            connect();
        }

        if (Greenfoot.isKeyDown("up")) {
            int currentAvatar = instance.avatars.get(instance.account).intValue();
            if (currentAvatar + 1 <= avatarCount) {
                controller.communications.session.avatar((byte) (currentAvatar + 1));
                entry(instance.account);
            }
        }

        if (Greenfoot.isKeyDown("down")) {
            int currentAvatar = instance.avatars.get(instance.account).intValue();
            if (currentAvatar - 1 >= 0) {
                controller.communications.session.avatar((byte) (currentAvatar - 1));
                entry(instance.account);
            }
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

        ButtonAvatar avatar = new ButtonAvatar();
        avatar.avatar(instance.avatars.get(account));

        entries.put(account, avatar);
        addObject(avatar, x, y);

        avatar.description(instance.users.get(account), String.valueOf(instance.scoreboard.get(account)));
    }

    void remove(String account) {
        order.remove(account);

        if (entries.containsKey(account)) {
            entries.get(account).destroy();
            entries.remove(account);
        }
    }
}
