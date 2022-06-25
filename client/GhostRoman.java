import java.util.HashMap;

public class GhostRoman extends Ghost {
    public GhostRoman(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
