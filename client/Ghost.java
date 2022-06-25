import greenfoot.*;

import java.util.HashMap;

public class Ghost extends Character {
    public Ghost(String account, Controller controller, HashMap<String, Integer> scoreboard, HashMap<String, Ghost> ghosts) {
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
