import greenfoot.Color;
import greenfoot.GreenfootImage;

public class ButtonText extends Button {
    Color color;
    int fontSize;

    public ButtonText(Color color, int fontSize) {
        this.color = color;
        this.fontSize = fontSize;
    }

    public void content(String text) {
        setImage(new GreenfootImage(text, fontSize, color, new Color(0, 0, 0, 0)));
    }
}
