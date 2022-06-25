public class ButtonReady extends Button {
    public void ready(boolean ready) {
        if (ready) {
            setImage("buttons/ready.png");
        } else {
            setImage("buttons/unready.png");
        }
    }
}
