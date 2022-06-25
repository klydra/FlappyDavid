import greenfoot.*;

import java.util.HashMap;

public class Ghost extends Character {
    public Ghost(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }

    public void updatePosition(int positionY) {
        setLocation(getX(), positionY);
    }

    @Override
    public void die() {
        ghosts.remove(account);
        super.die();
    }
}
