import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse Djungle.
 *
 * @author (Ihr Name)
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Background extends World implements Communication {
    public Controller controller;
    public boolean game = false;

    public Background() {
        super(1000, 750, 1);
        controller = new Controller(this, "ws://localhost:80");
        Frame f = new Frame();
        addObject(f, 500, 375);
        David d = new David(controller);
        addObject(d, 250, 375);

        controller.communications.authentication.register("klydra");
    }

    public void act() {

    }

    @Override
    public void onAuxiliaryMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onAuthenticationRegistered() {
        controller.communications.session.ready();
    }

    @Override
    public void onAuthenticationTaken() {

    }

    @Override
    public void onAuthenticationUnregistered() {

    }

    @Override
    public void onSessionStart() {

    }

    @Override
    public void onSessionReady(byte[] account) {

    }

    @Override
    public void onSessionUnReady(byte[] account) {

    }

    @Override
    public void onSessionUserJoined(byte[] account, String username) {

    }

    @Override
    public void onSessionUserLeft(byte[] account) {

    }

    @Override
    public void onSessionPositionUpdate(byte[] account, int positionY) {

    }

    @Override
    public void onSessionAvatarUpdate(byte[] account, Byte avatar) {

    }

    @Override
    public void onSessionObstacle(int position) {
        if (!game) {
            return;
        }

        int winkel = 180;
        int speed = 2;

        if (position > 400) {
            int posY1 = position - 200;
            int posX1 = 1000;
            int posY2 = position + 300;
            int posX2 = 1000;
            addObject(new TubeLong(winkel, speed), posX1, posY1);
            addObject(new TubeShort(winkel, speed), posX2, posY2);
        }
        if (250 < position && position < 400) {
            int posY1 = position - 250;
            int posX1 = 1000;
            int posY2 = position + 350;
            int posX2 = 1000;
            addObject(new TubeLong(winkel, speed), posX1, posY1);
            addObject(new TubeMedium(winkel, speed), posX2, posY2);
        }
        if (200 < position && position < 250) {
            int posY1 = position - 200;
            int posX1 = 1000;
            int posY2 = position + 500;
            int posX2 = 1000;
            addObject(new TubeLong(winkel, speed), posX1, posY1);
            addObject(new TubeLong(winkel, speed), posX2, posY2);
        }
        if (50 < position && position < 200) {
            int posY1 = position - 50;
            int posX1 = 1000;
            int posY2 = position + 550;
            int posX2 = 1000;
            addObject(new TubeMedium(winkel, speed), posX1, posY1);
            addObject(new TubeLong(winkel, speed), posX2, posY2);
        }
        if (50 > position) {
            int posY1 = position;
            int posX1 = 1000;
            int posY2 = position + 500;
            int posX2 = 1000;
            addObject(new TubeShort(winkel, speed), posX1, posY1);
            addObject(new TubeLong(winkel, speed), posX2, posY2);
        }
    }
}
