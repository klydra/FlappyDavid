import java.util.HashMap;

public class GhostEly extends Ghost {
    public GhostEly(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
