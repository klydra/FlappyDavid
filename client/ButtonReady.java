public class ButtonReady extends Button {
    public void ready(boolean ready) {
        if (ready) {
            setImage("images/buttons/ready.png");
        } else {
            setImage("images/buttons/unready.png");
        }
    }
}
