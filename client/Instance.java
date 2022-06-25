import greenfoot.*;

import javax.swing.*;
import java.util.HashMap;
import java.util.Objects;

public class Instance extends World implements Communication {
    public Lobby lobby;
    public Controller controller;
    public Frame frame;

    public String account;
    String username;
    boolean game;

    public Byte character = 0;
    public Player player;
    public HashMap<String, Integer> scoreboard = new HashMap<>();
    public HashMap<String, Byte> avatars = new HashMap<>();
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
    }

    void register() {
        username = JOptionPane.showInputDialog("Enter your Username");
        controller.communications.authentication.register(username);
    }

    public void act() {
    }

    @Override
    public void started() {
        super.started();
    }

    public void addScore() {
        if (player != null) {
            scoreboard.put(account, scoreboard.get(account) + 1);
        }

        for(HashMap.Entry<String, Ghost> entry : ghosts.entrySet()) {
            String account = entry.getKey();
            scoreboard.put(account, scoreboard.get(account) + 1);
        }
    }

    public Player player(Byte avatar) {
        switch (avatar) {
            case 1:
                player = new PlayerRoman(account, controller, scoreboard, ghosts);
                break;
            case 2:
                player = new PlayerAlex(account, controller, scoreboard, ghosts);
                break;
            case 3:
                player = new PlayerDavid(account, controller, scoreboard, ghosts);
                break;
            case 4:
                player = new PlayerDino(account, controller, scoreboard, ghosts);
                break;
            case 5:
                player = new PlayerEly(account, controller, scoreboard, ghosts);
                break;
            case 6:
                player = new PlayerJustin(account, controller, scoreboard, ghosts);
                break;
            case 7:
                player = new PlayerMarcus(account, controller, scoreboard, ghosts);
                break;
            case 8:
                player = new PlayerKilian(account, controller, scoreboard, ghosts);
                break;
            case 9:
                player = new PlayerSimon(account, controller, scoreboard, ghosts);
                break;
            case 0:
            default:
                player = new PlayerFindus(account, controller, scoreboard, ghosts);
                break;
        }

        addObject(player, 250, 375);
    }

    public Ghost ghostCharacter(Byte avatar, String account) {
        switch (avatar) {
            case 1:
                return new GhostRoman(account, controller, scoreboard, ghosts);
            case 2:
                return new GhostAlex(account, controller, scoreboard, ghosts);
            case 3:
                return new GhostDavid(account, controller, scoreboard, ghosts);
            case 4:
                return new GhostDino(account, controller, scoreboard, ghosts);
            case 5:
                return new GhostEly(account, controller, scoreboard, ghosts);
            case 6:
                return new GhostJustin(account, controller, scoreboard, ghosts);
            case 7:
                return new GhostMarcus(account, controller, scoreboard, ghosts);
            case 8:
                return new GhostKilian(account, controller, scoreboard, ghosts);
            case 9:
                return new GhostSimon(account, controller, scoreboard, ghosts);
            case 0:
            default:
                return new GhostFindus(account, controller, scoreboard, ghosts);
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
        JOptionPane.showMessageDialog(null, "Joined Server.");
    }

    @Override
    public void onAuthenticationTaken() {
        JOptionPane.showMessageDialog(null, "Username already taken.");
        username = "";
        register();
    }

    @Override
    public void onAuthenticationUnregistered() {
        JOptionPane.showMessageDialog(null, "Left server.");
        username = "";
        register();
    }

    @Override
    public void onSessionStart() {
        game = true;
    }

    @Override
    public void onSessionReady(String account) {
        readies.put(account, true);
    }

    @Override
    public void onSessionUnReady(String account) {
        readies.put(account, false);

        if (game) {
            if (!Objects.equals(this.account, account)) {
                ghosts.get(account).die();
            }

            if (ghosts.size() == 0 && player == null) {
                game = false;
                Greenfoot.setWorld(lobby);
                System.out.println("done");
            }
        }
    }

    @Override
    public void onSessionUserJoined(String account, String username) {
        if (!Objects.equals(this.account, account)) {
            Ghost ghost = ghostCharacter((byte) 0, account);
            addObject(ghost, 250, 375);

            users.put(account, username);
            scoreboard.put(account, 0);
            ghosts.put(account, ghost);
            readies.put(account, false);
            avatars.put(account, (byte) 0);
            lobby.entry(account);
        }
    }

    @Override
    public void onSessionUserLeft(String account) {
        Ghost ghost = ghosts.get(account);
        ghost.die();
        scoreboard.remove(account);
        users.remove(account);
        avatars.remove(account);
        readies.remove(account);
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
    public void onSessionAvatarUpdate(String account, Byte avatar) {
        if (ghosts.containsKey(account)) {
            ghosts.get(account).die();
        }

        Ghost ghost = ghostCharacter((byte) 0, account);
        ghosts.put(account, ghost);
        addObject(ghost, 250, 375);
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
