import java.util.HashMap;

public class GhostDavid extends Ghost {
    public GhostDavid(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
