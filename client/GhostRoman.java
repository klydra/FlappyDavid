import java.util.HashMap;

public class GhostRoman extends Ghost {
    public GhostRoman(String account, Controller controller, HashMap<String, Integer> scoreboard, HashMap<String, Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
