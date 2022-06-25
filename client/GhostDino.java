import java.util.HashMap;

public class GhostDino extends Ghost {
    public GhostDino(String account, Controller controller, HashMap<String, Integer> scoreboard, HashMap<String, Ghost> ghosts) {
        super(account, controller, scoreboard, ghosts);
    }
}
