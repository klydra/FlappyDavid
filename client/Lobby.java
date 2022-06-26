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

    public HashMap<String, ButtonAvatar> entries = new HashMap<>();
    public ArrayList<String> order = new ArrayList<>();

    int avatarCount = 9;
    boolean keyPress = false;

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

        if (instance.account != null) {
            entry(instance.account);
        }
    }

    @Override
    public void act() {
        if (Greenfoot.isKeyDown("space") && !keyPress) {
            keyPress = true;
            if (instance.readies.get(instance.account)) {
                controller.communications.session.unready();
                ready.ready(false);
            } else {
                controller.communications.session.ready();
                ready.ready(true);
            }
        }

        if (Greenfoot.isKeyDown("escape") && !keyPress) {
            keyPress = true;
            controller.communications.authentication.unregister();
            System.exit(0);
        }

        if (Greenfoot.isKeyDown("up") && !keyPress) {
            keyPress = true;
            int currentAvatar = instance.avatars.get(instance.account);
            if (currentAvatar + 1 <= avatarCount) {
                int newAvatar = currentAvatar + 1;
                controller.communications.session.avatar(newAvatar);
            }
        }

        if (Greenfoot.isKeyDown("down") && !keyPress) {
            keyPress = true;
            int currentAvatar = instance.avatars.get(instance.account);
            if (currentAvatar - 1 >= 0) {
                int newAvatar = currentAvatar - 1;
                controller.communications.session.avatar(newAvatar);
            }
        }

        if (!Greenfoot.isKeyDown("escape") && !Greenfoot.isKeyDown("space") && !Greenfoot.isKeyDown("up") && !Greenfoot.isKeyDown("down")) {
            keyPress = false;
        }

        super.act();
    }

    public void connect() {
        JFrame jframe = new JFrame();
        jframe.setAlwaysOnTop(true);
        uri = JOptionPane.showInputDialog(jframe, "Enter Server URI");
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

        int x = 375;
        int y = 100 + (index * 50);

        if (entries.containsKey(account)) {
            entries.get(account).destroy();
        }

        ButtonAvatar avatar = new ButtonAvatar(instance.avatars.get(account));

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
