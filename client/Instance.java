import greenfoot.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Instance extends World implements Communication {
    public Lobby lobby;
    public Controller controller;
    public Frame frame;
    public ButtonText usernameStatus;
    public ButtonText scoreStatus;

    public String account;
    String username;
    boolean game;

    public Player player;
    public HashMap<String, Integer> scoreboard = new HashMap<>();
    public HashMap<String, Integer> avatars = new HashMap<>();
    public HashMap<String, String> users = new HashMap<>();
    public HashMap<String, Ghost> ghosts = new HashMap<>();
    public HashMap<String, Boolean> readies = new HashMap<>();

    public Instance(Lobby lobby, String uri) {
        super(1000, 750, 1);

        this.lobby = lobby;
        this.controller = new Controller(lobby, this, uri);

        register();

        frame = new Frame();
        addObject(frame, 500, 375);

        usernameStatus = new ButtonText(Color.WHITE, 24);
        usernameStatus.content(" ");
        addObject(usernameStatus, 835, 62);

        scoreStatus = new ButtonText(Color.WHITE,36);
        scoreStatus.content("0");
        addObject(scoreStatus, 160, 62);

        setPaintOrder(ButtonText.class, Frame.class, Player.class, Ghost.class);
    }

    void register() {
        JFrame jframe = new JFrame();
        jframe.setAlwaysOnTop(true);
        username = JOptionPane.showInputDialog(jframe, "Enter your Username");
        controller.communications.authentication.register(username);
    }

    public void addScore() {
        if (player != null) {
            scoreboard.put(account, scoreboard.get(account) + 1);
            scoreStatus.content(scoreboard.get(account).toString());
        }

        for(HashMap.Entry<String, Ghost> entry : ghosts.entrySet()) {
            String account = entry.getKey();
            scoreboard.put(account, scoreboard.get(account) + 1);
        }
    }

    @Override
    public void onAuxiliaryMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onAuthenticationRegistered(String account) {
        this.account = account;
        users.put(account, username);
        scoreboard.put(account, 0);
        avatars.put(account, 0);
        readies.put(account, false);

        JFrame jframe = new JFrame();
        jframe.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jframe, "Joined Server.");
    }

    @Override
    public void onAuthenticationTaken() {
        JFrame jframe = new JFrame();
        jframe.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jframe, "Username already taken.");
        username = "";
        register();
    }

    @Override
    public void onAuthenticationBusy() {
        JFrame jframe = new JFrame();
        jframe.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jframe, "A game is currently in progress. Try again later.");
        username = "";
        register();
    }

    @Override
    public void onAuthenticationUnregistered() {
        JFrame jframe = new JFrame();
        jframe.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jframe, "Left server.");
        username = "";
    }

    @Override
    public void onSessionStart() {
        game = true;
        lobby.start();

        usernameStatus.content(username);

        player = new Player(account, controller, scoreboard, ghosts);
        player.avatar(avatars.get(account));
        addObject(player, 250, 375);

        lobby.order = new ArrayList<>();

        for (HashMap.Entry<String, Integer> score : scoreboard.entrySet()) {
            score.setValue(0);
        }
    }

    @Override
    public void onSessionReady(String account) {
        readies.put(account, true);
    }

    @Override
    public void onSessionUnReady(String account) {
        readies.put(account, false);

        if (game) {
            lobby.order.add(0, account);

            readies.put(account, false);

            if (!Objects.equals(this.account, account)) {
                ghosts.get(account).destroy();
                ghosts.remove(account);
            } else {
                player = null;
            }

            if (!readies.containsValue(true)) {
                game = false;

                for (int i = 0; i < lobby.order.size(); i++) {
                    lobby.entry(lobby.order.get(i));
                }

                List<TubeLong> tubeLongs = getObjects(TubeLong.class);
                removeObjects(tubeLongs);

                List<TubeMedium> tubeMediums = getObjects(TubeMedium.class);
                removeObjects(tubeMediums);

                List<TubeShort> tubeShorts = getObjects(TubeShort.class);
                removeObjects(tubeShorts);

                for (HashMap.Entry<String, String> user : users.entrySet()) {
                    if (Objects.equals(this.account, user.getKey())) {
                        continue;
                    }

                    Ghost ghost = new Ghost(user.getKey(), controller, scoreboard, ghosts);
                    ghost.avatar(avatars.get(user.getKey()));
                    addObject(ghost, 250, 375);
                    ghosts.put(user.getKey(), ghost);
                }

                scoreStatus.content("0");

                lobby.ready.ready(false);

                Greenfoot.setWorld(lobby);
            }
        }
    }

    @Override
    public void onSessionUserJoined(String account, String username) {
        if (!Objects.equals(this.account, account)) {
            Ghost ghost = new Ghost(account, controller, scoreboard, ghosts);
            ghost.avatar(0);
            addObject(ghost, 250, 375);

            users.put(account, username);
            scoreboard.put(account, 0);
            ghosts.put(account, ghost);
            readies.put(account, false);
            avatars.put(account, 0);
            lobby.entry(account);
        }
    }

    @Override
    public void onSessionUserLeft(String account) {
        Ghost ghost = ghosts.get(account);
        ghost.destroy();
        ghosts.remove(account);
        scoreboard.remove(account);
        users.remove(account);
        avatars.remove(account);
        readies.remove(account);
        lobby.remove(account);
    }

    @Override
    public void onSessionPositionUpdate(String account, int positionY) {
        if (!game) {
            return;
        }

        if (!Objects.equals(this.account, account)) {
            ghosts.get(account).updatePosition(positionY);
        }
    }

    @Override
    public void onSessionAvatarUpdate(String account, int avatar) {
        avatars.put(account, avatar);
        lobby.entry(account);

        if (!Objects.equals(this.account, account)) {
            if (avatars.containsKey(account)) {
                ghosts.get(account).avatar(avatar);
            }
        } else {
            if (player != null) {
                player.avatar(avatar);
            }
        }
    }

    @Override
    public void onSessionObstacle(int position) {
        position += 150;
        int posX = 1000;

        if (position >= 524)
        {
            int posY1 = position - 350;
            int posY2 = position + 149;

            addObject(new TubeLong(true),posX,posY1);
            addObject(new TubeShort(false),posX,posY2);
        }
        if (400 <= position && position < 524)
        {
            int posY1 = position - 350;
            int posY2 = position + 225;

            addObject(new TubeLong(true),posX,posY1);
            addObject(new TubeMedium(false),posX,posY2);
        }
        if (350 < position && position < 400)
        {
            int posY1 = position - 349;
            int posY2 = position + 349;

            addObject(new TubeLong(true),posX,posY1);
            addObject(new TubeLong(false),posX,posY2);
        }
        if (225 < position && position <= 350)
        {
            int posY1 = position - 225;
            int posY2 = position + 350;

            addObject(new TubeMedium(true),posX,posY1);
            addObject(new TubeLong(false),posX,posY2);
        }
        if (position <= 225)
        {
            int posY1 = position - 149;
            int posY2 = position + 350;

            addObject(new TubeShort(true),posX,posY1);
            addObject(new TubeLong(false),posX,posY2);
        }
    }
}
