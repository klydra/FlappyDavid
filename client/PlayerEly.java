import java.util.HashMap;

public class PlayerEly extends Player {
    public PlayerEly(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
