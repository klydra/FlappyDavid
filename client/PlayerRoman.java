import java.util.HashMap;

public class PlayerRoman extends Player {
    public PlayerRoman(byte[] account, Controller controller, HashMap<byte[], Integer> scoreboard, HashMap<byte[], Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
