import greenfoot.Color;
import greenfoot.GreenfootImage;

public class ButtonText extends Button {
    public void content(String text, int fontSize) {
        setImage(new GreenfootImage(text, fontSize, Color.BLACK, new Color(0, 0, 0, 0)));
    }
}
