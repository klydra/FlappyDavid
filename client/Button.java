import greenfoot.*;

public class Button extends Actor {
    public void destroy() {
        getWorld().removeObject(this);
    }
}
