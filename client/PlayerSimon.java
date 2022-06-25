import java.util.HashMap;

public class PlayerSimon extends Player {
    public PlayerSimon(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
