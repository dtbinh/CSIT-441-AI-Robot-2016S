import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import parts.EV3LocalBrick;
import parts.EV3RemoteBrick;
import utils.Notifications;

/**
 * Brick 1:
 *      Motor A: Forward wheel
 *      Motor B: 120 Cclockwise wheel
 *      Motor C: 120 Clockwise wheel
 *      Motor D: Putter arm
 *      Sensor 1: Forward Prox
 *      Sensor 2: 120 Clockwise Prox
 *      Sensor 3: 120 CClockwise Prox
 *      Sensor 4: Gyroscope
 *
 *  Brick 2:
 *      Motor A: Forward color
 *      Motor B: Downward color
 *      Motor C:
 *      Motor D:
 *      Sensor 1:
 *      Sensor 2:
 *      Sensor 3:
 *      Sensor 4:
 */
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