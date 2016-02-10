package parts;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;

/**
 * Brick 1 = 192.168.0.10
 * Brick 2 = 192.168.0.11
 *
 * Tire 1 = EV3LocalBrick A (centralMotor)
 * Tire 2 = EV3LocalBrick B (CW120degMotor)
 * Tire 3 = Ev3LocalBrick C (CCW120degMotor)
 *
 * Color sensor = EV3LocalBrick 1 (color sensor below the frame)
 * Proximity Sensors?
 */
public class EV3LocalBrick {

    public EV3LocalBrick() {
        // TODO Setup motors if needed

        // TODO Setup sensors

    }

    public void printReady() {
        LCD.clear();
        Button.LEDPattern(4);
        LCD.drawString("Ready", 0, 5);
        Button.waitForAnyPress();
        LCD.clear();
        LCD.refresh();
    }

    public void helloWorld() {
        Button.LEDPattern(1);

        Motor.A.setSpeed(Motor.A.getMaxSpeed());
        Motor.B.setSpeed(Motor.A.getMaxSpeed());
        Motor.C.setSpeed(Motor.A.getMaxSpeed());

        Motor.A.forward();
        Motor.B.forward();
        Motor.C.forward();
    }

    public void cleanExit() {
        Button.LEDPattern(0);
    }

}

