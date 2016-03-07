import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import parts.EV3LocalBrick;
import parts.EV3RemoteBrick;
import utils.Notifications;

public class Main {

    private  EV3LocalBrick localEV3;
    private  EV3RemoteBrick remoteEV3;


    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        setup();
        helloWorld();
        cleanExit();
    }

    private void setup() {
        localEV3 = new EV3LocalBrick();
        remoteEV3 = new EV3RemoteBrick();

        //Testing the ready functionality
        Notifications.ready();
    }

    /**
     * Basic hello world to test the deploy functionality is working properly
     */
    private void helloWorld() {
        localEV3.helloWorld();
        remoteEV3.helloWorld();

        LCD.clear();
        LCD.drawString("First EV3 Program", 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
    }

    private void cleanExit() {
        localEV3.cleanExit();
        remoteEV3.cleanExit();
    }
}