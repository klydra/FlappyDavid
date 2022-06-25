import java.util.HashMap;

public class GhostDino extends Ghost {
    public GhostDino(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
