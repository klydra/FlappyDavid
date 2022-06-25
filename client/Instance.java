import greenfoot.*;

import java.util.HashMap;

public class Instance extends World implements Communication {
    public Controller controller;
    public boolean game = false;
    public int passed;
    public Frame frame;

    public byte[] account; // TODO : Pre-convert account to string
    String username;

    public Byte character = 0;
    public Player player;
    public HashMap<byte[], Integer> scoreboard = new HashMap<>();
    public HashMap<byte[], String> users = new HashMap<>();
    public HashMap<byte[], Ghost> ghosts = new HashMap<>();

    public Instance() {
        super(1000, 750, 1);

        controller = new Controller(this, "ws://localhost:80");

        frame = new Frame();
        addObject(frame, 500, 375);

        changeCharacter(character);

        username = "klydra";
    }

    public void act() {
        if (game) {
            addScore();
        }
    }

    @Override
    public void started() {
        controller.communications.authentication.register("klydra");
        controller.communications.session.ready();
        super.started();
    }

    public void addScore() {
        if (passed == 0) {
            passed = 20;

            if (player != null) {
                scoreboard.put(account, scoreboard.get(account) + 1);
            }

            for(HashMap.Entry<byte[], Ghost> entry : ghosts.entrySet()) {
                byte[] account = entry.getKey();
                scoreboard.put(account, scoreboard.get(account) + 1);
            }
        } else {
            passed--;
        }
    }

    public void changeCharacter(Byte avatar) {
        if (player != null) {
            player.die();
        }

        switch (avatar) {
            case 1:
                player = new PlayerAdrian(account, controller, scoreboard, ghosts);
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
                player = new PlayerRoman(account, controller, scoreboard, ghosts);
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

    public Ghost ghostCharacter(Byte avatar, byte[] account) {
        switch (avatar) {
            case 1:
                return new GhostAdrian(account, controller, scoreboard, ghosts);
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
                return new GhostRoman(account, controller, scoreboard, ghosts);
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
    public void onAuthenticationRegistered(byte[] account) {
        this.account = account;
        users.put(account, username);
        scoreboard.put(account, 0);
    }

    @Override
    public void onAuthenticationTaken() {

    }

    @Override
    public void onAuthenticationUnregistered() {

    }

    @Override
    public void onSessionStart() {
        game = true;
        passed = 50;
    }

    @Override
    public void onSessionReady(byte[] account) {

    }

    @Override
    public void onSessionUnReady(byte[] account) {
        if (ghosts.size() == 0 && player == null) {
            /* Game Finished */
        }
    }

    @Override
    public void onSessionUserJoined(byte[] account, String username) {
        if (this.account != account) {
            Ghost ghost = ghostCharacter((byte) 0, account);
            addObject(ghost, 250, 375);

            users.put(account, username);
            scoreboard.put(account, 0);
            ghosts.put(account, ghost);
        }
    }

    @Override
    public void onSessionUserLeft(byte[] account) {
        Ghost ghost = ghosts.get(account);
        ghost.die();
        scoreboard.remove(account);
        users.remove(account);
    }

    @Override
    public void onSessionPositionUpdate(byte[] account, int positionY) {
        if (this.account != account) {
            ghosts.get(account).updatePosition(positionY);
        }
    }

    @Override
    public void onSessionAvatarUpdate(byte[] account, Byte avatar) {
        if (ghosts.containsKey(account)) {
            ghosts.get(account).die();
        }

        Ghost ghost = ghostCharacter((byte) 0, account);
        ghosts.put(account, ghost);
        addObject(ghost, 250, 375);
    }

    @Override
    public void onSessionObstacle(int position) {
        int winkel = 180;
        int speed = 2;

        if (position > 400) {
            int posY1 = position - 200;
            int posX1 = 1000;
            int posY2 = position + 300;
            int posX2 = 1000;
            addObject(new TubeLong(winkel, speed), posX1, posY1);
            addObject(new TubeShort(winkel, speed), posX2, posY2);
        }
        if (250 < position && position < 400) {
            int posY1 = position - 250;
            int posX1 = 1000;
            int posY2 = position + 350;
            int posX2 = 1000;
            addObject(new TubeLong(winkel, speed), posX1, posY1);
            addObject(new TubeMedium(winkel, speed), posX2, posY2);
        }
        if (200 < position && position < 250) {
            int posY1 = position - 200;
            int posX1 = 1000;
            int posY2 = position + 500;
            int posX2 = 1000;
            addObject(new TubeLong(winkel, speed), posX1, posY1);
            addObject(new TubeLong(winkel, speed), posX2, posY2);
        }
        if (50 < position && position < 200) {
            int posY1 = position - 50;
            int posX1 = 1000;
            int posY2 = position + 550;
            int posX2 = 1000;
            addObject(new TubeMedium(winkel, speed), posX1, posY1);
            addObject(new TubeLong(winkel, speed), posX2, posY2);
        }
        if (50 > position) {
            int posY1 = position;
            int posX1 = 1000;
            int posY2 = position + 500;
            int posX2 = 1000;
            addObject(new TubeShort(winkel, speed), posX1, posY1);
            addObject(new TubeLong(winkel, speed), posX2, posY2);
        }
    }
}
