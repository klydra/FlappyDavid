import java.util.HashMap;

public class GhostAlex extends Ghost {
    public GhostAlex(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
