import java.util.HashMap;

public class GhostSimon extends Ghost {
    public GhostSimon(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
