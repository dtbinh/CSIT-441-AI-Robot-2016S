import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import parts.EV3LocalBrick;
import parts.EV3RemoteBrick;
import utils.Notifications;

public class Main {

    private static EV3LocalBrick localEV3;
    private static EV3RemoteBrick remoteEV3;

    /**
     * Main
     * Start of program
     * @param args
     */
    public static void main(String[] args) {
        //Check that the bricks are working
        LCD.clear();
        LCD.drawString("First EV3 Program", 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();

        setup();

        helloWorld();
    }

    private static void setup() {
        //Testing the ready functionality
        Notifications.ready();
    }

    private static void helloWorld() {
        localEV3 = new EV3LocalBrick();
        remoteEV3 = new EV3RemoteBrick();
    }
}