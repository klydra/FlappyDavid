import greenfoot.Color;
import greenfoot.GreenfootImage;

public class ButtonAvatar extends Button {
    public ButtonAvatar(int avatar) {
        switch (avatar) {
            case 1:
                setImage(new GreenfootImage("images/characters/player/roman.png"));
                break;
            case 2:
                setImage(new GreenfootImage("images/characters/player/alex.png"));
                break;
            case 3:
                setImage(new GreenfootImage("images/characters/player/david.png"));
                break;
            case 4:
                setImage(new GreenfootImage("images/characters/player/dino.png"));
                break;
            case 5:
                setImage(new GreenfootImage("images/characters/player/ely.png"));
                break;
            case 6:
                setImage(new GreenfootImage("images/characters/player/justin.png"));
                break;
            case 7:
                setImage(new GreenfootImage("images/characters/player/marcus.png"));
                break;
            case 8:
                setImage(new GreenfootImage("images/characters/player/kilian.png"));
                break;
            case 9:
                setImage(new GreenfootImage("images/characters/player/simon.png"));
                break;
            case 0:
            default:
                setImage(new GreenfootImage("images/characters/player/findus.png"));
                break;
        }
    }

    ButtonText username;
    ButtonText score;

    public void description(String username, String score) {
        if (this.username != null) {
            getWorld().removeObject(this.username);
        }
        if (this.score != null) {
            getWorld().removeObject(this.score);
        }

        this.username = new ButtonText(Color.BLACK, 20);
        this.username.content(username);
        getWorld().addObject(this.username, getX() + 130, getY());

        this.score = new ButtonText(Color.BLACK, 20);
        this.score.content(score);
        getWorld().addObject(this.score, getX() + 250, getY());
    }

    @Override
    public void destroy() {
        if (this.username != null) {
            getWorld().removeObject(this.username);
        }
        if (this.score != null) {
            getWorld().removeObject(this.score);
        }
        super.destroy();
    }
}
