import java.util.HashMap;

public class GhostAdrian extends Ghost {
    public GhostAdrian(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
