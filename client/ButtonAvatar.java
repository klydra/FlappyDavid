public class ButtonAvatar extends Button {
    ButtonText username;
    ButtonText score;

    public void avatar(Byte avatar) {
        switch (avatar) {
            case 1:
                setImage("characters/player/roman.png");
            case 2:
                setImage("characters/player/alex.png");
            case 3:
                setImage("characters/player/david.png");
            case 4:
                setImage("characters/player/dino.png");
            case 5:
                setImage("characters/player/ely.png");
            case 6:
                setImage("characters/player/justin.png");
            case 7:
                setImage("characters/player/marcus.png");
            case 8:
                setImage("characters/player/kilian.png");
            case 9:
                setImage("characters/player/kilian.png");
            case 0:
            default:
                setImage("characters/player/findus.png");
        }
    }

    public void description(String username, String score) {
        if (this.username != null) {
            getWorld().removeObject(this.username);
        }
        if (this.score != null) {
            getWorld().removeObject(this.score);
        }

        this.username = new ButtonText();
        this.username.content(username, 20);
        getWorld().addObject(this.username, getX() + 50, getY());

        this.score = new ButtonText();
        this.score.content(score, 20);
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
