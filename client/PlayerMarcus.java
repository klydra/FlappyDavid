import java.util.HashMap;

public class PlayerMarcus extends Player {
    public PlayerMarcus(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
